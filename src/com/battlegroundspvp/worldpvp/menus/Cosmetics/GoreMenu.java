package com.battlegroundspvp.worldpvp.menus.Cosmetics;
/* Created by GamerBah on 8/20/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.enums.Cosmetic;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.*;
import com.battlegroundspvp.worldpvp.menus.Player.ProfileMenu;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GoreMenu extends GameInventory {

    public GoreMenu(Player player) {
        super("Gores", new ProfileMenu(player));
        setInventory(Bukkit.getServer().createInventory(null, 27, getInventory().getName()));

        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());

        ItemBuilder rareLockedGlass = new ItemBuilder(Material.STAINED_GLASS_PANE).durability(11).name(ChatColor.BLUE + "Rare Gore").lore(ChatColor.GRAY + "Random unlock from a Cosmeticrate");
        for (int i = 9; i < 18; i++) {
            addClickableItem(i, rareLockedGlass);
        }

        ItemBuilder epicLockedGlass = new ItemBuilder(Material.STAINED_GLASS_PANE).durability(1).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Epic Gore").lore(ChatColor.GRAY + "Random unlock from a Cosmeticrate");
        for (int i = 0; i < 5; i++) {
            addClickableItem(i, epicLockedGlass);
        }

        ItemBuilder legendaryLockedGlass = new ItemBuilder(Material.STAINED_GLASS_PANE).durability(2).name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Legendary Gore").lore(ChatColor.GRAY + "Random unlock from a Cosmeticrate");
        for (int i = 5; i < 9; i++) {
            addClickableItem(i, legendaryLockedGlass);
        }

        int rareSlot = 9, epicSlot = 0, legendarySlot = 5;

        for (Cosmetic.Item item : Cosmetic.Item.values()) {
            if (item.getGroup().equals(Cosmetic.KILL_EFFECT)) {
                if (item.getRarity() == Rarity.RARE) {
                    if (gameProfile.getOwnedCosmetics().contains(item.getId() + ",")) {
                        addClickableItem(rareSlot++, item.getItem());
                    }
                } else if (item.getRarity() == Rarity.EPIC) {
                    if (gameProfile.getOwnedCosmetics().contains(item.getId() + ",")) {
                        addClickableItem(epicSlot++, item.getItem());
                    }
                } else if (item.getRarity() == Rarity.LEGENDARY) {
                    if (gameProfile.getOwnedCosmetics().contains(item.getId() + ",")) {
                        addClickableItem(legendarySlot++, item.getItem());
                    }
                }
            }
        }

        addClickableItem(26, Cosmetic.Item.GORE_NONE.getItem());
        addClickableItem(22, InventoryItems.back.clone()
                .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                    new InventoryBuilder(player, getPreviousInventory()).open();
                    EventSound.playSound(player, EventSound.INVENTORY_GO_BACK);
                })));
    }

}
