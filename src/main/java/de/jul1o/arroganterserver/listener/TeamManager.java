package de.jul1o.arroganterserver.listener;

import de.jul1o.arroganterserver.Data;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class TeamManager {

    private final Player player;
    private final PlayerConnection connection;
    private final Scoreboard scoreboard;

    public TeamManager(final Player player) {
        this.player = player;
        this.connection = ((CraftPlayer) player).getHandle().playerConnection;
        this.scoreboard = new Scoreboard();

        for (final Player players : Bukkit.getOnlinePlayers()) {
            create(players);
        }

        createPlayerList();
    }

    private void create(final Player player) {
        final ScoreboardTeam team = scoreboard.createTeam(getName(player));

        team.setPrefix(Team.getTeam(player).getPrefix());
        team.setSuffix(Team.getTeam(player).getSuffix());
        team.getPlayerNameSet().add(player.getName());

        connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 0));
    }

    private void createPlayerList() {
        final PacketPlayOutPlayerListHeaderFooter playerList = new PacketPlayOutPlayerListHeaderFooter();
        setField(playerList, "a", getJson(Data.HEADER));
        setField(playerList, "b", getJson(Data.FOOTER));

        connection.sendPacket(playerList);
    }

    public void update() {
        for (final Player players : Bukkit.getOnlinePlayers()) {
            final ScoreboardTeam team = scoreboard.getTeam(getName(players));
            if (team != null) {
                if (!check(players, team)) {
                    connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 1));
                    scoreboard.removeTeam(team);

                    create(players);
                } else if (team.getPlayerNameSet().contains(players.getName())) {
                    connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 1));
                    scoreboard.removeTeam(team);

                    create(players);
                }
            } else {
                create(players);
            }
        }

        createPlayerList();
    }

    private String getName(final Player player) {
        final String priority = Team.getTeam(player).getPriority();
        final String name = player.getName().length() > 4 ? player.getName().substring(0, 4) : player.getName();
        final String uuid = player.getUniqueId().toString().substring(0, 12 - priority.length());

        return priority + name + uuid;
    }

    private boolean check(final Player player, final ScoreboardTeam team) {
        return team.getName().equals(getName(player));
    }

    private IChatBaseComponent getJson(final String text) {
        final String info = text.replace("%players%", String.valueOf(Bukkit.getOnlinePlayers().size())).replace("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers())).replace("%world%", player.getWorld().getName());

        return IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + info + "\"}");
    }

    private void setField(final Object object, final String name, final Object type) {
        try {
            final Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(object, type);
        } catch (final NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }
}
