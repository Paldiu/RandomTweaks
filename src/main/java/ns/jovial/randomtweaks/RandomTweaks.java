package ns.jovial.randomtweaks;

import java.util.List;
import java.util.logging.Level;

import ns.jovial.randomtweaks.commands.RTweaks;
import ns.jovial.randomtweaks.commands.handling.CommandHandler;
import ns.jovial.randomtweaks.commands.handling.CommandLoader;
import ns.jovial.randomtweaks.listener.PlayerListener;
import ns.jovial.randomtweaks.listener.WorldListener;
import ns.jovial.randomtweaks.reflect.Reflector;
import ns.jovial.randomtweaks.timer.RecurringTask;
import ns.jovial.randomtweaks.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RandomTweaks extends JavaPlugin {
    public static RandomTweaks plugin;
    public static String pluginName;
    public static String pluginVersion;
    public static Server server;
    //
    public static Timer timer;
    public static Reflector reflect;
    //
    public static WorldListener wl;
    public static PlayerListener pl;

    @Override
    public void onLoad() {
        // Basic Loading
        plugin = this;
        server = plugin.getServer();
        pluginName = this.getName();
        pluginVersion = this.getDescription().getVersion();
        // Timer
        timer = new Timer();
        // Reflector
        reflect = new Reflector(RTweaks.class);
    }
    
    @Override
    public void onEnable() {
        wl = new WorldListener(this);
        pl = new PlayerListener(this);

        new RecurringTask(plugin).runTaskTimer(plugin, timer.clean(), timer.clean());

        new BukkitRunnable() {
            @Override
            public void run() {
                CommandLoader.getInstance().scan();
            }
        }.runTaskLater(plugin, timer.second());

        Bukkit.getLogger().log(Level.INFO,
                String.format("[%s] v%s by the Jovial Development Group has been enabled!",
                pluginName, pluginVersion));
    }
    
    @Override
    public void onDisable() {
        server.getScheduler().cancelTasks(plugin);
        plugin = null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return CommandHandler.handle(sender, command, label, args);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return CommandHandler.onTabComplete(sender, command, alias, args);
    }
}