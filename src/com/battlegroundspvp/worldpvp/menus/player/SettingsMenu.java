package com.battlegroundspvp.worldpvp.menus.player;
/* Created by GamerBah on 6/1/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.administration.data.Rank;
import com.battlegroundspvp.util.enums.EventSound;
import com.battlegroundspvp.util.enums.ParticleQuality;
import com.battlegroundspvp.util.gui.InventoryItems;
import com.battlegroundspvp.util.message.MessageBuilder;
import com.gamerbah.inventorytoolkit.ClickEvent;
import com.gamerbah.inventorytoolkit.GameInventory;
import com.gamerbah.inventorytoolkit.InventoryBuilder;
import com.gamerbah.inventorytoolkit.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SettingsMenu extends GameInventory {

    public SettingsMenu(Player player) {
        super("Settings", 27, null);
        setBackButton(false);
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        if (!gameProfile.getPlayerSettings().isTeamRequests()) {
            addButton(11, new ItemBuilder(Material.INK_SACK).durability(8)
                    .name(ChatColor.YELLOW + "Team Requests: " + new MessageBuilder(ChatColor.RED).bold().create() + "DISABLED")
                    .lore(ChatColor.GRAY + "Enabling this will allow players")
                    .lore(ChatColor.GRAY + "to send you team requests")
                    .onClick(new ClickEvent(() -> {
                        gameProfile.getPlayerSettings().setTeamRequests(true);
                        new InventoryBuilder(player, new SettingsMenu(player)).open();
                        EventSound.playSound(player, EventSound.CLICK);
                    })));
        } else {
            addButton(11, new ItemBuilder(Material.INK_SACK).durability(10)
                    .name(ChatColor.YELLOW + "Team Requests: " + new MessageBuilder(ChatColor.GREEN).bold().create() + "ENABLED")
                    .lore(ChatColor.GRAY + "Disabling this will prevent players")
                    .lore(ChatColor.GRAY + "from sending you team requests")
                    .onClick(new ClickEvent(() -> {
                        gameProfile.getPlayerSettings().setTeamRequests(false);
                        new InventoryBuilder(player, new SettingsMenu(player)).open();
                        EventSound.playSound(player, EventSound.CLICK);
                    })));
        }

        if (!gameProfile.getPlayerSettings().isPrivateMessaging()) {
            addButton(13, new ItemBuilder(Material.INK_SACK).durability(8)
                    .name(ChatColor.YELLOW + "Private Messaging: " + new MessageBuilder(ChatColor.RED).bold().create() + "DISABLED")
                    .lore(ChatColor.GRAY + "Enabling this will allow players")
                    .lore(ChatColor.GRAY + "to privately message you")
                    .onClick(new ClickEvent(() -> {
                        gameProfile.getPlayerSettings().setPrivateMessaging(true);
                        new InventoryBuilder(player, new SettingsMenu(player)).open();
                        EventSound.playSound(player, EventSound.CLICK);
                    })));
        } else {
            addButton(13, new ItemBuilder(Material.INK_SACK).durability(10)
                    .name(ChatColor.YELLOW + "Private Messaging: " + new MessageBuilder(ChatColor.GREEN).bold().create() + "ENABLED")
                    .lore(ChatColor.GRAY + "Disabling this will prevent players")
                    .lore(ChatColor.GRAY + "from privately messaging you")
                    .onClick(new ClickEvent(() -> {
                        gameProfile.getPlayerSettings().setPrivateMessaging(false);
                        new InventoryBuilder(player, new SettingsMenu(player)).open();
                        EventSound.playSound(player, EventSound.CLICK);
                    })));
        }

        if (gameProfile.getPlayerSettings().getParticleQuality().equals(ParticleQuality.LOW)) {
            addButton(15, ParticleQuality.LOW.item);
        }
        if (gameProfile.getPlayerSettings().getParticleQuality().equals(ParticleQuality.MEDIUM)) {
            addButton(15, ParticleQuality.MEDIUM.item);
        }
        if (gameProfile.getPlayerSettings().getParticleQuality().equals(ParticleQuality.HIGH)) {
            //addButton(15, ParticleQuality.HIGH.getItem());
            addButton(15, new ItemBuilder(Material.INK_SACK).durability(8)
                    .name(ChatColor.AQUA + "Particle Quality")
                    .lore(new MessageBuilder(ChatColor.RED).bold().create() + "COMING SOON!")
                    .onClick(new ClickEvent(() -> EventSound.playSound(player, EventSound.ACTION_FAIL))));
        }

        if (gameProfile.hasRank(Rank.HELPER)) {
            addButton(22, new ItemBuilder(Material.EYE_OF_ENDER)
                    .name(ChatColor.GOLD + "Staff Preferences")
                    .onClick(new ClickEvent(() -> {
                        new InventoryBuilder(player, new StaffSettings(player)).open();
                        EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                    })));
        }
    }

    public class StaffSettings extends GameInventory {

        public StaffSettings(Player player) {
            super("Staff Settings", 36, new SettingsMenu(player));
            GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());

            if (!gameProfile.getPlayerSettings().isStealthyJoin()) {
                if (gameProfile.hasRank(Rank.MODERATOR)) {
                    addButton(12, new ItemBuilder(Material.INK_SACK).durability(8)
                            .name(ChatColor.GOLD + "Stealthy Join: " + new MessageBuilder(ChatColor.RED).bold().create() + "DISABLED")
                            .lore(ChatColor.GRAY + "Enabling this will cause you to join")
                            .lore(ChatColor.GRAY + "the server without the notifications")
                            .onClick(new ClickEvent(() -> {
                                gameProfile.getPlayerSettings().setStealthyJoin(true);
                                new InventoryBuilder(player, new StaffSettings(player)).open();
                                EventSound.playSound(player, EventSound.CLICK);
                            })));
                } else {
                    addButton(12, new ItemBuilder(Material.INK_SACK).durability(8)
                            .name(ChatColor.GOLD + "Stealthy Join: " + new MessageBuilder(ChatColor.RED).bold().create() + "DISABLED")
                            .lore(ChatColor.GRAY + "Must be a " + Rank.MODERATOR.getColor() + "" + ChatColor.BOLD + "MODERATOR"
                                    + ChatColor.GRAY + " to enable this!")
                            .onClick(new ClickEvent(() -> EventSound.playSound(player, EventSound.ACTION_FAIL))));
                }
            } else {
                addButton(12, new ItemBuilder(Material.INK_SACK).durability(10)
                        .name(ChatColor.GOLD + "Stealthy Join: " + new MessageBuilder(ChatColor.GREEN).bold().create() + "ENABLED")
                        .lore(ChatColor.GRAY + "Disabling this will cause you to join")
                        .lore(ChatColor.GRAY + "the server with notifications")
                        .onClick(new ClickEvent(() -> {
                            gameProfile.getPlayerSettings().setStealthyJoin(false);
                            new InventoryBuilder(player, new StaffSettings(player)).open();
                            EventSound.playSound(player, EventSound.CLICK);
                        })));
            }
            if (!BattlegroundsCore.getCmdspies().contains(player.getUniqueId())) {
                addButton(14, new ItemBuilder(Material.INK_SACK).durability(8)
                        .name(ChatColor.GOLD + "Command Spy: " + new MessageBuilder(ChatColor.RED).bold().create() + "DISABLED")
                        .lore(ChatColor.GRAY + "Enabling this will allow you to see")
                        .lore(ChatColor.GRAY + "every command that players execute")
                        .onClick(new ClickEvent(() -> {
                            BattlegroundsCore.getCmdspies().add(player.getUniqueId());
                            new InventoryBuilder(player, new StaffSettings(player)).open();
                            EventSound.playSound(player, EventSound.CLICK);
                        })));
            } else {
                addButton(14, new ItemBuilder(Material.INK_SACK).durability(10)
                        .name(ChatColor.GOLD + "Command Spy: " + new MessageBuilder(ChatColor.GREEN).bold().create() + "ENABLED")
                        .lore(ChatColor.GRAY + "Disabling this will stop you from seeing")
                        .lore(ChatColor.GRAY + "every command that players execute")
                        .onClick(new ClickEvent(() -> {
                            BattlegroundsCore.getCmdspies().remove(player.getUniqueId());
                            new InventoryBuilder(player, new StaffSettings(player)).open();
                            EventSound.playSound(player, EventSound.CLICK);
                        })));
            }
            addButton(31, InventoryItems.back.clone()
                    .onClick(new ClickEvent(() -> {
                        new InventoryBuilder(player, getPreviousInventory()).open();
                        EventSound.playSound(player, EventSound.INVENTORY_GO_BACK);
                    })));
        }
    }
}
