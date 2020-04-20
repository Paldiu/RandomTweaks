package ns.jovial.randomtweaks.commands;

import ns.jovial.randomtweaks.RandomTweaks;
import ns.jovial.randomtweaks.commands.handling.CommandBase;
import ns.jovial.randomtweaks.commands.handling.CommandParameters;
import ns.jovial.randomtweaks.listener.WorldListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@CommandParameters(description = "Get info about the plugin.",
        usage = "/<command> [listenerType] <on | off>",
        aliases = "rt")
public class RTweaks extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        boolean isConsole = false;
        if (!(sender instanceof Player)) {
            isConsole = true;
        }

        if (args.length == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("&8[&a")
            .append(RandomTweaks.pluginName)
            .append("&8] &e v&a")
            .append(RandomTweaks.pluginVersion)
            .append("&e by Jovial Development.\n");
            sb.append("This plugin has been designed as a library for Spigot developers.\n");
            sb.append("&cWhen run on the host server, it also provides other uses as well.\n");
            sb.append("&eThis plugin otherwise is simply a Library/API, and should be treated as such.\n");
            sb.append("Info on this plugin can be obtained by joining the discord: https://discord.gg/cdJhSVT");
            sender.sendMessage(colorize(sb.toString()));
        } else if (args.length == 2) {
            switch (args[0]) {
                case "world":
                    switch (args[1]) {
                        case "on":
                            if (RandomTweaks.wl.isEnabled()) {
                                sender.sendMessage("That is already enabled!");
                                return true;
                            }
                            RandomTweaks.wl.setEnabled(true);
                           break;
                        case "off":
                            if (RandomTweaks.wl.isEnabled()) {
                                sender.sendMessage("That is already disabled!");
                                return true;
                            }
                            RandomTweaks.wl.setEnabled(false);
                            break;
                    }
                    break;
                case "player":
                    switch (args[1]) {
                        case "on":
                            if (RandomTweaks.pl.isEnabled()) {
                                sender.sendMessage("That is already enabled!");
                                return true;
                            }
                            RandomTweaks.pl.setEnabled(true);
                            break;
                        case "off":
                            if (!RandomTweaks.pl.isEnabled()) {
                                sender.sendMessage("That is already disabled!");
                                return true;
                            }
                            RandomTweaks.pl.setEnabled(false);
                            break;
                    }
                    break;
            }
        } else {
            return false;
        }

        return true;
    }

    private String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        List<String> newList = new ArrayList<>();
        if (args[0].startsWith("\\s") || args[0].startsWith("")) {
            newList.add("world");
            newList.add("player");
            newList.add("server");
            newList.add("entity");
            newList.add("block");
            return newList;
        }

        if (args[1].startsWith("\\s") || args[1].startsWith("")) {
            newList.add("on");
            newList.add("off");
            return newList;
        }

        return null;
    }
}
