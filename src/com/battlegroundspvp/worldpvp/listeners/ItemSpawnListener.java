package com.battlegroundspvp.worldpvp.listeners;
/* Created by GamerBah on 8/15/2016 */

import com.battlegroundspvp.BattlegroundsKitPvP;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemSpawnListener implements Listener {

    @EventHandler
    public void onItemSpawn(final ItemSpawnEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                event.getEntity().remove();
            }
        }.runTaskLater(BattlegroundsKitPvP.getInstance(), 40);
    }

}
