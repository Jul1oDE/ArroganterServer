package de.jul1o.arroganterserver.commands;

import de.jul1o.arroganterserver.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NewsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("news")) {
            if (sender instanceof Player) {

                sender.sendMessage("§8");
                sender.sendMessage("§8§m----------------§r§8[§6§l N E W S §r§8]§m----------------");
                sender.sendMessage("§8");

                for (final String message : Data.NEWS) {
                    sender.sendMessage(Data.replace(message));
                }

                sender.sendMessage("§8");
                sender.sendMessage("§8§m----------------§r§8[§6§l N E W S §r§8]§m----------------");
                sender.sendMessage("§8");

            }
        }
        return false;
    }
}
