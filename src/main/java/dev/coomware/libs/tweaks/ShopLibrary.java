package dev.coomware.libs.tweaks;

import java.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class ShopLibrary implements InventoryHolder, Listener {
    private final Inventory INV;
    private final Map<Integer, GUIAction> actions;
    private final UUID uuid;
    //
    public static Map<UUID, ShopLibrary> invByUUID = new HashMap<>();
    public static Map<UUID, UUID> openInventories = new HashMap<>();
    //
    public static Map<UUID, Integer> coinMap = new HashMap<>();
    
    @SuppressWarnings("")
    public ShopLibrary(int invSize, String invName) {
        uuid = UUID.randomUUID();
        INV = Bukkit.createInventory(null, invSize, invName);
        actions = new HashMap<>();
        invByUUID.put(getUUId(), this);
    }
    
    public UUID getUUId() {
        return uuid;
    }
    
    @Override
    public Inventory getInventory() {
        return INV;
    }
    
    public interface GUIAction {
        void click(Player player);
    }
    
    public final void setItem(int slot, ItemStack stack, GUIAction action) {
        INV.setItem(slot, stack);
        if (action != null) {
            actions.put(slot, action);
        }
    }
    
    public final void setItem(int slot, ItemStack stack) {
        setItem(slot, stack, null);
    }
    
    public final void open(Player p) {
        p.openInventory(INV);
        openInventories.put(p.getUniqueId(), getUUId());
    }
    
    public final void delete() {
        Bukkit.getOnlinePlayers().forEach((p) ->
        {
            UUID u = openInventories.get(p.getUniqueId());
            if (u.equals(getUUId()))
            {
                p.closeInventory();
            }
        });
        invByUUID.remove(getUUId());
    }
    
    public static Map<UUID, ShopLibrary> getInvByUUID() {
        return invByUUID;
    }
    
    public static Map<UUID, UUID> getOpenInvs() {
        return openInventories;
    }
    
    public Map<Integer, GUIAction> getActions() {
        return actions;
    }
    
    @SuppressWarnings("null")
    public final ItemStack newItem(Material mat, String name, String...lore) {
        ItemStack item = new ItemStack(mat, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<>(Arrays.asList(lore));
        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return item;
    }
}
