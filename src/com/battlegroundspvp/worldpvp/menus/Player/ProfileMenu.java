package com.battlegroundspvp.worldpvp.menus.Player;
/* Created by GamerBah on 9/5/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.administration.data.Rank;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ClickEvent;
import com.battlegroundspvp.utils.inventories.GameInventory;
import com.battlegroundspvp.utils.inventories.InventoryBuilder;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.worldpvp.menus.Cosmetics.GoreMenu;
import com.battlegroundspvp.worldpvp.menus.Cosmetics.TrailMenu;
import com.battlegroundspvp.worldpvp.menus.Cosmetics.WarcryMenu;
import com.battlegroundspvp.worldpvp.utils.KDRatio;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.SkullMeta;

public class ProfileMenu extends GameInventory {

    public ProfileMenu(Player player) {
        super("Profile", null);
        setInventory(Bukkit.getServer().createInventory(null, 27, getInventory().getName()));
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        KDRatio kd = new KDRatio();
        int amount = BattlegroundsCore.getInstance().getTotalEssenceAmount(player);
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
                .lore(ChatColor.GRAY + "Particle Pack: " + (gameProfile.getTrail().getRarity() == Rarity.COMMON ? ChatColor.DARK_GRAY : gameProfile.getTrail().getRarity().getColor())
                        + (gameProfile.getTrail().getRarity() == Rarity.EPIC || gameProfile.getTrail().getRarity() == Rarity.LEGENDARY ? "" + ChatColor.BOLD : "") + gameProfile.getTrail().getName())
                .lore(ChatColor.GRAY + "Warcry: " + (gameProfile.getWarcry().getRarity() == Rarity.COMMON ? ChatColor.DARK_GRAY : gameProfile.getWarcry().getRarity().getColor())
                        + (gameProfile.getWarcry().getRarity() == Rarity.EPIC || gameProfile.getWarcry().getRarity() == Rarity.LEGENDARY ? "" + ChatColor.BOLD : "") + gameProfile.getWarcry().getName())
                .lore(ChatColor.GRAY + "Gore: " + (gameProfile.getGore().getRarity() == Rarity.COMMON ? ChatColor.DARK_GRAY : gameProfile.getGore().getRarity().getColor())
                        + (gameProfile.getGore().getRarity() == Rarity.EPIC || gameProfile.getGore().getRarity() == Rarity.LEGENDARY ? "" + ChatColor.BOLD : "") + gameProfile.getGore().getName())
                //.lore(ChatColor.GRAY + "Mastery Title: " + (achievement != null ? BoldColor.GOLD.getColor() + "[" + achievement.getTitle() + "]" : "None"))
                .lore(" ")
                .lore(ChatColor.GRAY + "Souls: " + ChatColor.AQUA + gameProfile.getKitPvpData().getSouls())
                .lore(ChatColor.GRAY + "Battle Coins: " + ChatColor.LIGHT_PURPLE + gameProfile.getCoins());
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(player);
        head.setItemMeta(meta);


        addClickableItem(10, new ItemBuilder(Material.DIAMOND_SWORD)
                .name(ChatColor.AQUA + "Warcries")
                .lore(ChatColor.GRAY + "Make sure your kills are heard!").flag(ItemFlag.HIDE_ATTRIBUTES)
                .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                    new InventoryBuilder(player, new WarcryMenu(player)).open();
                    EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                })));
        addClickableItem(11, new ItemBuilder(Material.GLOWSTONE_DUST)
                .name(ChatColor.AQUA + "Gores")
                .lore(ChatColor.GRAY + "Effects for your kills!")
                .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                    new InventoryBuilder(player, new GoreMenu(player)).open();
                    EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                })));
        addClickableItem(12, new ItemBuilder(Material.MAGMA_CREAM)
                .name(ChatColor.AQUA + "Particle Packs")
                .lore(ChatColor.GRAY + "Select cool trails to show off!")
                .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                    new InventoryBuilder(player, new TrailMenu(player)).open();
                    EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                })));
        addClickableItem(13, head);
        addClickableItem(14, new ItemBuilder(Material.EMERALD)
                .name(new ColorBuilder(ChatColor.RED).bold().create() + "COMING SOON! " + ChatColor.GREEN + "Social Menu")
                .lore(ChatColor.GRAY + "Opens the Social Menu!")
                .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                    //new InventoryBuilder(player, new SocialMenu(player)).open();
                    EventSound.playSound(player, EventSound.ACTION_FAIL);
                })));
        addClickableItem(15, new ItemBuilder(Material.DIAMOND)
                .name(new ColorBuilder(ChatColor.RED).bold().create() + "COMING SOON! " + ChatColor.YELLOW + "Daily Challenges")
                .lore(ChatColor.GRAY + "View the daily challenges, start")
                .lore(ChatColor.GRAY + "new ones, and receive rewards!")
                .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                    //new InventoryBuilder(player, new ChallengeMenu(player)).open();
                    EventSound.playSound(player, EventSound.ACTION_FAIL);
                })));
        addClickableItem(16, new ItemBuilder(Material.BLAZE_POWDER)
                .name((amount == 0 ? ChatColor.RED + "Battle Essence" : ChatColor.GREEN + "Battle Essence"))
                .amount(amount == 0 ? 1 : amount)
                .lore(amount == 0 ? ChatColor.GRAY + "You don't have Battle Essence!" : ChatColor.GRAY + "You have " + ChatColor.AQUA + amount + ChatColor.GRAY + " Battle "
                        + (amount == 1 ? "Essence" : "Essences")).lore(" ").lore(ChatColor.GRAY + "Purchase Battle Essences at our store!")
                .lore(ChatColor.YELLOW + "store.battlegroundspvp.com")
                .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                    if (amount != 0) {
                        new InventoryBuilder(player, new EssenceMenu(player)).open();
                        EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                    } else
                        EventSound.playSound(player, EventSound.ACTION_FAIL);
                })));
    }

}
