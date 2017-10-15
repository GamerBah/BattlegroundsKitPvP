package com.battlegroundspvp.worldpvp.menus;
/* Created by GamerBah on 10/14/2017 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.administration.data.Rank;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.utils.inventories.*;
import com.battlegroundspvp.worldpvp.runnables.KitRollRunnable;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class QuartermasterMenus {

    public class SelectionMenu extends GameInventory {
        public SelectionMenu(Player player) {
            super("Quartermaster", null);
            setInventory(BattlegroundsKitPvP.getInstance().getServer().createInventory(null, 27, getInventory().getName()));
            GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
            addClickableItem(13, new ItemBuilder(Material.DIAMOND)
                    .name(new ColorBuilder(ChatColor.GREEN).bold().create() + "Purchase Random Kits")
                    .lore(ChatColor.GRAY + "Purchase a random kit")
                    .lore(ChatColor.GRAY + "from the Quartermaster")
                    .lore(ChatColor.GRAY + "for " + ChatColor.AQUA + "150 Souls" + ChatColor.GRAY + " each")
                    .lore("").lore(ChatColor.GRAY + "You have " + ChatColor.AQUA + gameProfile.getKitPvpData().getSouls() + " Souls")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                        new InventoryBuilder(player, new SlotSelectionMenu(player)).open();
                        EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                    })));
        }
    }

    public class SlotSelectionMenu extends GameInventory {

        SlotSelectionMenu(Player player) {
            super("Roll Amount", new SelectionMenu(player));
            setInventory(BattlegroundsKitPvP.getInstance().getServer().createInventory(null, 36, getInventory().getName()));
            addClickableItem(10, getSlotItem(player, 1));
            addClickableItem(12, getSlotItem(player, 2));
            addClickableItem(14, getSlotItem(player, 3));
            addClickableItem(16, getSlotItem(player, 4));
            addClickableItem(27, InventoryItems.back.clone().clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                new InventoryBuilder(player, new SelectionMenu(player)).open();
                EventSound.playSound(player, EventSound.INVENTORY_GO_BACK);
            })));
        }

        private ItemBuilder getSlotItem(Player player, int slotAmount) {
            GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
            int max = 1;
            if (gameProfile.hasRank(Rank.WARRIOR))
                max = 2;
            if (gameProfile.hasRank(Rank.CONQUEROR))
                max = 3;
            if (gameProfile.hasRank(Rank.WARLORD))
                max = 4;

            ItemBuilder itemBuilder = new ItemBuilder(Material.BREWING_STAND_ITEM).amount(slotAmount);
            if (slotAmount > max)
                itemBuilder.name(ChatColor.RED + "" + slotAmount + " Rolls");
            else {
                itemBuilder.name(gameProfile.getKitPvpData().getSouls() >= (150 * slotAmount) && gameProfile.hasRank(Rank.WARRIOR)
                        ? ChatColor.GREEN + "" + slotAmount + " Roll" : ChatColor.RED + "" + slotAmount + (slotAmount > 1 ? " Rolls" : " Roll"));
                itemBuilder.lore(ChatColor.GRAY + "Rolls " + slotAmount + (slotAmount > 1 ? " times" : " time") + " for " + ChatColor.AQUA + "" + (150 * slotAmount) + " Souls")
                        .lore("").lore(gameProfile.getKitPvpData().getSouls() >= (150 * slotAmount)
                        ? ChatColor.GRAY + "You'll have " + ChatColor.AQUA + (gameProfile.getKitPvpData().getSouls() - (150 * slotAmount))
                        + (gameProfile.getKitPvpData().getSouls() - (150 * slotAmount) > 1 ? " Souls" : " Soul") + ChatColor.GRAY + " left"
                        : ChatColor.RED + "You need " + ChatColor.AQUA + ((150 * slotAmount) - gameProfile.getKitPvpData().getSouls()
                        + ((150 * slotAmount) - gameProfile.getKitPvpData().getSouls() > 1 ? " Souls" : " Soul") + ChatColor.RED + "!"));
            }
            if (gameProfile.getKitPvpData().getSouls() >= (150 * slotAmount) && slotAmount <= max) {
                itemBuilder.lore("").lore(new ColorBuilder(ChatColor.YELLOW).bold().create() + "CLICK TO ROLL!").clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                    new InventoryBuilder(player, new SlotRollMenu(player, slotAmount)).open();
                    EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                }));
            } else {
                itemBuilder.clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> EventSound.playSound(player, EventSound.ACTION_FAIL)));
            }
            return itemBuilder;
        }
    }

    public class SlotRollMenu extends GameInventory {

        SlotRollMenu(Player player, int amount) {
            super("Rolling...", new SlotSelectionMenu(player));
            setInventory(BattlegroundsKitPvP.getInstance().getServer().createInventory(null, 45, getInventory().getName()));
            new KitRollRunnable(player, amount, getInventory()).start();
        }
    }

}
