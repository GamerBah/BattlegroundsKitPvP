package com.battlegroundspvp.worldpvp.playerevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerItemDrop implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}