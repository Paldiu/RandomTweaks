package dev.coomware.libs;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import dev.coomware.libs.commands.Coomlibs;
import dev.coomware.libs.commands.handling.CommandHandler;
import dev.coomware.libs.commands.handling.CommandLoader;
import dev.coomware.libs.config.RTConfig;
import dev.coomware.libs.listener.PlayerListener;
import dev.coomware.libs.listener.WorldListener;
import dev.coomware.libs.reflect.Reflector;
import dev.coomware.libs.timer.RecurringTask;
import dev.coomware.libs.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoomLibs extends JavaPlugin {
    public static CoomLibs plugin;
    public static String pluginName;
    public static String pluginVersion;
    public static Server server;
    //
    public static Timer timer;
    public static Reflector reflect;
    //
    public static WorldListener wl;
    public static PlayerListener pl;
    //
    public static RTConfig config;

    @Override
    public void onLoad() {
        // ======================== Basic Loading ======================== \\
        plugin = this;
        server = plugin.getServer();
        pluginName = this.getName();
        pluginVersion = this.getDescription().getVersion();
        // ======================== Timer ======================== \\
        timer = new Timer();
        // ======================== Reflector ======================== \\
        reflect = new Reflector(Coomlibs.class);
    }
    
    @Override
    public void onEnable() {
        // ======================== Listeners ======================== \\
        wl = new WorldListener(this);
        pl = new PlayerListener(this);

        // ======================== CONFIG ======================== \\
        config = new RTConfig(this);
        config.load();

        // ======================== Scheduled Tasks ======================== \\
        new RecurringTask(plugin).runTaskTimer(plugin, timer.clean(), timer.clean());
        new BukkitRunnable() {
            @Override
            public void run() {
                CommandLoader.getInstance().scan();
            }
        }.runTaskLater(plugin, timer.second());

        // ======================== Logger! ======================== \\
        Bukkit.getLogger().log(Level.INFO,
                String.format("[%s] v%s by the Jovial Development Group has been enabled!",
                pluginName, pluginVersion));
    }
    
    @Override
    public void onDisable() {
        // ======================== De-initialization ======================== \\
        server.getScheduler().cancelTasks(plugin);
        config.save();
        plugin = null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return CommandHandler.handle(reflect, sender, command, label, args);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return CommandHandler.onTabComplete(reflect, sender, command, alias, args);
    }
}