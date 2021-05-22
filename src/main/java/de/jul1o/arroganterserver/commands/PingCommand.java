package de.jul1o.arroganterserver.commands;

import de.jul1o.arroganterserver.utils.PrefixUtil;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                player.sendMessage(PrefixUtil.getServerprefix() + "§7Dein Ping: " + ChatColor.RED + getPing(player) + "ms");
            } else if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    sender.sendMessage(PrefixUtil.getServerprefix() + "§7Ping von " + ChatColor.RED + args[0] + "§7: " + ChatColor.RED + getPing(Bukkit.getPlayer(args[0])) + "§cms");
                } else {
                    sender.sendMessage(PrefixUtil.getServerprefix() + "§7Der Spieler " + ChatColor.RED + Bukkit.getPlayer(args[0]) + "§7ist nicht online.");
                }
            } else {
                sender.sendMessage(PrefixUtil.getServerprefix() + "§cNutzung: /ping");
            }
        } else {
            sender.sendMessage(PrefixUtil.getServerprefix() + "§cDu musst ein Spieler sein!");
        }

        return false;
    }

    public static Integer getPing(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        EntityPlayer ping = craftPlayer.getHandle();
        return ping.ping;
    }
}
