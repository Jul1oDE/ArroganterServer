package de.jul1o.arroganterserver.commands;

import de.jul1o.arroganterserver.utils.PrefixUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            sender.sendMessage("");
            sender.sendMessage(PrefixUtil.getServerprefix() + "§7Folgende Befehle sind verfügbar:");
            sender.sendMessage(PrefixUtil.getServerprefix() + "  §7/world §8» §aÖffnet die Weltenübersicht!");
            sender.sendMessage(PrefixUtil.getServerprefix() + "  §7/create <NAME> <NORMAL ┃ FLAT ┃ VOID>");
            sender.sendMessage(PrefixUtil.getServerprefix() + "  §8» §aErstelle eine Welt!");
            sender.sendMessage(PrefixUtil.getServerprefix() + "  §7/delete §8» §aLösche eine Welt!");
            sender.sendMessage(PrefixUtil.getServerprefix() + "  §7/spawn §8» §aTeleportiere dich zum Spawn!");
            sender.sendMessage("");
        }
        return false;
    }
}
