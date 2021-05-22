package de.jul1o.arroganterserver.commands;

import de.jul1o.arroganterserver.Main;
import de.jul1o.arroganterserver.utils.PrefixUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("spawn")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location location = new Location(Bukkit.getWorld(Main.worldspawn), 0.5, 100.0, 0.5);
                location.setYaw((float) 0.0);
                location.setPitch((float) 0.0);
                player.teleport(location);
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(PrefixUtil.getServerprefix() + "§7Du wurdest zum §aSpawn §7teleportiert!");

            }
        }
        return false;
    }

}
