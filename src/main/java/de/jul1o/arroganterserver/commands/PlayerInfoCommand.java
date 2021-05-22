package de.jul1o.arroganterserver.commands;

import de.jul1o.arroganterserver.utils.PrefixUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerInfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (player.hasPermission("permission.playerinfo") && command.getName().equalsIgnoreCase("playerinfo")) {
            if (args.length == 0) {
                player.sendMessage(PrefixUtil.getServerprefix() + "§cNutzung: /playerinfo <name>");
            } else {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    player.sendMessage(PrefixUtil.getServerprefix() + "§7Dieser Spieler ist §cnicht §7online!");
                } else {
                    player.sendMessage("");
                    player.sendMessage(PrefixUtil.getPlayerinfoprefix());
                    player.sendMessage("");
                    player.sendMessage("§6IP§7: " + ChatColor.GREEN + target.getAddress().toString());
                    player.sendMessage("§6Name§7: " + ChatColor.GREEN + target.getDisplayName());
                    player.sendMessage("§6UUID§7: " + ChatColor.GREEN + target.getUniqueId().toString());
                    player.sendMessage("§6Welt§7: " + ChatColor.GREEN + target.getWorld().toString());
                    player.sendMessage("");
                }
            }
        } else {
            player.sendMessage("");
            player.sendMessage(PrefixUtil.getServerprefix() + "§cDafür hast du nicht genug Rechte!");
            player.sendMessage("");
        }
        return false;
    }
}
