package ns.jovial.randomtweaks.commands.handling;

import ns.jovial.randomtweaks.RandomTweaks;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class CommandBase {
    protected RandomTweaks plugin;
    protected Server server;
    private CommandSender commandSender;
    private Class<?> commandClass;

    public CommandBase() {}

    abstract public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args);

    abstract public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args);

    public void setup(final RandomTweaks plugin, final CommandSender cs, final Class<?> clazz) {
        this.plugin = plugin;
        this.server = plugin.getServer();
        this.commandSender = cs;
        this.commandClass = clazz;
    }

    public Player getPlayer(final String partial) {
        List<Player> matcher = server.matchPlayer(partial);
        if (matcher.isEmpty()) {
            for (Player p : server.getOnlinePlayers()) {
                if (p.getDisplayName().toLowerCase().contains(partial.toLowerCase())) {
                    return p;
                }
            }
            return null;
        } else {
            return matcher.get(0);
        }
    }
}
