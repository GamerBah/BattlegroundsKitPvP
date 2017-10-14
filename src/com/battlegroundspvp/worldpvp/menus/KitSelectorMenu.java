package com.battlegroundspvp.worldpvp.menus;
/* Created by GamerBah on 10/14/2017 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ClickEvent;
import com.battlegroundspvp.utils.inventories.GameInventory;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KitSelectorMenu extends GameInventory {

    public KitSelectorMenu(Player player) {
        super("Kit Selector", null);
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());

        for (int i = 36; i < 54; i++)
            getInventory().setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(15).name(ChatColor.GRAY + "Common Kit")
                    .lore(ChatColor.GRAY + "Random unlock from").lore(ChatColor.GRAY + "the " + ChatColor.AQUA + "Quartermaster"));

        for (int i = 18; i < 36; i++)
            getInventory().setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(11).name(ChatColor.BLUE + "Rare Kit")
                    .lore(ChatColor.GRAY + "Random unlock from").lore(ChatColor.GRAY + "the " + ChatColor.AQUA + "Quartermaster"));

        for (int i = 9; i < 18; i++)
            getInventory().setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(1).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Epic Kit")
                    .lore(ChatColor.GRAY + "Random unlock from").lore(ChatColor.GRAY + "the " + ChatColor.AQUA + "Quartermaster"));

        for (int i = 0; i < 9; i++)
            getInventory().setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(2).name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Legendary Kit")
                    .lore(ChatColor.GRAY + "Random unlock from").lore(ChatColor.GRAY + "the " + ChatColor.AQUA + "Quartermaster"));

        int commonSlot = 36, rareSlot = 18, epicSlot = 9, legendarySlot = 0;

        for (Kit kit : KitManager.getKits()) {
            if (kit.getRarity() == Rarity.COMMON) {
                if (gameProfile.getKitPvpData().getOwnedKits().contains(kit.getId() + ",")) {
                    addClickableItem(commonSlot++, kit.getItem().clone().clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                        kit.wearCheckLevel(player);
                        player.closeInventory();
                    })));
                } else {
                    commonSlot++;
                }
            } else if (kit.getRarity() == Rarity.RARE) {
                if (gameProfile.getKitPvpData().getOwnedKits().contains(kit.getId() + ",")) {
                    addClickableItem(rareSlot++, kit.getItem().clone().clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                        kit.wearCheckLevel(player);
                        player.closeInventory();
                    })));
                } else {
                    rareSlot++;
                }
            } else if (kit.getRarity() == Rarity.EPIC) {
                if (gameProfile.getKitPvpData().getOwnedKits().contains(kit.getId() + ",")) {
                    addClickableItem(epicSlot++, kit.getItem().clone().clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                        kit.wearCheckLevel(player);
                        player.closeInventory();
                    })));
                } else {
                    epicSlot++;
                }
            } else if (kit.getRarity() == Rarity.LEGENDARY) {
                if (gameProfile.getKitPvpData().getOwnedKits().contains(kit.getId() + ",")) {
                    addClickableItem(legendarySlot++, kit.getItem().clone().clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                        kit.wearCheckLevel(player);
                        player.closeInventory();
                    })));
                } else {
                    legendarySlot++;
                }
            }
        }
    }

}
