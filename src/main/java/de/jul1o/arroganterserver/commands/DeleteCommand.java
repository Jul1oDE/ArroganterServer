package de.jul1o.arroganterserver.commands;

import de.jul1o.arroganterserver.Data;
import de.jul1o.arroganterserver.Main;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;

public class DeleteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("build.world.delete")) {
                createInventory((Player) sender, 0);
            } else {
                sender.sendMessage(Data.COMMAND_PERMISSION);
            }
        } else {
            if (args.length == 1) {
                for (final String worlds : Main.getMaps()) {
                    if (worlds.equalsIgnoreCase(args[0])) {
                        delete(args[0], sender);
                        return true;
                    }
                }
            }
            sender.sendMessage(Data.WORLD_DELETE_HELP);
        }

        return true;
    }

    public static void createInventory(final Player player, final int first) {
        final Inventory inventory = Bukkit.getServer().createInventory(null, 27, Data.WORLD_DELETE_INVENTORY);

        int slot = first;
        for (int i = 0; i < 27; i++) {
            if (i < 10 || i > 16) {
                inventory.setItem(i, WorldCommand.createItem(Material.STAINED_GLASS_PANE, "&7"));
            } else if (i == 10) {
                inventory.setItem(i, WorldCommand.createSkull(true, first != 0, first));
            } else if (i == 16) {
                inventory.setItem(i, WorldCommand.createSkull(false, Main.getMaps().size() - 1 - first > 4, first));
            } else {
                if (slot < Main.getMaps().size()) {
                    inventory.setItem(i, WorldCommand.createItem(Material.GRASS, "&e" + Main.getMaps().get(slot)));
                    slot++;
                } else {
                    inventory.setItem(i, WorldCommand.createItem(Material.BARRIER, "&7"));
                }
            }
        }

        player.openInventory(inventory);
    }

    public static void createInventory(final Player player, final String name) {
        final Inventory inventory = Bukkit.getServer().createInventory(null, 27, Data.WORLD_DELETE_CONFIRM);

        for (int i = 0; i < 27; i++) {
            if (i < 10 || i > 16 || i == 13) {
                inventory.setItem(i, WorldCommand.createItem(Material.STAINED_GLASS_PANE, "&7"));
            } else if (i < 13) {
                inventory.setItem(i, WorldCommand.createItem(Material.STAINED_CLAY, Data.WORLD_DELETE_ACCEPT, (short) 5));
            } else {
                inventory.setItem(i, WorldCommand.createItem(Material.STAINED_CLAY, Data.WORLD_DELETE_DENY, (short) 14));
            }
        }

        player.openInventory(inventory);

        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        entityPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(entityPlayer.activeContainer.windowId, "minecraft:chest", new ChatMessage(Data.WORLD_DELETE_CONFIRM_FAKE.replace("%world%", name)), player.getOpenInventory().getTopInventory().getSize()));
    }

    public static void delete(final String name, final CommandSender sender) {
        if (Bukkit.getWorlds().get(0).getName().equalsIgnoreCase(name)) {
            sender.sendMessage(Data.WORLD_DELETE_ERROR);
        } else {
            final org.bukkit.World world = Bukkit.getWorld(name);
            for (final org.bukkit.World worlds : Bukkit.getWorlds()) {
                if (worlds.getName().equalsIgnoreCase(name)) {
                    for (final Player players : world.getPlayers()) {
                        players.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                        players.sendMessage(Data.WORLD_TELEPORT.replace("%world%", Bukkit.getWorlds().get(0).getName()));
                    }
                }
            }

            try {
                Bukkit.unloadWorld(world, false);
                final File file = new File(Bukkit.getServer().getWorldContainer(), world.getName());
                FileUtils.deleteDirectory(file);
                sender.sendMessage(Data.WORLD_DELETE.replace("%world%", world.getName()));
            } catch (final IOException ignored) {
                sender.sendMessage(Data.WORLD_DELETE_ERROR);
            }
        }
    }
}
