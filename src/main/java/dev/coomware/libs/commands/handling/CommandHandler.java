package dev.coomware.libs.commands.handling;

import dev.coomware.libs.reflect.Reflector;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CommandHandler {

    /**
     * The actual command handler.
     * This will create a new CommandBase instance every time a command has been run.
     * To use the command handler, simply override the default onCommand method in your Main class,
     * and have it return CommandHandler#handle().
     *
     * @param reflect The reflector instance. This should be initialized in your main class.
     * @param sender The command sender (Console, CommandBlock, or Player)
     * @param cmd The actual executable command instance.
     * @param lbl The name of the command (/<command>)
     * @param args Any variable arguments that could also be provided after the command label.
     * @return true if the command successfully completed, false if it did not.
     */
    public static boolean handle(Reflector reflect, CommandSender sender, Command cmd, String lbl, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            Bukkit.getLogger().info(String.format("[PLAYER COMMAND] %s (%s): /%s %s",
                    player.getName(),
                    ChatColor.stripColor(player.getDisplayName()),
                    lbl,
                    StringUtils.join(args, ' ')));
        } else {
            Bukkit.getLogger().info(String.format("[CONSOLE COMMAND] %s: /%s %s",
                    sender.getName(),
                    lbl,
                    StringUtils.join(args, ' ')));
        }
        final CommandBase base;
        try {
            final ClassLoader loader = reflect.getDefClass().getClassLoader();
            base = (CommandBase) loader.loadClass( reflect.getDefPackage().getName() + "." + cmd.getName()).newInstance();
            base.setup(reflect.getPlugin(), sender, base.getClass());
        } catch (Exception ex) {
            Bukkit.getLogger().severe("Could not load command: " + cmd.getName());
            Bukkit.getLogger().severe(ex.getMessage());

            sender.sendMessage(ChatColor.RED + "Command error! Could not load command: " + cmd.getName());
            return true;
        }

        try {
            return base.onCommand(sender, cmd, lbl, args);
        } catch (Exception ex) {
            Bukkit.getLogger().severe("Command Error: " + lbl);
            Bukkit.getLogger().severe(ex.getMessage());
            sender.sendMessage(ChatColor.RED + "COMMAND ERROR: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Exactly the same thing as the handle method, except that this returns any possible arguments provided for the command.
     * This must be completed with the onCommand method from BaseCommand.class when creating new commands.
     *
     * @param reflect The reflector instance. This should be initialized in your main class.
     * @param sender The command sender (Console, CommandBlock, or Player)
     * @param cmd The actual executable command instance.
     * @param lbl The name of the command (/<command>).
     * @param args Any variable arguments that could also be provided after the command label.
     * @return A new instance of a List<Object> containing the different possible arguments for each command.
     */
    public @Nullable
    static List<String> onTabComplete(@NotNull Reflector reflect, @NotNull CommandSender sender, @NotNull Command cmd, @NotNull String lbl, @NotNull String[] args) {
        final CommandBase base;
        try {
            final ClassLoader loader = reflect.getDefClass().getClassLoader();
            base = (CommandBase) loader.loadClass(reflect.getDefPackage().getName() + "." + cmd.getName()).newInstance();
            base.setup(reflect.getPlugin(), sender, base.getClass());
        } catch (Exception ex) {
            return null;
        }

        try {
            return base.onTabComplete(sender, cmd, lbl, args);
        } catch (Exception ex) {
           return null;
        }
    }
}
