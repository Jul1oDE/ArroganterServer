package de.jul1o.arroganterserver;

import de.jul1o.arroganterserver.listener.Team;
import de.jul1o.arroganterserver.utils.PrefixUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.List;
import java.util.Set;

public class Data {

    public static String replace(final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static final List<String> NEWS = Main.CONFIG.getStringList("news");

    public static final Location SPAWN = new Location(Bukkit.getWorld("Spawn"), 0.5, 100.0, 0.5, 0.0F, 0.0F);

    public static final String HEADER = replace(Main.CONFIG.getString("tablist.header"));
    public static final String FOOTER = replace(Main.CONFIG.getString("tablist.footer"));

    public static final String COMMAND_PERMISSION = replace(PrefixUtil.getServerprefix() + "&cUnzureichende Rechte!");
    public static final String COMMAND_PLAYER = replace(PrefixUtil.getServerprefix() + "&cDiesen Befehl können nur Spieler ausführen!");

    public static final String WORLD_INVENTORY = replace("&7Wähle eine Welt aus");
    public static final String WORLD_NEXT = replace("&6Weiter");
    public static final String WORLD_BACK = replace("&6Zurück");
    public static final String WORLD_TELEPORT = replace(PrefixUtil.getServerprefix() + "&7Du wurdest auf die Welt &e%world% &7teleportiert!");

    public static final String WORLD_CREATE_EXISTS = replace(PrefixUtil.getServerprefix() + "&cDiese Welt existiert bereits!");
    public static final String WORLD_CREATE_SYMBOL = replace(PrefixUtil.getServerprefix() + "&cDer Name enthält ein ungültiges Zeichen!");
    public static final String WORLD_CREATE_HELP = replace(PrefixUtil.getServerprefix() + "&7Nutze &e/create <Welt> <Normal/Flat/Void>&7!");

    public static final String WORLD_DELETE_INVENTORY = replace("&7Welche Welt löschen?");
    public static final String WORLD_DELETE_CONFIRM = replace("&7Welt wirklich löschen?");
    public static final String WORLD_DELETE_CONFIRM_FAKE = replace("&e%world% &7wirklich löschen?");
    public static final String WORLD_DELETE_ACCEPT = replace("&aBestätigen");
    public static final String WORLD_DELETE_DENY = replace("&cAbbrechen");
    public static final String WORLD_DELETE = replace(PrefixUtil.getServerprefix() + "&7Die Welt &e%world% &7wurde gelöscht!");
    public static final String WORLD_DELETE_ERROR = replace(PrefixUtil.getServerprefix() + "&cDu kannst diese Welt nicht löschen!");
    public static final String WORLD_DELETE_HELP = replace(PrefixUtil.getServerprefix() + "&7Nutze &e/delete <Welt>&7!");

    public static void getTeams() {
        final Set<String> teams = Main.CONFIG.getConfigurationSection("teams").getKeys(false);

        for (final String team : teams) {
            final String prefix = Main.CONFIG.getString("teams." + team + ".prefix");
            final String suffix = Main.CONFIG.getString("teams." + team + ".suffix");
            final String priority = Main.CONFIG.getString("teams." + team + ".priority");
            final String permission = Main.CONFIG.getBoolean("teams." + team + ".default") ? null : Main.CONFIG.getString("teams." + team + ".permission");

            new Team(replace(prefix), replace(suffix), priority, permission);
        }
    }
}
