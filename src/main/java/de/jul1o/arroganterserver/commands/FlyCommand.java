package de.jul1o.arroganterserver.commands;

import de.jul1o.arroganterserver.utils.PrefixUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.hasPermission("permission.fly")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.getAllowFlight()) {
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    p.sendMessage(PrefixUtil.getServerprefix() + "§aDu kannst nun fliegen!");
                } else {
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.sendMessage(PrefixUtil.getServerprefix() + "§aDu kannst nun nicht mehr fliegen!");
                }
            } else {
                sender.sendMessage(PrefixUtil.getServerprefix() + "§cDu musst ein Spieler sein um dies zu tun!");
            }
        } else {
            sender.sendMessage(PrefixUtil.getServerprefix() + "§cDafür hast du nicht genug Rechte!");
        }
        return false;
    }
}
