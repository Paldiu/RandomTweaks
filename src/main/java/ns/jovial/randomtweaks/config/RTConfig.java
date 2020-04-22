package ns.jovial.randomtweaks.config;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RTConfig extends YamlConfiguration {
    private Plugin plugin;
    private File file;

    public RTConfig(Plugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            defaults();
        }
    }

    public void load() {
        try {
            super.load(file);
        } catch (InvalidConfigurationException e) {
            plugin.getLogger().severe(ExceptionUtils.getStackTrace(e));
        } catch (FileNotFoundException e) {
            plugin.getLogger().severe(ExceptionUtils.getStackTrace(e));
        } catch (IOException e) {
            plugin.getLogger().severe(ExceptionUtils.getStackTrace(e));
        }
    }

    public void save() {
        try {
            super.save(file);
        } catch (IOException ex) {
            plugin.getLogger().severe(ExceptionUtils.getStackTrace(ex));
        }
    }

    private void defaults() {
        plugin.saveResource("config.yml", false);
    }
}
