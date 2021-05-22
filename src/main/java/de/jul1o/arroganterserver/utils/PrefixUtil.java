package de.jul1o.arroganterserver.utils;

import org.bukkit.plugin.java.JavaPlugin;

public class PrefixUtil extends JavaPlugin {

    private static String serverprefix = "§7[§bAC§7] §8» §r";
    private static String playerinfoprefix = "§7[§cSpielerinfo§7] §r";

    public static String getServerprefix() {
        return serverprefix;
    }

    public static String getPlayerinfoprefix() {
        return playerinfoprefix;
    }
}
