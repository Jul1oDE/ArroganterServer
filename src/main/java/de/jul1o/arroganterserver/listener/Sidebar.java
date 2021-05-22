package de.jul1o.arroganterserver.listener;

import de.jul1o.arroganterserver.Main;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

// Ranks: Admin, Clan, Kyudo, Spieler

public class Sidebar {

    private final Scoreboard board;
    private final Player player;
    private final PlayerConnection connection;

    private ScoreboardObjective objective = null;

    public Sidebar(final Player player) {
        this.board = new Scoreboard();
        this.player = player;
        this.connection = ((CraftPlayer) player).getHandle().playerConnection;
    }

    private void createObjective() {
        objective = board.registerObjective("sidebar", IScoreboardCriteria.b);

        objective.setDisplayName("§b§lArroganteCrew");

        connection.sendPacket(new PacketPlayOutScoreboardObjective(objective, 1));
        connection.sendPacket(new PacketPlayOutScoreboardObjective(objective, 0));
        connection.sendPacket(new PacketPlayOutScoreboardDisplayObjective(1, objective));

        status();
    }

    private void updateObjective(final String name) {
        objective.setDisplayName(name);

        connection.sendPacket(new PacketPlayOutScoreboardObjective(objective, 2));
    }

    private void createTeam(final int score, final String name, final String prefix, final String suffix, final String player) {
        final ScoreboardTeam team = board.createTeam(name);

        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.getPlayerNameSet().add(player);

        final ScoreboardScore scoreboardScore = new ScoreboardScore(board, objective, player);
        scoreboardScore.setScore(score);

        connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 1));
        connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 0));

        connection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore));
    }

    private void updateTeam(final ScoreboardTeam team, final String prefix, final String suffix) {
        team.setPrefix(prefix);
        team.setSuffix(suffix);

        connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 2));
    }

    private void createScore(final int score, final String message) {
        final ScoreboardScore scoreboardScore = new ScoreboardScore(board, objective, message);
        scoreboardScore.setScore(score);

        connection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore));
    }

    public void update() {
        for (final ScoreboardTeam teams : board.getTeams()) {
            switch (teams.getName()) {
                case "rank":
                    updateTeam(teams, "§8➥ ", getRank());
                    break;
                case "world":
                    updateTeam(teams, "§8➥ ", player.getWorld().getName());
                    break;
                case "players":
                    updateTeam(teams, "§8➥ ", String.valueOf(Bukkit.getOnlinePlayers().size()));
                    break;
            }
        }
    }

    public void set() {
        createObjective();

        createScore(8, "§a");
        createScore(7, "§fRang:");
        createTeam(6, "rank", "§8» ", getRank(), "§b");
        createScore(5, "§c");
        createScore(4, "§fDeine Welt:");
        createTeam(3, "world", "§8» ", player.getWorld().getName(), "§d§e");
        createScore(2, "§e");
        createScore(1, "§fSpielerzahl:");
        createTeam(0, "players", "§8» ", String.valueOf(Bukkit.getOnlinePlayers().size()), "§f§a");
    }

    private String getRank() {
        if (player.hasPermission("scoreboard.admin")) {
            return "§4Admin";
        } else if (player.hasPermission("scoreboard.clan")) {
            return "§bClan";
        } else if (player.hasPermission("scoreboard.kyudo")) {
            return "§3Kyudo";
        } else if (player.hasPermission("scoreboard.friend")) {
            return "§6Friend";
        } else {
            return "§7Spieler";
        }
    }

    private void status() {
        final int[] status = {0};
        new BukkitRunnable() {
            @Override
            public void run() {
                if (objective == null) {
                    this.cancel();
                    return;
                }

                update();
                updateObjective(getStatus(status[0]));

                status[0]++;
                if (status[0] > 60) {
                    status[0] = 0;
                }
            }
        }.runTaskTimerAsynchronously(Main.PLUGIN, 0, 2);
    }

    private String getStatus(final int status) {
        final char[] messages = "ArroganteCrew".toCharArray();
        final StringBuilder message = new StringBuilder();

        if (status < 40 + messages.length * 2 && status >= 40) {
            message.append("§f§l");
            for (int i = 0; i < messages.length; i++) {
                if (i == status - 40) {
                    message.append("§3§l").append(messages[i]);
                } else if (i == status - 39) {
                    message.append("§b§l").append(messages[i]);
                } else {
                    message.append(messages[i]);
                }
            }
        } else if ((status >= 43 + messages.length * 2 && status <= 45 + messages.length * 2)) {
            message.append("§f§l");
            for (final char chars : messages) {
                message.append(chars);
            }
        } else {
            message.append("§b§l");
            for (final char chars : messages) {
                message.append(chars);
            }

        }

        return message.toString();
    }
}
