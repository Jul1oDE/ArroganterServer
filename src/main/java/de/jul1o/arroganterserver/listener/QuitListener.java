package de.jul1o.arroganterserver.listener;

import de.jul1o.arroganterserver.utils.PrefixUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(null);
/*
        if (player.hasPermission("displayname.admin")) {
            event.setQuitMessage("§8[§c-§8] " + ChatColor.DARK_RED + event.getPlayer().getDisplayName());
        } else if (player.hasPermission("displayname.clan")) {
            event.setQuitMessage("§8[§c-§8] " + ChatColor.AQUA + event.getPlayer().getDisplayName());
        } else if (player.hasPermission("displayname.kyudo")) {
            event.setQuitMessage("§8[§c-§8] " + ChatColor.DARK_AQUA + event.getPlayer().getDisplayName());
        } else {
            event.setQuitMessage("§8[§c-§8] " + ChatColor.GOLD + event.getPlayer().getDisplayName());
        }

 */
    }
}
