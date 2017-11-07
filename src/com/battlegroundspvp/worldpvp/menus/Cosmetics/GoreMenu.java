package com.battlegroundspvp.worldpvp.menus.Cosmetics;
/* Created by GamerBah on 8/20/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.cosmetics.Cosmetic;
import com.battlegroundspvp.utils.cosmetics.CosmeticManager;
import com.battlegroundspvp.utils.cosmetics.defaultcosmetics.DefaultGore;
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
        setInventory(Bukkit.getServer().createInventory(null, 36, getInventory().getName()));

        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());

        for (int i = 18; i < 27; i++) {
            addClickableItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .durability(7)
                    .name(ChatColor.GRAY + "Common Gore")
                    .lore(ChatColor.GRAY + "Random unlock from a Battle Crate")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> EventSound.playSound(player, EventSound.ACTION_FAIL))));
        }

        for (int i = 9; i < 18; i++) {
            addClickableItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .durability(11)
                    .name(ChatColor.BLUE + "Rare Gore")
                    .lore(ChatColor.GRAY + "Random unlock from a Battle Crate")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> EventSound.playSound(player, EventSound.ACTION_FAIL))));
        }

        for (int i = 0; i < 5; i++) {
            addClickableItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .durability(1)
                    .name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Epic Gore")
                    .lore(ChatColor.GRAY + "Random unlock from a Battle Crate")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> EventSound.playSound(player, EventSound.ACTION_FAIL))));
        }

        for (int i = 5; i < 9; i++) {
            addClickableItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .durability(2)
                    .name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Legendary Gore")
                    .lore(ChatColor.GRAY + "Random unlock from a Battle Crate")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> EventSound.playSound(player, EventSound.ACTION_FAIL))));
        }

        int commonSlot = 18, rareSlot = 9, epicSlot = 0, legendarySlot = 5;

        for (Cosmetic cosmetic : CosmeticManager.getRewardableCosmetics()) {
            if (cosmetic.getEffectType().equals(Cosmetic.EffectType.KILL_EFFECT)) {
                if (gameProfile.getCosmeticsData().getKitPvpCosmetics().contains(cosmetic.getId())) {
                    if (cosmetic.getRarity() == Rarity.COMMON) addClickableItem(commonSlot++,
                            cosmetic.getItem().clone().clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                                EventSound.playSound(player, EventSound.ACTION_SUCCESS);
                                player.sendMessage(ChatColor.GRAY + "Equipped the " + cosmetic.getName() + " Particle Pack!");
                                gameProfile.getKitPvpData().setActiveGore(cosmetic.getId());
                            })));
                    if (cosmetic.getRarity() == Rarity.RARE) addClickableItem(rareSlot++,
                            cosmetic.getItem().clone().clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                                EventSound.playSound(player, EventSound.ACTION_SUCCESS);
                                player.sendMessage(ChatColor.GRAY + "Equipped the " + cosmetic.getName() + " Particle Pack!");
                                gameProfile.getKitPvpData().setActiveGore(cosmetic.getId());
                            })));
                    if (cosmetic.getRarity() == Rarity.EPIC) addClickableItem(epicSlot++,
                            cosmetic.getItem().clone().clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                                EventSound.playSound(player, EventSound.ACTION_SUCCESS);
                                player.sendMessage(ChatColor.GRAY + "Equipped the " + cosmetic.getName() + " Particle Pack!");
                                gameProfile.getKitPvpData().setActiveGore(cosmetic.getId());
                            })));
                    if (cosmetic.getRarity() == Rarity.LEGENDARY) addClickableItem(legendarySlot++,
                            cosmetic.getItem().clone().clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                                EventSound.playSound(player, EventSound.ACTION_SUCCESS);
                                player.sendMessage(ChatColor.GRAY + "Equipped the " + cosmetic.getName() + " Particle Pack!");
                                gameProfile.getKitPvpData().setActiveGore(cosmetic.getId());
                            })));
                }
            }
        }

        addClickableItem(35, new DefaultGore().getItem()
                .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                    EventSound.playSound(player, EventSound.ACTION_FAIL);
                    player.sendMessage(ChatColor.GRAY + "Equipped the default Gore");
                    gameProfile.getKitPvpData().setActiveWarcry(new DefaultGore().getId());
                })));
        addClickableItem(27, InventoryItems.back.clone()
                .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                    new InventoryBuilder(player, getPreviousInventory()).open();
                    EventSound.playSound(player, EventSound.INVENTORY_GO_BACK);
                })));
    }

}
