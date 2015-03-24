package me.jjm_223.PetTransportation.listeners;

import me.jjm_223.PetTransportation.PTMain;
import me.jjm_223.PetTransportation.utils.DataStorage;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;

/**
 * Helps config cleanup by keeping track of despawns and removals.
 */

@SuppressWarnings("unused")
public class ItemDespawn implements Listener {

    private DataStorage storage = new DataStorage(PTMain.getPlugin(PTMain.class).getConfig());

    //Called when an item despawns.
    @EventHandler
    public void onDespawn(ItemDespawnEvent event) {
        //Make sure item is indeed of the spawn egg persuasion, then remove it from the config.
        if (event.getEntity().getItemStack().getItemMeta().getLore() != null) {
            if (event.getEntity().getItemStack().getItemMeta().getLore().size() == 2) {
                storage.configClean(event.getEntity().getItemStack().getItemMeta().getLore().get(1));
            }
        }
    }

    //Called when an item is destroyed by cactus/explosion/etc.
    @EventHandler
    public void onDestroy(EntityDamageEvent event) {
        //Make sure item is indeed of the spawn egg persuasion, then remove it from the config.
        if (event.getEntity() instanceof Item) {
            Item item = (Item) event.getEntity();
            if (item.getItemStack().getItemMeta().getLore() != null) {
                if (item.getItemStack().getItemMeta().getLore().size() == 2) {
                    storage.configClean(item.getItemStack().getItemMeta().getLore().get(1));
                }
            }
        }
    }
}
