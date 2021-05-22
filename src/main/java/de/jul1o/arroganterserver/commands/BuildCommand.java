package de.jul1o.arroganterserver.commands;

import de.jul1o.arroganterserver.utils.PrefixUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BuildCommand implements CommandExecutor {

    public static ArrayList<Player> allowed = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("permission.build")) {
                if (allowed.contains(player)) {
                    allowed.remove(player);
                    player.sendMessage(PrefixUtil.getServerprefix() + "§cDu kannst nun nicht mehr bauen!");
                } else {
                    allowed.add(player);
                    player.sendMessage(PrefixUtil.getServerprefix() + "§aDu kannst nun bauen!");
                }
            } else {
                player.sendMessage(PrefixUtil.getServerprefix() + "§cDafür hast du nicht genug Rechte!");
            }

        } else {
            Bukkit.getConsoleSender().sendMessage(PrefixUtil.getServerprefix() + "§cDiser Befehel kann nur als Spieler benutzt werden!");
        }

        return false;
    }
}
