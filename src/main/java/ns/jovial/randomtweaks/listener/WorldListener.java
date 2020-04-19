package ns.jovial.randomtweaks.listener;


import ns.jovial.randomtweaks.RandomTweaks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.Plugin;

public class WorldListener implements Listener {
    //Using generic plugin types in the constructor to let you implement these listeners in your plugin.
    public WorldListener(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static boolean enabled = false;

    @EventHandler
    public void SheepEvent(SheepRegrowWoolEvent event) {
        if (enabled) {

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
