package de.jul1o.arroganterserver.listener;

import de.jul1o.arroganterserver.Data;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Listeners implements Listener {

    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAchivment(PlayerAchievementAwardedEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("Spawn")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        if (event.getPlayer().getWorld().getName().equalsIgnoreCase("Spawn")) {
            if (event.getPlayer().getLocation().getY() <= 65) {
                event.getPlayer().teleport(Data.SPAWN);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equalsIgnoreCase("Spawn") && event.getRightClicked() instanceof ArmorStand) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractPotion(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null && event.getItem().getType() == Material.POTION) {
            player.setItemInHand(null);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
            ((Player) event.getEntity()).setFoodLevel(20);
        }
    }

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!(player.hasPermission("permission.plugin.command"))) {
            if (event.getMessage().split(" ")[0].equalsIgnoreCase("/pl")) {
                event.setCancelled(true);
            }
        }
    }
}
