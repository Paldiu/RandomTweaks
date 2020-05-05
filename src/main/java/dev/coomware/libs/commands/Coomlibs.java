package dev.coomware.libs.commands;

import dev.coomware.libs.CoomLibs;
import dev.coomware.libs.commands.handling.CommandBase;
import dev.coomware.libs.commands.handling.CommandParameters;
import dev.coomware.libs.util.Completer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CommandParameters(description = "Get info about the plugin.",
        usage = "/<command>",
        aliases = "rt")
public class Coomlibs extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        boolean isConsole = false;
        if (!(sender instanceof Player)) {
            isConsole = true;
        }

        if (args.length == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("&8[&a")
            .append(CoomLibs.pluginName)
            .append("&8] &e v&a")
            .append(CoomLibs.pluginVersion)
            .append("&e by Jovial Development.\n");
            sb.append("This plugin has been designed as a library for Spigot developers.\n");
            sb.append("&cWhen run on the host server, it also provides other uses as well.\n");
            sb.append("&eThis plugin otherwise is simply a Library/API, and should be treated as such.\n");
            sb.append("Info on this plugin can be obtained by joining the discord: https://discord.gg/cdJhSVT");
            sender.sendMessage(colorize(sb.toString()));
        }

        return true;
    }

    private String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
        return (new Completer()).nameCompleter();
    }
}
