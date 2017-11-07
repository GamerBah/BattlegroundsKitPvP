package com.battlegroundspvp.worldpvp.playerevents;
/* Created by GamerBah on 10/14/2017 */

import com.battlegroundspvp.global.commands.NPCCommand;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.utils.inventories.InventoryBuilder;
import com.battlegroundspvp.worldpvp.menus.QuartermasterMenus;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntity {

    public static void interact(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (player.getGameMode() == GameMode.SPECTATOR)
            event.setCancelled(true);

        if (CitizensAPI.getNPCRegistry().isNPC(entity)) {
            NPC npc = CitizensAPI.getNPCRegistry().getNPC(entity);
            if (player.getGameMode() == GameMode.CREATIVE) {
                if (NPCCommand.getRemoval().contains(player)) {
                    npc.destroy();
                    NPCCommand.getRemoval().remove(player);
                    player.sendMessage(ChatColor.GREEN + "NPC successfully removed!");
                    EventSound.playSound(player, EventSound.ACTION_SUCCESS);
                    return;
                }
                return;
            }

            new InventoryBuilder(player, new QuartermasterMenus().new SelectionMenu(player)).open();
        }
    }
}
