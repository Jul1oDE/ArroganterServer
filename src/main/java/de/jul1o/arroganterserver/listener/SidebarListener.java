package de.jul1o.arroganterserver.listener;

import de.jul1o.arroganterserver.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SidebarListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Sidebar sidebar = new Sidebar(event.getPlayer());
        sidebar.set();

        Main.SIDEBAR.put(event.getPlayer(), sidebar);

        for (final Sidebar sidebars : Main.SIDEBAR.values()) {
            sidebars.update();
        }

        for (final Player players : Main.TEAM.keySet()) {
            Main.TEAM.get(players).update();
        }

        Main.TEAM.put(event.getPlayer(), new TeamManager(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        Main.SIDEBAR.remove(event.getPlayer());

        for (final Sidebar sidebars : Main.SIDEBAR.values()) {
            sidebars.update();
        }

        Main.TEAM.remove(event.getPlayer());

        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.PLUGIN, ()-> {
            for (final Player players : Main.TEAM.keySet()) {
                Main.TEAM.get(players).update();
            }
        }, 1);
    }

    @EventHandler
    public void onChange(final PlayerChangedWorldEvent event) {
        if (Main.SIDEBAR.containsKey(event.getPlayer())) {
            Main.SIDEBAR.get(event.getPlayer()).update();
        }
        if (Main.TEAM.containsKey(event.getPlayer())) {
            Main.TEAM.get(event.getPlayer()).update();
        }
    }
}
