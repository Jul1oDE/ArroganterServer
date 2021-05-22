package de.jul1o.arroganterserver;

import de.jul1o.arroganterserver.commands.*;
import de.jul1o.arroganterserver.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;


public class Main extends JavaPlugin {

    public static String worldspawn = "Spawn";

    public static FileConfiguration CONFIG;
    public static Plugin PLUGIN;

    public static final HashMap<Player, Sidebar> SIDEBAR = new HashMap<>();
    public static final HashMap<Player, TeamManager> TEAM = new HashMap<>();

    @Override
    public void onEnable() {
        loadConfig(this);

        //Inventorys
        //Listener
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Bukkit.getPluginManager().registerEvents(new SidebarListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new BuildListener(), this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
        Bukkit.getPluginManager().registerEvents(new JumppadListener(), this);
        //Commands
        getCommand("gm").setExecutor(new GmCommand());
        getCommand("hilfe").setExecutor(new HelpCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("news").setExecutor(new NewsCommand());
        getCommand("playerinfo").setExecutor(new PlayerInfoCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("build").setExecutor(new BuildCommand());
        getCommand("create").setExecutor(new CreateCommand());
        getCommand("delete").setExecutor(new DeleteCommand());
        getCommand("world").setExecutor(new WorldCommand());

        Data.getTeams();

        new BukkitRunnable() {
            @Override
            public void run() {
                for (final TeamManager manager : TEAM.values()) {
                    manager.update();
                }
            }
        }.runTaskTimerAsynchronously(this, 20, 20);
    }

    private static void loadConfig(final Plugin plugin) {
        final InputStream defaultConfig = plugin.getResource("config.yml");
        plugin.getConfig().setDefaults(YamlConfiguration.loadConfiguration(defaultConfig));

        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        CONFIG = plugin.getConfig();
        PLUGIN = plugin;
    }

    public static ArrayList<String> getMaps() {
        final ArrayList<String> maps = new ArrayList<>();
        final File worlds = Bukkit.getWorldContainer();

        for (final File file : Objects.requireNonNull(worlds.listFiles())) {
            if (file.isDirectory()) {
                final File session = new File(file.getPath(), "session.lock");
                if (session.exists()) {
                    maps.add(file.getName());
                }
            }
        }

        Collections.sort(maps);

        return maps;
    }

}
