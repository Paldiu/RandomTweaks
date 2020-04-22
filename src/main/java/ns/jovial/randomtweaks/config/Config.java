package ns.jovial.randomtweaks.config;

import ns.jovial.randomtweaks.RandomTweaks;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.logging.Level;

public class Config extends YamlConfiguration {
    private final Plugin plugin;
    private final File config;
    private final boolean copyDefaults;

    /**
     * Creates a new configuration instance.
     * @param plugin The plugin to which the config belongs.
     * @param fileName The file name of the configuration file.
     * @param copyDefaults If the defaults should be loaded and loaded from a config in the plugin jar-file.
     */
    public Config(Plugin plugin, String fileName, boolean copyDefaults) {
        this(plugin, RandomTweaks.reflect.getPluginFile(plugin, fileName), copyDefaults);
    }

    /**
     * Creates a new configuration instance.
     * @param plugin The plugin to which the config belongs.
     * @param file The configuration file in question.
     * @param copyDefaults If the defaults should be loaded and loaded from a config in the plugin jar-file.
     */
    public Config(Plugin plugin, File file, boolean copyDefaults) {
        this.plugin = plugin;
        this.config = file;
        this.copyDefaults = copyDefaults;
    }

    /**
     * Saves the configuration to the predefined file.
     *
     * @see YamlConfiguration#save(String)
     */
    public void save() {
        try {
            super.save(config);
        } catch (IOException ex) {
            plugin.getLogger().severe("Could not load configuration file: " + config.getName());
            plugin.getLogger().severe(ExceptionUtils.getStackTrace(ex));
        }
    }

    /**
     * Loads the configuration from the predefined file.
     * If #copyDefaults has been set to true, it will load from the jar-file of the owning plugin.
     */
    public void load() {
        Writer writer = new Writer();
        try {
            if (copyDefaults) {
                if (!config.exists()) {
                    config.getParentFile().mkdirs();
                    try {
                        writer.copy(plugin.getResource(config.getName()), config);
                    } catch (IOException ex) {
                        plugin.getLogger().severe("Could not write default configuration file: " + config.getName());
                        plugin.getLogger().severe(ExceptionUtils.getStackTrace(ex));
                    }
                    plugin.getLogger().info("Installed default configuration: " + config.getName());
                }
                super.addDefaults(getDefaultConfigurationFile());
            }
            if (config.exists()) {
                super.load(config);
            }
        } catch (IOException | InvalidConfigurationException ex) {
            plugin.getLogger().severe("Could not load configuration file: " + config.getName());
            plugin.getLogger().severe(ExceptionUtils.getStackTrace(ex));
        }
    }

    /**
     * Returns the raw YamlConfiguration that this Config has been based on.
     *
     * @return The YamlConfiguration
     * @see YamlConfiguration
     */
    public YamlConfiguration getConfig() {
        return this;
    }

    /**
     * Returns the default configuration stored in the jar-file of the owning plugin.
     *
     * @return The default configuration.
     */
    public YamlConfiguration getDefaultConfigurationFile() {
        final YamlConfiguration CONFIG = new YamlConfiguration();
        try {
            CONFIG.load(RandomTweaks.reflect.getPluginFile(plugin, "config.yml"));
        } catch (IOException | InvalidConfigurationException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not load default configuration: {0}");
            plugin.getLogger().severe(ExceptionUtils.getStackTrace(ex));
            return null;
        }
        return CONFIG;
    }

    public static class Writer {
        void copy(InputStream stream, File file) throws IOException {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }

            final OutputStream output = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int fuzz;
            while ((fuzz = stream.read(buffer)) > 0) {
                output.write(buffer, 0, fuzz);
            }
            output.close();
            stream.close();
        }

        boolean deleteFolder(final File file) {
            if (file.exists() && file.isDirectory()) {
                return FileUtils.deleteQuietly(file);
            }
            return false;
        }

        void createDefaults(final Plugin plugin, final String configFile) {
            final File target = new File(plugin.getDataFolder(), configFile);
            if (target.exists()) return;

            plugin.getLogger().info("Installing default configuration file template: "
                    + target.getPath());

            try {
                final InputStream configStream = plugin.getResource(configFile);
                FileUtils.copyInputStreamToFile(configStream, target);
                configStream.close();
            } catch (IOException ex) {
                plugin.getLogger().severe(ex.getMessage());
            }
        }
    }
}
