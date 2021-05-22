package de.jul1o.arroganterserver.commands;

import de.jul1o.arroganterserver.utils.PrefixUtil;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GmCommand implements CommandExecutor {

    // --> /gm 0 - 1 - 2 - 3
    // --> /gm 0 1
    // --> /gm 0 1 2
    // --> /gm 0 1 2 3

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Du musst ein Spieler sein!");
            return false;
        }

        Player player = (Player) sender;
        if (args.length == 1) {
            if (!(sender.hasPermission("permission.gamemode"))) {
                sender.sendMessage(PrefixUtil.getServerprefix() + "§cDafür hast du nicht genug Rechte!");
            } else if (args[0].equalsIgnoreCase("0")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ChatColor.GREEN + "Dein Spielmodus wurde zu " + ChatColor.RED + "Survival" + ChatColor.GREEN + " geändert!");

            } else if (args[0].equalsIgnoreCase("1")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(ChatColor.GREEN + "Dein Spielmodus wurde zu " + ChatColor.RED + "Creative" + ChatColor.GREEN + " geändert!");

            } else if (args[0].equalsIgnoreCase("2")) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(ChatColor.GREEN + "Dein Spielmodus wurde zu " + ChatColor.RED + "Adventure" + ChatColor.GREEN + " geändert!");

            } else if (args[0].equalsIgnoreCase("3")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.GREEN + "Dein Spielmodus wurde zu " + ChatColor.RED + "Spectator" + ChatColor.GREEN + " geändert!");

            } else {
                sender.sendMessage(PrefixUtil.getServerprefix() + ChatColor.RED + "Nutzung: /gm <0 ┃ 1 ┃ 2 ┃ 3>");
            }
            return false;
        }
        return false;
    }
}

