package com.battlegroundspvp.worldpvp.playerevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PlayerItemPickup implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        event.setCancelled(true);
    }
}
