package com.battlegroundspvp.worldpvp.playerevents;
/* Created by GamerBah on 11/11/2017 */

import com.battlegroundspvp.worldpvp.WorldPvP;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class PlayerCloseInventory {

    public static void close(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (!WorldPvP.getRolling().containsKey(player) && WorldPvP.getRollInventory().containsKey(player))
            WorldPvP.getRollInventory().remove(player);
    }

}
