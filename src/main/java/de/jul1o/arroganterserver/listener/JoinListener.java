package de.jul1o.arroganterserver.listener;

import de.jul1o.arroganterserver.Data;
import de.jul1o.arroganterserver.utils.PrefixUtil;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("permission.spawn") || player.isOp()) {
            player.setGameMode(GameMode.CREATIVE);
        }

        event.setJoinMessage(null);
/*        if (player.hasPermission("displayname.admin")) {
            event.setJoinMessage("§8[§a+§8] " + ChatColor.DARK_RED + event.getPlayer().getDisplayName());
        } else if (player.hasPermission("displayname.clan")) {
            event.setJoinMessage("§8[§a+§8] " + ChatColor.AQUA + event.getPlayer().getDisplayName());
        } else if (player.hasPermission("displayname.kyudo")) {
            event.setJoinMessage("§8[§a+§8] " + ChatColor.DARK_AQUA + event.getPlayer().getDisplayName());
        } else {
            event.setJoinMessage("§8[§a+§8] " + ChatColor.GOLD + event.getPlayer().getDisplayName());
        }

 */


        event.getPlayer().teleport(Data.SPAWN);

        player.sendMessage("§8");
        player.sendMessage("§8§m----------------§r§8[§6§l N E W S §r§8]§m----------------");
        player.sendMessage("§8");

        for (final String message : Data.NEWS) {
            player.sendMessage(Data.replace(message));
        }

        player.sendMessage("§8");
        player.sendMessage("§8§m----------------§r§8[§6§l N E W S §r§8]§m----------------");
        player.sendMessage("§8");

    }
}
