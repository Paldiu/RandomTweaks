package dev.coomware.libs.world;

import dev.coomware.libs.world.properties.Properties;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public interface WorldAPI {
    static WorldAPI getInstance(Plugin plugin) {
        JavaPlugin plug = JavaPlugin.getProvidingPlugin(WorldAPI.class);
        WorldAPI api = (WorldAPI) plug;
        return api;
    }

    ChunkGenerator createCumstonGenerator(Referencer world, Consumer<WorldGenBase> consumer);

    WorldGenBase getForWorld(World world);

    Properties getProperties();
}
