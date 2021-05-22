package de.jul1o.arroganterserver.listener;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class JumppadListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getBlock().getType() == Material.GOLD_PLATE) {
            Vector vector = player.getLocation().getDirection().multiply(3.0D).setY(1);
            player.setVelocity(vector);
            player.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 3);
            player.playSound(player.getLocation(), Sound.FIZZ, 1.0F, 1.0F);
        }
    }
}
