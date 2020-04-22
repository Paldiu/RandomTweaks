package ns.jovial.randomtweaks.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PlayerListener implements Listener {
    public PlayerListener(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
