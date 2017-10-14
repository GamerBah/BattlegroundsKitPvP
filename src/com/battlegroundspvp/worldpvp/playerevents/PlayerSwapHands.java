package com.battlegroundspvp.worldpvp.playerevents;
/* Created by GamerBah on 8/27/2016 */

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerSwapHands implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onSwapHands(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }
}
