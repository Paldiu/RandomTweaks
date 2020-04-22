package ns.jovial.randomtweaks.commands.handling;

import ns.jovial.randomtweaks.RandomTweaks;
import ns.jovial.randomtweaks.reflect.Reflector;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class Dynamic extends Command implements PluginIdentifiableCommand {
    private final Commander commander;

    /**
     * A mock command class for holding command instances.
     * @param commander A new commander instance.
     */
    Dynamic(Commander commander) {
        super(commander.getCommandName(), commander.getDescription(), commander.getUsage(), commander.getAliases());

        this.commander = commander;
    }

    /**
     * This is our actual executor. Notice how we avoid the need to use the default Command Executor entirely.
     * @param sender The command sender (Console, CommandBlock, Player)
     * @param lbl The command name
     * @param args Any arguments that would be supplied after the command label.
     * @return True if successful, false if not.
     */
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String lbl, @NotNull String[] args) {
        boolean success = false;
        Iterator it;
        while ((it = RandomTweaks.reflect.getPlugins().iterator()).hasNext()) {
            if (success) break;

            for(Plugin plugin : RandomTweaks.reflect.getPlugins()) {
                if (!plugin.isEnabled()) {
                    it.next();
                    continue;
                }

                try {
                    success = plugin.onCommand(sender, this, lbl, args);
                } catch (Throwable ex) {
                    it.next();
                    continue;
                }

                if (!success && getUsage().length() > 0) {
                    for (String string : getUsage().replace("<command>", lbl).split(" \n")) {
                        sender.sendMessage(string);
                    }
                } else if (!success && !it.hasNext()) {
                    throw new CommandException("Unhandled exception executing command '" + lbl + "'in plugin " + getPlugin().getDescription().getFullName());
                }
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
