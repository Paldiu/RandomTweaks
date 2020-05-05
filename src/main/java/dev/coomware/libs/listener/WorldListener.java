package dev.coomware.libs.listener;


import dev.coomware.libs.CoomLibs;
import org.bukkit.DyeColor;
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

import java.util.Random;

public class WorldListener implements Listener {
    private final Random R = new Random();
    public WorldListener(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    // All sheep randomly have a different color when they regrow wool \\
    @EventHandler
    public void SheepEvent(SheepRegrowWoolEvent event) {
        if (CoomLibs.config.getBoolean(name("sheep_event"))) {
            DyeColor[] colors = DyeColor.values();
            int i = R.nextInt(colors.length);
            event.getEntity().setColor(colors[i]);
        }
    }

    // Randomly spawn a number between 1 and 10 chickens.
    @EventHandler
    public void EggEvent(PlayerEggThrowEvent event) {
        if (CoomLibs.config.getBoolean(name("egg_event"))) {
            int i = R.nextInt(10);
            Egg egg  = event.getEgg();
            Player player = event.getPlayer();
            World world = player.getWorld();
            Location eggLoc = egg.getLocation();

            world.createExplosion(eggLoc, 0f);
            for (int x = 0; x <= i; x++) {
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

    // To make things a little neater in the listener \\
    private String name(String event) {
        return "world_listener." + event;
    }


}
