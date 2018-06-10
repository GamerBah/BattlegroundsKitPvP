package com.battlegroundspvp.worldpvp.playerevents;
/* Created by GamerBah on 10/13/2017 */

import com.battlegroundspvp.administration.command.FreezeCommand;
import com.battlegroundspvp.runnable.misc.UpdateRunnable;
import com.battlegroundspvp.runnable.timer.AFKRunnable;
import com.battlegroundspvp.util.enums.EventSound;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import com.battlegroundspvp.worldpvp.menus.KitSelectorMenu;
import com.battlegroundspvp.worldpvp.menus.player.ProfileMenu;
import com.battlegroundspvp.worldpvp.menus.player.SettingsMenu;
import com.gamerbah.inventorytoolkit.InventoryBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractItem {

    public static void interact(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (AFKRunnable.getAfkTimer().containsKey(player)) {
            AFKRunnable.getAfkTimer().put(player, 0);
        }

        if (!player.getGameMode().equals(GameMode.CREATIVE) && !(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                || event.getAction().equals(Action.RIGHT_CLICK_AIR))) {
            event.setCancelled(true);
        }

        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL) {
            event.setCancelled(true);
        }

        if (FreezeCommand.frozen || FreezeCommand.frozenPlayers.contains(player)) {
            event.setCancelled(true);
            return;
        }


        if (item != null) {
            if (UpdateRunnable.updating) {
                event.setCancelled(true);
                return;
            }
            if (item.getType().equals(Material.POISONOUS_POTATO) || item.getType().equals(Material.POTATO_ITEM)) {
                event.setCancelled(true);
                return;
            }
            if (item.getType().equals(Material.NETHER_STAR)) {
                new KitSelectorMenu(player).build(player).open();
                EventSound.playSound(player, EventSound.INVENTORY_OPEN_MENU);
            } else if (item.getType().equals(Material.BOOK)) {
                if (KitManager.getPreviousKit().containsKey(player.getUniqueId())) {
                    player.getInventory().setItem(0, null);
                    KitManager.getPreviousKit().get(player.getUniqueId()).wearCheckLevel(player);
                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 2, 0.85F);
                }
            } else if (item.getType().equals(Material.SKULL_ITEM)) {
                new InventoryBuilder(player, new ProfileMenu(player)).open();
                EventSound.playSound(player, EventSound.INVENTORY_OPEN_MENU);
            } else if (item.getType().equals(Material.REDSTONE_COMPARATOR)) {
                new InventoryBuilder(player, new SettingsMenu(player)).open();
                EventSound.playSound(player, EventSound.INVENTORY_OPEN_MENU);
            }
        }
    }

}
