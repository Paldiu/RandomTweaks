package ns.jovial.randomtweaks.commands.handling;

import ns.jovial.randomtweaks.RandomTweaks;
import ns.jovial.randomtweaks.commands.RTweaks;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {
    private static final String COMMAND_PATH = RandomTweaks.reflect.getDefPackage().getName();

    public static boolean handle(CommandSender sender, Command cmd, String lbl, String[] args) {
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
            final ClassLoader loader = RandomTweaks.reflect.getDefClass().getClassLoader();
            base = (CommandBase) loader.loadClass( COMMAND_PATH + cmd.getName()).newInstance();
            base.setup(RandomTweaks.plugin, sender, base.getClass());
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
        }

        return true;
    }

    public @Nullable
    static List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String lbl, @NotNull String[] args) {
        final CommandBase base;
        try {
            final ClassLoader loader = RandomTweaks.reflect.getDefClass().getClassLoader();
            base = (CommandBase) loader.loadClass(COMMAND_PATH + "." + cmd.getName()).newInstance();
            base.setup(RandomTweaks.plugin, sender, base.getClass());
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
