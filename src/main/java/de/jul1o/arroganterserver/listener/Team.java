package de.jul1o.arroganterserver.listener;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Team {

    private static final ArrayList<Team> teams = new ArrayList<>();

    private final String prefix;
    private final String suffix;
    private final String priority;
    private final String permission;

    public Team(final String prefix, final String suffix, final String priority, final String permission) {
        this.prefix = prefix.length() > 16 ? prefix.substring(0, 16) : prefix;
        this.suffix = suffix.length() > 16 ? suffix.substring(0, 16) : suffix;
        this.priority = priority.length() > 16 ? priority.substring(0, 16) : priority;
        this.permission = permission;

        teams.add(this);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getPriority() {
        return priority;
    }

    public String getPermission() {
        return permission;
    }

    public static Team getTeam(final Player player) {
        Team defaultTeam = null;
        for (final Team team : teams) {
            final String permission = team.getPermission();
            if (permission != null) {
                if (player.hasPermission(permission)) {
                    return team;
                }
            } else {
                defaultTeam = team;
            }
        }
        return defaultTeam;
    }
}
