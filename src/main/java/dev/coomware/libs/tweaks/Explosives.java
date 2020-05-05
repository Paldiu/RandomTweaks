package dev.coomware.libs.tweaks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Explosives {
    private final World WORLD;
    private final float RANGE;
    private final Location LOCATION;

    public Explosives(World world, Location loc, float abs) {
        WORLD = world;
        LOCATION = loc;
        RANGE = abs;
    }

    public Explosion explosion() {
        return new Explosion(WORLD, LOCATION, RANGE);
    }



    // Any methods marked in here as deprecated are listed as such because
    // they are mainly used when initializing this class using the default constructor.
    private class Explosion {
        final World w;
        float f;
        Location l;

        /**
         * This is unsafe as a constructor as it will get the first loaded world.
         * If you are only using the default world supplier, this will get the overworld.
         * This will also retrieve the location as spawn. This however will be configurable later on.
         */
        @Deprecated
        Explosion() {
            w = Bukkit.getServer().getWorlds().get(0); // This cannot be changed.
            l = w.getSpawnLocation(); // This can be configured later.
            f = 3; // The default explosive radius, which can also be changed later.
        }

        Explosion(World w, Location l, float f) {
            this.w = w;
            this.f = f;
            this.l = l;
        }

        /**
         * This will create an explosion at the created location.
         */
        void create() {
            w.createExplosion(l, f, false, false);
        }

        /**
         * This creates an explosion based on
         */
        void createWithFire() {
            w.createExplosion(l, f, true, false);
        }

        void createWithBlockDestruction() {
            w.createExplosion(l, f, false, true);
        }

        void createWithFireAndBlockDestruction() {
            w.createExplosion(l, f, true, true);
        }

        @Deprecated
        void setRadius(float f) {
            this.f = f;
        }

        float getRadius() {
            return f;
        }

        @Deprecated
        void setLocation(Location loc) {
            this.l = loc;
        }

        Location getLocation() {
            return l;
        }

        World getWorld() {
            return w;
        }
    }
}
