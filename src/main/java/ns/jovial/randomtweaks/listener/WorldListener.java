package ns.jovial.randomtweaks.listener;


import ns.jovial.randomtweaks.RandomTweaks;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

public class WorldListener implements Listener {
    public WorldListener(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void SheepEvent(SheepRegrowWoolEvent event) {
        if (RandomTweaks.config.getBoolean("world_listener.sheep_event")) {

        }
    }

    @EventHandler
    public void EggEvent(PlayerEggThrowEvent event) {
        if (RandomTweaks.config.getBoolean("world_listener.egg_event")) {
            Egg egg  = event.getEgg();
            Player player = event.getPlayer();
            World world = player.getWorld();
            Location eggLoc = egg.getLocation();

            world.createExplosion(eggLoc, 0f);
            for (int x = 0; x <= 50; x++) {
                Location vector = eggLoc.add(new Vector((new Random()).nextInt(5),
                        5,
                        (new Random()).nextInt(5)));
                EntityType type = EntityType.CHICKEN;
                world.spawnEntity(vector, type);
            }
        }
    }

    @EventHandler
    public void BreakBlockEvent(BlockBreakEvent event) {

    }

    @EventHandler
    public void PlaceBlockEvent(BlockPlaceEvent event) {

    }

    @EventHandler
    public void DropItemEvent(PlayerDropItemEvent event) {

    }

    @EventHandler
    public void BlockDropItem(BlockDropItemEvent event) {

    }

    @EventHandler
    public void InteractEvent(PlayerInteractEvent event) {

    }

    @EventHandler
    public void TickEvent(StructureGrowEvent event) {

    }

    @EventHandler
    public void PhysicsEvent(BlockPhysicsEvent event) {

    }


}
