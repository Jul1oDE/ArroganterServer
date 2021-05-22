package de.jul1o.arroganterserver.listener;

import de.jul1o.arroganterserver.Data;
import de.jul1o.arroganterserver.commands.DeleteCommand;
import de.jul1o.arroganterserver.commands.WorldCommand;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class WorldListener implements Listener {

    private final HashMap<Player, String> DELETE = new HashMap<>();

    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        try {
            final Player player = (Player) event.getWhoClicked();
            final Inventory inventory = event.getClickedInventory();
            final ItemStack item = event.getCurrentItem();

            if (inventory.getName().equalsIgnoreCase(Data.WORLD_INVENTORY)) {
                event.setCancelled(true);

                if (item.getType() == Material.GRASS) {
                    final String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
                    for (final org.bukkit.World worlds : Bukkit.getWorlds()) {
                        if (worlds.getName().equalsIgnoreCase(name)) {
                            player.closeInventory();
                            player.teleport(worlds.getSpawnLocation());
                            player.playSound(player.getLocation(), Sound.WOOD_CLICK, 4.0f, 1.0f);
                            player.sendMessage(Data.WORLD_TELEPORT.replace("%world%", worlds.getName()));
                            return;
                        }
                    }

                    final org.bukkit.World world = new WorldCreator(name).createWorld();
                    player.closeInventory();
                    player.teleport(world.getSpawnLocation());
                    player.playSound(player.getLocation(), Sound.WOOD_CLICK, 4.0f, 1.0f);
                    player.sendMessage(Data.WORLD_TELEPORT.replace("%world%", world.getName()));
                } else if (item.getType() == Material.SKULL_ITEM) {
                    final net.minecraft.server.v1_8_R3.ItemStack craftItem = CraftItemStack.asNMSCopy(item);
                    if (craftItem.hasTag()) {
                        final NBTTagCompound compound = craftItem.getTag();
                        final boolean clickAble = compound.getBoolean("ClickAble");
                        final int start = compound.getInt("FirstWorld");

                        if (clickAble) {
                            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Data.WORLD_NEXT)) {
                                WorldCommand.createInventory(player, start + 1);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Data.WORLD_BACK)) {
                                WorldCommand.createInventory(player, start - 1);
                            }
                            player.playSound(player.getLocation(), Sound.WOOD_CLICK, 4.0f, 1.0f);
                        } else {
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 4.0f, 0.1f);
                        }
                    }
                }
            } else if (inventory.getName().equalsIgnoreCase(Data.WORLD_DELETE_INVENTORY)) {
                event.setCancelled(true);

                if (item.getType() == Material.GRASS) {
                    DELETE.put(player, ChatColor.stripColor(item.getItemMeta().getDisplayName()));
                    DeleteCommand.createInventory(player, DELETE.get(player));
                    player.playSound(player.getLocation(), Sound.WOOD_CLICK, 4.0f, 1.0f);
                } else if (item.getType() == Material.SKULL_ITEM) {
                    final net.minecraft.server.v1_8_R3.ItemStack craftItem = CraftItemStack.asNMSCopy(item);
                    if (craftItem.hasTag()) {
                        final NBTTagCompound compound = craftItem.getTag();
                        final boolean clickAble = compound.getBoolean("ClickAble");
                        final int start = compound.getInt("FirstWorld");

                        if (clickAble) {
                            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Data.WORLD_NEXT)) {
                                DeleteCommand.createInventory(player, start + 1);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Data.WORLD_BACK)) {
                                DeleteCommand.createInventory(player, start - 1);
                            }
                            player.playSound(player.getLocation(), Sound.WOOD_CLICK, 4.0f, 1.0f);
                        } else {
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 4.0f, 0.1f);
                        }
                    }
                }
            } else if (inventory.getName().equalsIgnoreCase(Data.WORLD_DELETE_CONFIRM)) {
                event.setCancelled(true);
                if (DELETE.containsKey(player)) {
                    if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Data.WORLD_DELETE_ACCEPT)) {
                        DeleteCommand.delete(DELETE.get(player), player);
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.WOOD_CLICK, 4.0f, 1.0f);
                        DELETE.remove(player);
                    } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Data.WORLD_DELETE_DENY)) {
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.WOOD_CLICK, 4.0f, 1.0f);
                        DELETE.remove(player);
                    }
                } else {
                    player.closeInventory();
                }
            }
        } catch (final NullPointerException ignored) {}
    }

    @EventHandler
    public void onClose(final InventoryCloseEvent event) {
        if (event.getInventory().getName().equalsIgnoreCase(Data.WORLD_DELETE_CONFIRM)) {
            DELETE.remove((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void onLoad(final WorldLoadEvent event) {
        final org.bukkit.World world = event.getWorld();
        world.setKeepSpawnInMemory(false);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("randomTickSpeed", "0");
        world.setTime(6000);
        world.setThundering(false);
        world.setStorm(false);
    }

    @EventHandler
    public void onInit(final WorldInitEvent event) {
        final org.bukkit.World world = event.getWorld();
        world.setKeepSpawnInMemory(false);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("randomTickSpeed", "0");
        world.setTime(6000);
        world.setThundering(false);
        world.setStorm(false);
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        DELETE.remove(event.getPlayer());
    }
}
