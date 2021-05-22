package de.jul1o.arroganterserver.listener;

import de.jul1o.arroganterserver.commands.BuildCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equalsIgnoreCase("spawn")) {
            if (!BuildCommand.allowed.contains(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equalsIgnoreCase("spawn")) {
            if (!BuildCommand.allowed.contains(player)) {
                event.setCancelled(true);
            }
        }
    }

}
