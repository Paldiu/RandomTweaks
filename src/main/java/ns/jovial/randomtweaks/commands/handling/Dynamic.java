package ns.jovial.randomtweaks.commands.handling;

import ns.jovial.randomtweaks.RandomTweaks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Dynamic extends Command implements PluginIdentifiableCommand {
    private final Commander commander;

    Dynamic(Commander commander) {
        super(commander.getCommandName(), commander.getDescription(), commander.getUsage(), commander.getAliases());

        this.commander = commander;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String lbl, @NotNull String[] args) {
        boolean success;

        if (!getPlugin().isEnabled()) {
            return false;
        }

        try {
            success = getPlugin().onCommand(sender, this, lbl, args);
        } catch (Throwable ex) {
            throw new CommandException("Unhandled exception executing command '" + lbl + "'in plugin " + getPlugin().getDescription().getFullName(), ex);
        }

        if (!success && getUsage().length() > 0) {
            for (String string : getUsage().replace("<command>", lbl).split(" \n")) {
                sender.sendMessage(string);
            }
        }
        return success;
    }

    @NotNull
    @Override
    public Plugin getPlugin() {
        return RandomTweaks.plugin;
    }

    public Commander getCommander() {
        return commander;
    }
}
