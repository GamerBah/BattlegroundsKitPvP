package com.battlegroundspvp.worldpvp.menus;
/* Created by GamerBah on 10/14/2017 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.utils.inventories.*;
import com.battlegroundspvp.utils.messages.ColorBuilder;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class KitSelectorMenu extends GameInventory {

    public KitSelectorMenu(Player player) {
        super("Kit Selector", KitManager.getKits().size(), 54);
        setSearchRows(0, 3);
        alterNavigation(true, true);

        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        ArrayList<Kit> owned = new ArrayList<>();
        ArrayList<Kit> unowned = new ArrayList<>();

        for (Kit kit : KitManager.getKits()) {
            if (gameProfile.getKitPvpData().getOwnedKits().contains(kit.getId()))
                owned.add(kit);
            else unowned.add(kit);
        }
        owned.sort((k1, k2) -> (k2.getRarity().compareTo(k1.getRarity()) == 0 ? k1.getName().compareTo(k2.getName()) : k2.getRarity().compareTo(k1.getRarity())));
        unowned.sort((k1, k2) -> (k2.getRarity().compareTo(k1.getRarity()) == 0 ? k1.getName().compareTo(k2.getName()) : k2.getRarity().compareTo(k1.getRarity())));
        for (Kit kit : owned) {
            ItemBuilder ownedKit = kit.getItem()
                    .clone()
                    .lore(" ");
            if (gameProfile.getKitPvpData().getQuickSelectKits().size() < 9 && !gameProfile.getKitPvpData().getQuickSelectKits().contains(kit.getId())) {
                ownedKit.lore(ChatColor.YELLOW + "Left-Click" + ChatColor.GRAY + " to equip!")
                        .lore(ChatColor.YELLOW + "Right-Click" + ChatColor.GRAY + " to add to quick select!")
                        .clickEvent(new ClickEvent(ClickEvent.Type.LEFT, () -> {
                            kit.wearCheckLevel(player);
                            player.closeInventory();
                        }))
                        .clickEvent(new ClickEvent(ClickEvent.Type.RIGHT, () -> {
                            gameProfile.getKitPvpData().getQuickSelectKits().add(kit.getId());
                            EventSound.playSound(player, EventSound.ACTION_SUCCESS);
                            new InventoryBuilder(player, new KitSelectorMenu(player)).open();
                        }));
            } else {
                ownedKit.lore(ChatColor.YELLOW + "Click to equip!")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            kit.wearCheckLevel(player);
                            player.closeInventory();
                        }));
            }
            addItem(ownedKit);
        }
        for (Kit kit : unowned)
            addItem(new ItemBuilder(kit.getItem())
                    .name(new ColorBuilder(ChatColor.RED).bold().create() + "???")
                    .clearLore()
                    .lore(ChatColor.GRAY + "Unlocked from the Quartermaster!")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> EventSound.playSound(player, EventSound.ACTION_FAIL))));

        int x = 45;
        for (int i = 36; i < 45; i++)
            getInventory().setItem(i, InventoryItems.border(DyeColor.BLACK));
        for (int k : gameProfile.getKitPvpData().getQuickSelectKits())
            addButton(x++, KitManager.fromId(k).getItem()
                    .clone()
                    .lore(" ")
                    .lore(ChatColor.YELLOW + "Left-Click" + ChatColor.GRAY + " to equip!")
                    .lore(ChatColor.YELLOW + "Right-Click" + ChatColor.GRAY + " to remove from quick select!")
                    .clickEvent(new ClickEvent(ClickEvent.Type.LEFT, () -> {
                        KitManager.fromId(k).wearCheckLevel(player);
                        player.closeInventory();
                    }))
                    .clickEvent(new ClickEvent(ClickEvent.Type.RIGHT, () -> {
                        gameProfile.getKitPvpData().getQuickSelectKits().remove(new Integer(KitManager.fromId(k).getId()));
                        EventSound.playSound(player, EventSound.ACTION_SUCCESS);
                        new InventoryBuilder(player, new KitSelectorMenu(player)).open();
                    })));
    }
}
