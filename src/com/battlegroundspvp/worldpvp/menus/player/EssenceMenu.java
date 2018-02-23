package com.battlegroundspvp.worldpvp.menus.player;
/* Created by GamerBah on 8/17/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.administration.donations.DonationMessages;
import com.battlegroundspvp.administration.donations.Essence;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.utils.inventories.*;
import com.battlegroundspvp.utils.messages.ColorBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EssenceMenu extends GameInventory {

    public EssenceMenu(Player player) {
        super("Battle Essences", new ProfileMenu(player));
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        int oneSlot = 0, threeSlot = 18, sixSlot = 36;

        for (int f = 0; f < gameProfile.getEssenceData().getOne50(); f++) {
            Essence.Type essence = Essence.Type.ONE_HOUR_50_PERCENT;
            if (oneSlot < 18) {
                addButton(oneSlot++, new ItemBuilder(Material.BLAZE_POWDER).name(essence.getDisplayName(true)).amount(1)
                        .lore(ChatColor.GRAY + "Grants a server-wide " + essence.getChatColor() + essence.getPercent() + "% increase" + ChatColor.GRAY + " to")
                        .lore(ChatColor.GRAY + "the amount of Souls and Battle Coins players")
                        .lore(ChatColor.GRAY + "receive when they kill another player").lore(" ")
                        .lore(ChatColor.GRAY + "Lasts for " + ChatColor.RED + "1 Hour" + ChatColor.GRAY + " upon activation").lore(" ")
                        .lore(new ColorBuilder(ChatColor.YELLOW).bold().create() + "CLICK TO ACTIVATE!")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            if (!BattlegroundsCore.getInstance().getConfig().getBoolean("essenceActive")) {
                                Essence e = new Essence();
                                e.activateEssence(player, essence);
                                player.closeInventory();
                                player.sendMessage(ChatColor.YELLOW + "You activated your " + essence.getDisplayName(true) + ChatColor.YELLOW + " Battle Essence! Enjoy!");
                                player.sendMessage(new ColorBuilder(ChatColor.GREEN).bold().create() + "Thanks again for the purchase!");
                                DonationMessages donationMessages = new DonationMessages(BattlegroundsCore.getInstance());
                                donationMessages.sendEssenceActivationMessage(Essence.fromId(essence.getId()), player);
                            } else {
                                player.closeInventory();
                                player.sendMessage(new ColorBuilder(ChatColor.RED).bold().create() + "Sorry! " + ChatColor.GRAY + "Someone already has an active Battle Essence!");
                                EventSound.playSound(player, EventSound.ACTION_FAIL);
                            }
                        })));
            }
        }
        for (int f = 0; f < gameProfile.getEssenceData().getOne100(); f++) {
            Essence.Type essence = Essence.Type.ONE_HOUR_100_PERCENT;
            if (oneSlot < 18) {
                addButton(oneSlot++, new ItemBuilder(Material.BLAZE_POWDER).name(essence.getDisplayName(true)).amount(1)
                        .lore(ChatColor.GRAY + "Grants a server-wide " + essence.getChatColor() + essence.getPercent() + "% increase" + ChatColor.GRAY + " to")
                        .lore(ChatColor.GRAY + "the amount of Souls and Battle Coins players")
                        .lore(ChatColor.GRAY + "receive when they kill another player").lore(" ")
                        .lore(ChatColor.GRAY + "Lasts for " + ChatColor.RED + "1 Hour" + ChatColor.GRAY + " upon activation").lore(" ")
                        .lore(new ColorBuilder(ChatColor.YELLOW).bold().create() + "CLICK TO ACTIVATE!")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            if (!BattlegroundsCore.getInstance().getConfig().getBoolean("essenceActive")) {
                                Essence e = new Essence();
                                e.activateEssence(player, essence);
                                player.closeInventory();
                                player.sendMessage(ChatColor.YELLOW + "You activated your " + essence.getDisplayName(true) + ChatColor.YELLOW + " Battle Essence! Enjoy!");
                                player.sendMessage(new ColorBuilder(ChatColor.GREEN).bold().create() + "Thanks again for the purchase!");
                                DonationMessages donationMessages = new DonationMessages(BattlegroundsCore.getInstance());
                                donationMessages.sendEssenceActivationMessage(Essence.fromId(essence.getId()), player);
                            } else {
                                player.closeInventory();
                                player.sendMessage(new ColorBuilder(ChatColor.RED).bold().create() + "Sorry! " + ChatColor.GRAY + "Someone already has an active Battle Essence!");
                                EventSound.playSound(player, EventSound.ACTION_FAIL);
                            }
                        })));
            }
        }
        for (int f = 0; f < gameProfile.getEssenceData().getOne150(); f++) {
            Essence.Type essence = Essence.Type.ONE_HOUR_150_PERCENT;
            if (oneSlot < 18) {
                addButton(oneSlot++, new ItemBuilder(Material.BLAZE_POWDER).name(essence.getDisplayName(true)).amount(1)
                        .lore(ChatColor.GRAY + "Grants a server-wide " + essence.getChatColor() + essence.getPercent() + "% increase" + ChatColor.GRAY + " to")
                        .lore(ChatColor.GRAY + "the amount of Souls and Battle Coins players")
                        .lore(ChatColor.GRAY + "receive when they kill another player").lore(" ")
                        .lore(ChatColor.GRAY + "Lasts for " + ChatColor.RED + "1 Hour" + ChatColor.GRAY + " upon activation").lore(" ")
                        .lore(new ColorBuilder(ChatColor.YELLOW).bold().create() + "CLICK TO ACTIVATE!")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            if (!BattlegroundsCore.getInstance().getConfig().getBoolean("essenceActive")) {
                                Essence e = new Essence();
                                e.activateEssence(player, essence);
                                player.closeInventory();
                                player.sendMessage(ChatColor.YELLOW + "You activated your " + essence.getDisplayName(true) + ChatColor.YELLOW + " Battle Essence! Enjoy!");
                                player.sendMessage(new ColorBuilder(ChatColor.GREEN).bold().create() + "Thanks again for the purchase!");
                                DonationMessages donationMessages = new DonationMessages(BattlegroundsCore.getInstance());
                                donationMessages.sendEssenceActivationMessage(Essence.fromId(essence.getId()), player);
                            } else {
                                player.closeInventory();
                                player.sendMessage(new ColorBuilder(ChatColor.RED).bold().create() + "Sorry! " + ChatColor.GRAY + "Someone already has an active Battle Essence!");
                                EventSound.playSound(player, EventSound.ACTION_FAIL);
                            }
                        })));
            }
        }

        for (int f = 0; f < gameProfile.getEssenceData().getThree50(); f++) {
            Essence.Type essence = Essence.Type.THREE_HOUR_50_PERCENT;
            if (threeSlot < 36) {
                addButton(threeSlot++, new ItemBuilder(Material.BLAZE_POWDER).name(essence.getDisplayName(true)).amount(3)
                        .lore(ChatColor.GRAY + "Grants a server-wide " + essence.getChatColor() + essence.getPercent() + "% increase" + ChatColor.GRAY + " to")
                        .lore(ChatColor.GRAY + "the amount of Souls and Battle Coins players")
                        .lore(ChatColor.GRAY + "receive when they kill another player").lore(" ")
                        .lore(ChatColor.GRAY + "Lasts for " + ChatColor.RED + "3 Hours" + ChatColor.GRAY + " upon activation").lore(" ")
                        .lore(new ColorBuilder(ChatColor.YELLOW).bold().create() + "CLICK TO ACTIVATE!")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            if (!BattlegroundsCore.getInstance().getConfig().getBoolean("essenceActive")) {
                                Essence e = new Essence();
                                e.activateEssence(player, essence);
                                player.closeInventory();
                                player.sendMessage(ChatColor.YELLOW + "You activated your " + essence.getDisplayName(true) + ChatColor.YELLOW + " Battle Essence! Enjoy!");
                                player.sendMessage(new ColorBuilder(ChatColor.GREEN).bold().create() + "Thanks again for the purchase!");
                                DonationMessages donationMessages = new DonationMessages(BattlegroundsCore.getInstance());
                                donationMessages.sendEssenceActivationMessage(Essence.fromId(essence.getId()), player);
                            } else {
                                player.closeInventory();
                                player.sendMessage(new ColorBuilder(ChatColor.RED).bold().create() + "Sorry! " + ChatColor.GRAY + "Someone already has an active Battle Essence!");
                                EventSound.playSound(player, EventSound.ACTION_FAIL);
                            }
                        })));
            }
        }
        for (int f = 0; f < gameProfile.getEssenceData().getThree100(); f++) {
            Essence.Type essence = Essence.Type.THREE_HOUR_100_PERCENT;
            if (threeSlot < 36) {
                addButton(threeSlot++, new ItemBuilder(Material.BLAZE_POWDER).name(essence.getDisplayName(true)).amount(3)
                        .lore(ChatColor.GRAY + "Grants a server-wide " + essence.getChatColor() + essence.getPercent() + "% increase" + ChatColor.GRAY + " to")
                        .lore(ChatColor.GRAY + "the amount of Souls and Battle Coins players")
                        .lore(ChatColor.GRAY + "receive when they kill another player").lore(" ")
                        .lore(ChatColor.GRAY + "Lasts for " + ChatColor.RED + "3 Hours" + ChatColor.GRAY + " upon activation").lore(" ")
                        .lore(new ColorBuilder(ChatColor.YELLOW).bold().create() + "CLICK TO ACTIVATE!")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            if (!BattlegroundsCore.getInstance().getConfig().getBoolean("essenceActive")) {
                                Essence e = new Essence();
                                e.activateEssence(player, essence);
                                player.closeInventory();
                                player.sendMessage(ChatColor.YELLOW + "You activated your " + essence.getDisplayName(true) + ChatColor.YELLOW + " Battle Essence! Enjoy!");
                                player.sendMessage(new ColorBuilder(ChatColor.GREEN).bold().create() + "Thanks again for the purchase!");
                                DonationMessages donationMessages = new DonationMessages(BattlegroundsCore.getInstance());
                                donationMessages.sendEssenceActivationMessage(Essence.fromId(essence.getId()), player);
                            } else {
                                player.closeInventory();
                                player.sendMessage(new ColorBuilder(ChatColor.RED).bold().create() + "Sorry! " + ChatColor.GRAY + "Someone already has an active Battle Essence!");
                                EventSound.playSound(player, EventSound.ACTION_FAIL);
                            }
                        })));
            }
        }
        for (int f = 0; f < gameProfile.getEssenceData().getThree150(); f++) {
            Essence.Type essence = Essence.Type.THREE_HOUR_150_PERCENT;
            if (threeSlot < 36) {
                addButton(threeSlot++, new ItemBuilder(Material.BLAZE_POWDER).name(essence.getDisplayName(true)).amount(3)
                        .lore(ChatColor.GRAY + "Grants a server-wide " + essence.getChatColor() + essence.getPercent() + "% increase" + ChatColor.GRAY + " to")
                        .lore(ChatColor.GRAY + "the amount of Souls and Battle Coins players")
                        .lore(ChatColor.GRAY + "receive when they kill another player").lore(" ")
                        .lore(ChatColor.GRAY + "Lasts for " + ChatColor.RED + "3 Hours" + ChatColor.GRAY + " upon activation").lore(" ")
                        .lore(new ColorBuilder(ChatColor.YELLOW).bold().create() + "CLICK TO ACTIVATE!")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            if (!BattlegroundsCore.getInstance().getConfig().getBoolean("essenceActive")) {
                                Essence e = new Essence();
                                e.activateEssence(player, essence);
                                player.closeInventory();
                                player.sendMessage(ChatColor.YELLOW + "You activated your " + essence.getDisplayName(true) + ChatColor.YELLOW + " Battle Essence! Enjoy!");
                                player.sendMessage(new ColorBuilder(ChatColor.GREEN).bold().create() + "Thanks again for the purchase!");
                                DonationMessages donationMessages = new DonationMessages(BattlegroundsCore.getInstance());
                                donationMessages.sendEssenceActivationMessage(Essence.fromId(essence.getId()), player);
                            } else {
                                player.closeInventory();
                                player.sendMessage(new ColorBuilder(ChatColor.RED).bold().create() + "Sorry! " + ChatColor.GRAY + "Someone already has an active Battle Essence!");
                                EventSound.playSound(player, EventSound.ACTION_FAIL);
                            }
                        })));
            }
        }

        for (int f = 0; f < gameProfile.getEssenceData().getSix50(); f++) {
            Essence.Type essence = Essence.Type.SIX_HOUR_50_PERCENT;
            if (sixSlot < 45) {
                addButton(sixSlot++, new ItemBuilder(Material.BLAZE_POWDER).name(essence.getDisplayName(true)).amount(6)
                        .lore(ChatColor.GRAY + "Grants a server-wide " + essence.getChatColor() + essence.getPercent() + "% increase" + ChatColor.GRAY + " to")
                        .lore(ChatColor.GRAY + "the amount of Souls and Battle Coins players")
                        .lore(ChatColor.GRAY + "receive when they kill another player").lore(" ")
                        .lore(ChatColor.GRAY + "Lasts for " + ChatColor.RED + "6 Hours" + ChatColor.GRAY + " upon activation").lore(" ")
                        .lore(new ColorBuilder(ChatColor.YELLOW).bold().create() + "CLICK TO ACTIVATE!")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            if (!BattlegroundsCore.getInstance().getConfig().getBoolean("essenceActive")) {
                                Essence e = new Essence();
                                e.activateEssence(player, essence);
                                player.closeInventory();
                                player.sendMessage(ChatColor.YELLOW + "You activated your " + essence.getDisplayName(true) + ChatColor.YELLOW + " Battle Essence! Enjoy!");
                                player.sendMessage(new ColorBuilder(ChatColor.GREEN).bold().create() + "Thanks again for the purchase!");
                                DonationMessages donationMessages = new DonationMessages(BattlegroundsCore.getInstance());
                                donationMessages.sendEssenceActivationMessage(Essence.fromId(essence.getId()), player);
                            } else {
                                player.closeInventory();
                                player.sendMessage(new ColorBuilder(ChatColor.RED).bold().create() + "Sorry! " + ChatColor.GRAY + "Someone already has an active Battle Essence!");
                                EventSound.playSound(player, EventSound.ACTION_FAIL);
                            }
                        })));
            }
        }
        for (int f = 0; f < gameProfile.getEssenceData().getSix100(); f++) {
            Essence.Type essence = Essence.Type.SIX_HOUR_100_PERCENT;
            if (sixSlot < 45) {
                addButton(sixSlot++, new ItemBuilder(Material.BLAZE_POWDER).name(essence.getDisplayName(true)).amount(6)
                        .lore(ChatColor.GRAY + "Grants a server-wide " + essence.getChatColor() + essence.getPercent() + "% increase" + ChatColor.GRAY + " to")
                        .lore(ChatColor.GRAY + "the amount of Souls and Battle Coins players")
                        .lore(ChatColor.GRAY + "receive when they kill another player").lore(" ")
                        .lore(ChatColor.GRAY + "Lasts for " + ChatColor.RED + "6 Hours" + ChatColor.GRAY + " upon activation").lore(" ")
                        .lore(new ColorBuilder(ChatColor.YELLOW).bold().create() + "CLICK TO ACTIVATE!")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            if (!BattlegroundsCore.getInstance().getConfig().getBoolean("essenceActive")) {
                                Essence e = new Essence();
                                e.activateEssence(player, essence);
                                player.closeInventory();
                                player.sendMessage(ChatColor.YELLOW + "You activated your " + essence.getDisplayName(true) + ChatColor.YELLOW + " Battle Essence! Enjoy!");
                                player.sendMessage(new ColorBuilder(ChatColor.GREEN).bold().create() + "Thanks again for the purchase!");
                                DonationMessages donationMessages = new DonationMessages(BattlegroundsCore.getInstance());
                                donationMessages.sendEssenceActivationMessage(Essence.fromId(essence.getId()), player);
                            } else {
                                player.closeInventory();
                                player.sendMessage(new ColorBuilder(ChatColor.RED).bold().create() + "Sorry! " + ChatColor.GRAY + "Someone already has an active Battle Essence!");
                                EventSound.playSound(player, EventSound.ACTION_FAIL);
                            }
                        })));
            }
        }
        for (int f = 0; f < gameProfile.getEssenceData().getSix150(); f++) {
            Essence.Type essence = Essence.Type.SIX_HOUR_150_PERCENT;
            if (sixSlot < 45) {
                addButton(sixSlot++, new ItemBuilder(Material.BLAZE_POWDER).name(essence.getDisplayName(true)).amount(6)
                        .lore(ChatColor.GRAY + "Grants a server-wide " + essence.getChatColor() + essence.getPercent() + "% increase" + ChatColor.GRAY + " to")
                        .lore(ChatColor.GRAY + "the amount of Souls and Battle Coins players")
                        .lore(ChatColor.GRAY + "receive when they kill another player").lore(" ")
                        .lore(ChatColor.GRAY + "Lasts for " + ChatColor.RED + "6 Hours" + ChatColor.GRAY + " upon activation").lore(" ")
                        .lore(new ColorBuilder(ChatColor.YELLOW).bold().create() + "CLICK TO ACTIVATE!")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            if (!BattlegroundsCore.getInstance().getConfig().getBoolean("essenceActive")) {
                                Essence e = new Essence();
                                e.activateEssence(player, essence);
                                player.closeInventory();
                                player.sendMessage(ChatColor.YELLOW + "You activated your " + essence.getDisplayName(true) + ChatColor.YELLOW + " Battle Essence! Enjoy!");
                                player.sendMessage(new ColorBuilder(ChatColor.GREEN).bold().create() + "Thanks again for the purchase!");
                                DonationMessages donationMessages = new DonationMessages(BattlegroundsCore.getInstance());
                                donationMessages.sendEssenceActivationMessage(Essence.fromId(essence.getId()), player);
                            } else {
                                player.closeInventory();
                                player.sendMessage(new ColorBuilder(ChatColor.RED).bold().create() + "Sorry! " + ChatColor.GRAY + "Someone already has an active Battle Essence!");
                                EventSound.playSound(player, EventSound.ACTION_FAIL);
                            }
                        })));
            }
        }
        addButton(49, InventoryItems.back.clone()
                .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                    new InventoryBuilder(player, getPreviousInventory()).open();
                    EventSound.playSound(player, EventSound.INVENTORY_GO_BACK);
                })));
    }

}
