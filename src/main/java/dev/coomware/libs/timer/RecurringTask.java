package dev.coomware.libs.timer;

import dev.coomware.libs.tweaks.SupportedEntities;
import org.bukkit.Server;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class RecurringTask extends BukkitRunnable {
    private final Plugin plugin;
    private final Server server;

    private static Long lastRan = null;

    public RecurringTask(Plugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    public static Long getLastRan() { return lastRan; }

    @Override
    public void run() {
        lastRan = System.currentTimeMillis();

        Map<String, EntityType> typeMap = SupportedEntities.getAllClearableItems();

            server.getWorlds().forEach(world -> {
                if (world.getWeatherDuration() > 0) {
                    world.setThundering(false);
                    world.setWeatherDuration(0);
                }

                if (world.getThunderDuration() > 0) {
                    world.setStorm(false);
                    world.setThunderDuration(0);
                }

                world.getEntities().forEach(entity -> {
                   typeMap.values().forEach(type -> {
                       if (entity.getType().equals(type)) {
                           entity.remove();
                       }
                   });
                });
            });
    }
}
