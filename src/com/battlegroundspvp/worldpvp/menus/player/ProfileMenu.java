package com.battlegroundspvp.worldpvp.menus.player;
/* Created by GamerBah on 9/5/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.administration.data.Rank;
import com.battlegroundspvp.util.cosmetic.Cosmetic;
import com.battlegroundspvp.util.cosmetic.Gore;
import com.battlegroundspvp.util.cosmetic.ParticlePack;
import com.battlegroundspvp.util.cosmetic.Warcry;
import com.battlegroundspvp.util.enums.EventSound;
import com.battlegroundspvp.util.enums.Rarity;
import com.battlegroundspvp.util.message.MessageBuilder;
import com.battlegroundspvp.worldpvp.menus.cosmetics.GoreMenu;
import com.battlegroundspvp.worldpvp.menus.cosmetics.TrailMenu;
import com.battlegroundspvp.worldpvp.menus.cosmetics.WarcryMenu;
import com.battlegroundspvp.worldpvp.utils.KDRatio;
import com.gamerbah.inventorytoolkit.ClickEvent;
import com.gamerbah.inventorytoolkit.GameInventory;
import com.gamerbah.inventorytoolkit.InventoryBuilder;
import com.gamerbah.inventorytoolkit.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.SkullMeta;

public class ProfileMenu extends GameInventory {

    public ProfileMenu(Player player) {
        super("My Profile", 27);
        setBackButton(false);
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        KDRatio kd = new KDRatio();
        int amount = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId()).getTotalEssenceAmount();
        GameProfile lastKilledBy = (gameProfile.getKitPvpData().getLastKilledBy() == -1 ? null : BattlegroundsCore.getInstance().getGameProfile(gameProfile.getKitPvpData().getLastKilledBy()));

        ItemBuilder head = new ItemBuilder(Material.SKULL_ITEM).durability(3)
                .name(gameProfile.getRank().getColor().create() + (gameProfile.hasRank(Rank.WARRIOR) ? gameProfile.getRank().getName().toUpperCase() + " " : "")
                        + (gameProfile.hasRank(Rank.WARRIOR) ? ChatColor.WHITE : ChatColor.GRAY) + player.getName())
                .lore(ChatColor.GRAY + "Kills: " + ChatColor.GREEN + gameProfile.getKitPvpData().getKills())
                .lore(ChatColor.GRAY + "Deaths: " + ChatColor.RED + gameProfile.getKitPvpData().getDeaths())
                .lore(ChatColor.GRAY + "K/D Ratio: " + kd.getRatioColor(gameProfile) + kd.getRatio(gameProfile))
                .lore(ChatColor.GRAY + "Longest Killstreak: " + ChatColor.DARK_AQUA + gameProfile.getKitPvpData().getHighestKillstreak())
                .lore(ChatColor.GRAY + "Revenge Kills: " + ChatColor.BLUE + gameProfile.getKitPvpData().getRevengeKills())
                .lore(ChatColor.GRAY + "Killstreaks Ended: " + ChatColor.YELLOW + gameProfile.getKitPvpData().getKillstreaksEnded())
                .lore(ChatColor.GRAY + "Last Killed By: " + (lastKilledBy != null ? (lastKilledBy.hasRank(Rank.WARRIOR) ? ChatColor.WHITE : "")
                        + lastKilledBy.getName() : "--"))
                .lore(ChatColor.GRAY + "Combat Level: " + ChatColor.DARK_PURPLE + gameProfile.getKitPvpData().getCombatLevel())
                .lore(" ")
                .lore(ChatColor.GRAY + "Particle Pack: " + (ParticlePack.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveTrail()).getRarity() == Rarity.COMMON ? ChatColor.DARK_GRAY :
                        ParticlePack.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveTrail()).getRarity().getColor())
                        + (ParticlePack.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveTrail()).getRarity() == Rarity.EPIC ||
                        ParticlePack.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveTrail()).getRarity() == Rarity.LEGENDARY ? "" + ChatColor.BOLD : "")
                        + ParticlePack.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveTrail()).getName())
                .lore(ChatColor.GRAY + "Warcry: " + (Warcry.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveWarcry()).getRarity() == Rarity.COMMON ? ChatColor.DARK_GRAY :
                        Warcry.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveWarcry()).getRarity().getColor())
                        + (Warcry.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveWarcry()).getRarity() == Rarity.EPIC || Warcry.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveWarcry()).getRarity() == Rarity.LEGENDARY ? "" + ChatColor.BOLD : "")
                        + Warcry.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveWarcry()).getName())
                .lore(ChatColor.GRAY + "Gore: " + (Gore.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveGore()).getRarity() == Rarity.COMMON ? ChatColor.DARK_GRAY :
                        Gore.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveGore()).getRarity().getColor())
                        + (Gore.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveGore()).getRarity() == Rarity.EPIC ||
                        Gore.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveGore()).getRarity() == Rarity.LEGENDARY ? "" + ChatColor.BOLD : "")
                        + Gore.fromId(Cosmetic.ServerType.KITPVP, gameProfile.getKitPvpData().getActiveGore()).getName())
                //.lore(ChatColor.GRAY + "Mastery Title: " + (achievement != null ? BoldColor.GOLD.getColor() + "[" + achievement.getTitle() + "]" : "None"))
                .lore(" ")
                .lore(ChatColor.GRAY + "Souls: " + ChatColor.AQUA + gameProfile.getKitPvpData().getSouls())
                .lore(ChatColor.GRAY + "Battle Coins: " + ChatColor.LIGHT_PURPLE + gameProfile.getCoins());
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(player);
        head.setItemMeta(meta);


        addButton(10, new ItemBuilder(Material.DIAMOND_SWORD)
                .name(ChatColor.AQUA + "Warcries")
                .lore(ChatColor.GRAY + "Make sure your kills are heard!").flag(ItemFlag.HIDE_ATTRIBUTES)
                .onClick(new ClickEvent(() -> {
                    new InventoryBuilder(player, new WarcryMenu(player)).open();
                    EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                })));
        addButton(11, new ItemBuilder(Material.GLOWSTONE_DUST)
                .name(ChatColor.AQUA + "Gores")
                .lore(ChatColor.GRAY + "Effects for your kills!")
                .onClick(new ClickEvent(() -> {
                    new InventoryBuilder(player, new GoreMenu(player)).open();
                    EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                })));
        addButton(12, new ItemBuilder(Material.MAGMA_CREAM)
                .name(ChatColor.AQUA + "Particle Packs")
                .lore(ChatColor.GRAY + "Select cool trails to show off!")
                .onClick(new ClickEvent(() -> {
                    new InventoryBuilder(player, new TrailMenu(player)).open();
                    EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                })));
        addButton(13, head);
        addButton(14, new ItemBuilder(Material.EMERALD)
                .name(new MessageBuilder(ChatColor.RED).bold().create() + "COMING SOON! " + ChatColor.GREEN + "Social Menu")
                .lore(ChatColor.GRAY + "Opens the Social Menu!")
                .onClick(new ClickEvent(() -> {
                    //new InventoryBuilder(player, new SocialMenu(player)).open();
                    EventSound.playSound(player, EventSound.ACTION_FAIL);
                })));
        addButton(15, new ItemBuilder(Material.DIAMOND)
                .name(new MessageBuilder(ChatColor.RED).bold().create() + "COMING SOON! " + ChatColor.YELLOW + "Daily Challenges")
                .lore(ChatColor.GRAY + "View the daily challenges, start")
                .lore(ChatColor.GRAY + "new ones, and receive rewards!")
                .onClick(new ClickEvent(() -> {
                    //new InventoryBuilder(player, new ChallengeMenu(player)).open();
                    EventSound.playSound(player, EventSound.ACTION_FAIL);
                })));
        addButton(16, new ItemBuilder(Material.BLAZE_POWDER)
                .name((amount == 0 ? ChatColor.RED + "Battle Essence" : ChatColor.GREEN + "Battle Essence"))
                .amount(amount == 0 ? 1 : amount)
                .lore(amount == 0 ? ChatColor.GRAY + "You don't have Battle Essence!" : ChatColor.GRAY + "You have " + ChatColor.AQUA + amount + ChatColor.GRAY + " Battle "
                        + (amount == 1 ? "Essence" : "Essences")).lore(" ").lore(ChatColor.GRAY + "Purchase Battle Essences at our store!")
                .lore(ChatColor.YELLOW + "store.battlegroundspvp.com")
                .onClick(new ClickEvent(() -> {
                    if (amount != 0) {
                        new InventoryBuilder(player, new EssenceMenu(player)).open();
                        EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                    } else
                        EventSound.playSound(player, EventSound.ACTION_FAIL);
                })));
    }

}
