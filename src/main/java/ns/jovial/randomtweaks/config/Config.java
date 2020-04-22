package ns.jovial.randomtweaks.config;

import ns.jovial.randomtweaks.RandomTweaks;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class Config extends YamlConfiguration {
    private final Plugin plugin;
    private final File config;
    private final boolean copyDefaults;

    public Config(Plugin plugin, String fileName, boolean copyDefaults) {

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
