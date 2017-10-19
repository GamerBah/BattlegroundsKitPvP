package com.battlegroundspvp.worldpvp.menus.Player;
/* Created by GamerBah on 6/1/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.administration.data.Rank;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.utils.enums.ParticleQuality;
import com.battlegroundspvp.utils.inventories.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SettingsMenu extends GameInventory {

    public SettingsMenu(Player player) {
        super("Settings", null);
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        if (gameProfile.hasRank(Rank.MODERATOR))
            setInventory(Bukkit.getServer().createInventory(null, 36, getInventory().getName()));
        if (!gameProfile.getPlayerSettings().isTeamRequests()) {
            addClickableItem(11, new ItemBuilder(Material.INK_SACK).durability(8)
                    .name(ChatColor.YELLOW + "Team Requests: " + new ColorBuilder(ChatColor.RED).bold().create() + "DISABLED")
                    .lore(ChatColor.GRAY + "Enabling this will allow players")
                    .lore(ChatColor.GRAY + "to send you team requests")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                        gameProfile.getPlayerSettings().setTeamRequests(true);
                        new InventoryBuilder(player, new SettingsMenu(player)).open();
                        EventSound.playSound(player, EventSound.CLICK);
                    })));
        } else {
            addClickableItem(11, new ItemBuilder(Material.INK_SACK).durability(10)
                    .name(ChatColor.YELLOW + "Team Requests: " + new ColorBuilder(ChatColor.GREEN).bold().create() + "ENABLED")
                    .lore(ChatColor.GRAY + "Disabling this will prevent players")
                    .lore(ChatColor.GRAY + "from sending you team requests")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                        gameProfile.getPlayerSettings().setTeamRequests(false);
                        new InventoryBuilder(player, new SettingsMenu(player)).open();
                        EventSound.playSound(player, EventSound.CLICK);
                    })));
        }

        if (!gameProfile.getPlayerSettings().isPrivateMessaging()) {
            addClickableItem(13, new ItemBuilder(Material.INK_SACK).durability(8)
                    .name(ChatColor.YELLOW + "Private Messaging: " + new ColorBuilder(ChatColor.RED).bold().create() + "DISABLED")
                    .lore(ChatColor.GRAY + "Enabling this will allow players")
                    .lore(ChatColor.GRAY + "to privately message you")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                        gameProfile.getPlayerSettings().setPrivateMessaging(true);
                        new InventoryBuilder(player, new SettingsMenu(player)).open();
                        EventSound.playSound(player, EventSound.CLICK);
                    })));
        } else {
            addClickableItem(13, new ItemBuilder(Material.INK_SACK).durability(10)
                    .name(ChatColor.YELLOW + "Private Messaging: " + new ColorBuilder(ChatColor.GREEN).bold().create() + "ENABLED")
                    .lore(ChatColor.GRAY + "Disabling this will prevent players")
                    .lore(ChatColor.GRAY + "from privately messaging you")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                        gameProfile.getPlayerSettings().setPrivateMessaging(false);
                        new InventoryBuilder(player, new SettingsMenu(player)).open();
                        EventSound.playSound(player, EventSound.CLICK);
                    })));
        }

        if (gameProfile.getPlayerSettings().getParticleQuality().equals(ParticleQuality.LOW)) {
            addClickableItem(15, ParticleQuality.LOW.item);
        }
        if (gameProfile.getPlayerSettings().getParticleQuality().equals(ParticleQuality.MEDIUM)) {
            addClickableItem(15, ParticleQuality.MEDIUM.item);
        }
        if (gameProfile.getPlayerSettings().getParticleQuality().equals(ParticleQuality.HIGH)) {
            //addClickableItem(15, ParticleQuality.HIGH.getItem());
            addClickableItem(15, new ItemBuilder(Material.INK_SACK).durability(8)
                    .name(ChatColor.AQUA + "Particle Quality")
                    .lore(new ColorBuilder(ChatColor.RED).bold().create() + "COMING SOON!")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> EventSound.playSound(player, EventSound.ACTION_FAIL))));
        }

        if (gameProfile.hasRank(Rank.HELPER)) {
            addClickableItem(22, new ItemBuilder(Material.EYE_OF_ENDER)
                    .name(ChatColor.GOLD + "Staff Preferences")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                        new InventoryBuilder(player, new StaffSettings(player)).open();
                        EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                    })));
        }
    }

    public class StaffSettings extends GameInventory {

        public StaffSettings(Player player) {
            super("Staff Settings", new SettingsMenu(player));
            GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
            setInventory(Bukkit.getServer().createInventory(null, 36, getInventory().getName()));

            if (!gameProfile.getPlayerSettings().isStealthyJoin()) {
                if (gameProfile.hasRank(Rank.MODERATOR)) {
                    addClickableItem(12, new ItemBuilder(Material.INK_SACK).durability(8)
                            .name(ChatColor.GOLD + "Stealthy Join: " + new ColorBuilder(ChatColor.RED).bold().create() + "DISABLED")
                            .lore(ChatColor.GRAY + "Enabling this will cause you to join")
                            .lore(ChatColor.GRAY + "the server without the notifications")
                            .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                                gameProfile.getPlayerSettings().setStealthyJoin(true);
                                new InventoryBuilder(player, new StaffSettings(player)).open();
                                EventSound.playSound(player, EventSound.CLICK);
                            })));
                } else {
                    addClickableItem(12, new ItemBuilder(Material.INK_SACK).durability(8)
                            .name(ChatColor.GOLD + "Stealthy Join: " + new ColorBuilder(ChatColor.RED).bold().create() + "DISABLED")
                            .lore(ChatColor.GRAY + "Must be a " + Rank.MODERATOR.getColor() + "" + ChatColor.BOLD + "MODERATOR"
                                    + ChatColor.GRAY + " to enable this!")
                            .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> EventSound.playSound(player, EventSound.ACTION_FAIL))));
                }
            } else {
                addClickableItem(12, new ItemBuilder(Material.INK_SACK).durability(10)
                        .name(ChatColor.GOLD + "Stealthy Join: " + new ColorBuilder(ChatColor.GREEN).bold().create() + "ENABLED")
                        .lore(ChatColor.GRAY + "Disabling this will cause you to join")
                        .lore(ChatColor.GRAY + "the server with notifications")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            gameProfile.getPlayerSettings().setStealthyJoin(false);
                            new InventoryBuilder(player, new StaffSettings(player)).open();
                            EventSound.playSound(player, EventSound.CLICK);
                        })));
            }
            if (!BattlegroundsCore.getCmdspies().contains(player.getUniqueId())) {
                addClickableItem(14, new ItemBuilder(Material.INK_SACK).durability(8)
                        .name(ChatColor.GOLD + "Command Spy: " + new ColorBuilder(ChatColor.RED).bold().create() + "DISABLED")
                        .lore(ChatColor.GRAY + "Enabling this will allow you to see")
                        .lore(ChatColor.GRAY + "every command that players execute")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            BattlegroundsCore.getCmdspies().add(player.getUniqueId());
                            new InventoryBuilder(player, new StaffSettings(player)).open();
                            EventSound.playSound(player, EventSound.CLICK);
                        })));
            } else {
                addClickableItem(14, new ItemBuilder(Material.INK_SACK).durability(10)
                        .name(ChatColor.GOLD + "Command Spy: " + new ColorBuilder(ChatColor.GREEN).bold().create() + "ENABLED")
                        .lore(ChatColor.GRAY + "Disabling this will stop you from seeing")
                        .lore(ChatColor.GRAY + "every command that players execute")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            BattlegroundsCore.getCmdspies().remove(player.getUniqueId());
                            new InventoryBuilder(player, new StaffSettings(player)).open();
                            EventSound.playSound(player, EventSound.CLICK);
                        })));
            }
            addClickableItem(31, InventoryItems.back.clone()
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                        new InventoryBuilder(player, getPreviousInventory()).open();
                        EventSound.playSound(player, EventSound.INVENTORY_GO_BACK);
                    })));
        }
    }
}
