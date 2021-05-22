package de.jul1o.arroganterserver.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.jul1o.arroganterserver.Data;
import de.jul1o.arroganterserver.Main;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class WorldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("build.world.teleport")) {
                createInventory((Player) sender, 0);
            } else {
                sender.sendMessage(Data.COMMAND_PERMISSION);
            }
        } else {
            sender.sendMessage(Data.COMMAND_PLAYER);
        }

        return true;
    }

    public static void createInventory(final Player player, final int first) {
        final Inventory inventory = Bukkit.getServer().createInventory(null, 27, Data.WORLD_INVENTORY);

        int slot = first;
        for (int i = 0; i < 27; i++) {
            if (i < 10 || i > 16) {
                inventory.setItem(i, createItem(Material.STAINED_GLASS_PANE, "&7"));
            } else if (i == 10) {
                inventory.setItem(i, createSkull(true, first != 0, first));
            } else if (i == 16) {
                inventory.setItem(i, createSkull(false, Main.getMaps().size() - 1 - first > 4, first));
            } else {
                if (slot < Main.getMaps().size()) {
                    inventory.setItem(i, createItem(Material.GRASS, "&e" + Main.getMaps().get(slot)));
                    slot++;
                } else {
                    inventory.setItem(i, createItem(Material.BARRIER, "&7"));
                }
            }
        }

        player.openInventory(inventory);
    }

    public static ItemStack createItem(final Material material, final String name) {
        return createItem(material, name, (short) 0);
    }

    public static ItemStack createItem(final Material material, final String name, final short data) {
        final ItemStack item = new ItemStack(material, 1, data);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Data.replace(name));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createSkull(final boolean left, final boolean wood, final int start) {
        final ItemStack item = new ItemStack(Material.SKULL_ITEM);
        final SkullMeta meta = (SkullMeta) item.getItemMeta();
        final GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");

        final String wood_next = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19";
        final String wood_back = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==";

        final String stone_next = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjJmM2EyZGZjZTBjM2RhYjdlZTEwZGIzODVlNTIyOWYxYTM5NTM0YThiYTI2NDYxNzhlMzdjNGZhOTNiIn19fQ==";
        final String stone_back = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmIwZjZlOGFmNDZhYzZmYWY4ODkxNDE5MWFiNjZmMjYxZDY3MjZhNzk5OWM2MzdjZjJlNDE1OWZlMWZjNDc3In19fQ==";

        if (wood) {
            gameProfile.getProperties().put("textures", new Property("textures", left ? wood_back : wood_next));
        } else {
            gameProfile.getProperties().put("textures", new Property("textures", left ? stone_back : stone_next));
        }

        try {
            final Field field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta, gameProfile);
        } catch (final NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        meta.setDisplayName(left ? Data.WORLD_BACK : Data.WORLD_NEXT);

        item.setDurability((short) 3);
        item.setItemMeta(meta);

        final net.minecraft.server.v1_8_R3.ItemStack craftItem = CraftItemStack.asNMSCopy(item);
        final NBTTagCompound compound = craftItem.hasTag() ? craftItem.getTag() : new NBTTagCompound();

        compound.setBoolean("ClickAble", wood);
        compound.setInt("FirstWorld", start);

        return CraftItemStack.asBukkitCopy(craftItem);
    }
}
