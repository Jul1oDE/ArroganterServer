package de.jul1o.arroganterserver.commands;

import de.jul1o.arroganterserver.Data;
import de.jul1o.arroganterserver.Main;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class CreateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender.hasPermission("build.world.create")) {
            if (args.length == 2) {
                for (final String worlds : Main.getMaps()) {
                    if (worlds.equalsIgnoreCase(args[0])) {
                        sender.sendMessage(Data.WORLD_CREATE_EXISTS);
                        return true;
                    }
                }

                String name = args[0];
                for (final char letter : "abcdefghijklmnopqrstuvwxyzäöüß0123456789_-".toCharArray()) {
                    name = name.replace(String.valueOf(letter), "").replace(String.valueOf(letter).toUpperCase(), "");
                }

                if (name.length() > 0) {
                    sender.sendMessage(Data.WORLD_CREATE_SYMBOL);
                } else if (args[1].equalsIgnoreCase("normal")) {
                    final World world = create(args[0], WorldType.NORMAL);
                    if (sender instanceof Player) {
                        ((Player) sender).teleport(world.getSpawnLocation());
                        sender.sendMessage(Data.WORLD_TELEPORT.replace("%world%", world.getName()));
                    }
                } else if (args[1].equalsIgnoreCase("flat")) {
                    final World world = create(args[0], WorldType.FLAT);
                    if (sender instanceof Player) {
                        ((Player) sender).teleport(world.getSpawnLocation());
                        sender.sendMessage(Data.WORLD_TELEPORT.replace("%world%", world.getName()));
                    }
                } else if (args[1].equalsIgnoreCase("void")) {
                    final World world = create(args[0], WorldType.VOID);
                    if (sender instanceof Player) {
                        ((Player) sender).teleport(world.getSpawnLocation());
                        sender.sendMessage(Data.WORLD_TELEPORT.replace("%world%", world.getName()));
                    }
                } else {
                    sender.sendMessage(Data.WORLD_CREATE_HELP);
                }
            } else {
                sender.sendMessage(Data.WORLD_CREATE_HELP);
            }
        } else {
            sender.sendMessage(Data.COMMAND_PERMISSION);
        }

        return true;
    }

    private World create(final String name, final WorldType type) {
        final WorldCreator creator = new WorldCreator(name);
        creator.generateStructures(false);
        creator.type(type.getType());
        if (type == WorldType.VOID) {
            creator.generator(new ChunkGenerator() {
                @Override
                public byte[] generate(final org.bukkit.World world, final Random random, final int x, final int z) {
                    return new byte[32768];
                }
            });
        }
        final org.bukkit.World world = creator.createWorld();
        world.setKeepSpawnInMemory(false);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("randomTickSpeed", "0");
        world.setTime(6000);
        world.setThundering(false);
        world.setStorm(false);

        return world;
    }

    private enum WorldType {
        NORMAL(org.bukkit.WorldType.NORMAL),
        FLAT(org.bukkit.WorldType.FLAT),
        VOID(org.bukkit.WorldType.FLAT);

        private final org.bukkit.WorldType type;

        WorldType(final org.bukkit.WorldType type) {
            this.type = type;
        }

        public org.bukkit.WorldType getType() {
            return type;
        }
    }
}
