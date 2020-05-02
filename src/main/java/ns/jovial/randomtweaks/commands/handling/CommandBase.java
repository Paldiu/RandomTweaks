package ns.jovial.randomtweaks.commands.handling;

import ns.jovial.randomtweaks.exception.PlayerNotFoundException;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public abstract class CommandBase {
    protected Plugin plugin;
    protected Server server;
    private CommandSender commandSender;
    private Class<?> commandClass;

    /**
     * Constructor! A simple initializer.
     */
    public CommandBase() {}

    /**
     * The onCommand method. This should be overridden in classes which extend this one.
     * Classes which extend this must also be annotated with CommandParameters.class
     *
     * @param sender The command sender (Console, CommandBlock, Player)
     * @param cmd The actual executable command
     * @param lbl The name of the command as executed (</command>).
     * @param args Any other arguments that could be provided after your command label.
     * @return true if the command completed successfully, false if it did not.
     */
    abstract public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args);

    /**
     * The TabComplete method. This should be overridden in classes which extend this one.
     * Classes which extend this must also be annotated with CommandParameters.class
     *
     * @param sender The command sender (Console, CommandBlock, Player)
     * @param cmd The actual executable command
     * @param lbl The name of the command as executed (</command>).
     * @param args Any other arguments that could be provided after your command label.
     * @return A list of all possible arguments to autocomplete.
     */
    abstract public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args);

    /**
     * This sets up a new instance of CommandBase for the command handler to process and execute the command.
     *
     * @param plugin The plugin to execute from
     * @param cs The command source, aka who sent the command (Console, CommandBlock, Player(
     * @param clazz This class (CommandBase.class)
     */
    public void setup(final Plugin plugin, final CommandSender cs, final Class<?> clazz) {
        this.plugin = plugin;
        this.server = plugin.getServer();
        this.commandSender = cs;
        this.commandClass = clazz;
    }

    /**
     * Gets a player based on a partial string.
     * For example, getPlayer("p") would return with the first online player whose name begins with the letter P.
     * If there are no players with the letter P in the beginning, it searches for the first player who has
     * the letter P in their name at all.
     *
     * @param partial The partial player name. Useful for executing commands, really.
     * @return The player from the provided string.
     * @throws PlayerNotFoundException If there is no such player.
     */
    public Player getPlayer(final String partial) throws PlayerNotFoundException {
        List<Player> matcher = server.matchPlayer(partial);
        if (matcher.isEmpty()) {
            for (Player p : server.getOnlinePlayers()) {
                if (p.getDisplayName().toLowerCase().contains(partial.toLowerCase())) {
                    return p;
                }
            }
            throw new PlayerNotFoundException();
        } else {
            return matcher.get(0);
        }
    }

    /**
     * Gets an offline player based on a partial name.
     * Basically does the same this as above except you cannot use Server#matchPlayer() here.
     * @param partial The offline players partial name.
     * @return An offline player.
     * @throws PlayerNotFoundException If there is no offline player to be found.
     */
    public OfflinePlayer getOfflinePlayer(final String partial) throws PlayerNotFoundException {
        for (OfflinePlayer player : server.getOfflinePlayers()) {
            if (player.getName().toLowerCase().contains(partial.toLowerCase())) {
                return player;
            }
        }
        throw new PlayerNotFoundException();
    }
}
