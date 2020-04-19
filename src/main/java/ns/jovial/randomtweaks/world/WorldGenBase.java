package ns.jovial.randomtweaks.world;

import ns.jovial.randomtweaks.world.decor.Decorator;
import org.bukkit.World;

public interface WorldGenBase {
    Terrain getBaseTerrainGenerator() throws UnsupportedOperationException;

    Biome getBiomeGenerator();

    World getWorld();

    Decorator getDecorator() throws UnsupportedOperationException;

    Referencer getReferencer();

    Terrain setBaseNoiseGenerator(Noise noise);

    void setBaseTerrainGenerator(Terrain terrain);
}
