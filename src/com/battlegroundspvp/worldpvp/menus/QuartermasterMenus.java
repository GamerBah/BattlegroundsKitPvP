package com.battlegroundspvp.worldpvp.menus;
/* Created by GamerBah on 10/14/2017 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.administration.data.Rank;
import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.*;
import com.battlegroundspvp.utils.inventories.sortingtypes.RaritySort;
import com.battlegroundspvp.utils.messages.ColorBuilder;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import com.battlegroundspvp.worldpvp.listeners.ScoreboardListener;
import com.battlegroundspvp.worldpvp.runnables.KitRollRunnable;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QuartermasterMenus {

    public class SelectionMenu extends GameInventory {
        public SelectionMenu(Player player) {
            super("Quartermaster", 27, null);
            GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
            addButton(12, new ItemBuilder(Material.DIAMOND)
                    .name(new ColorBuilder(ChatColor.GREEN).bold().create() + "Buy Kits")
                    .lore(ChatColor.GRAY + "Purchase a random kit")
                    .lore(ChatColor.GRAY + "from the Quartermaster")
                    .lore(ChatColor.GRAY + "for " + ChatColor.AQUA + "150 Souls" + ChatColor.GRAY + " each")
                    .lore("").lore(ChatColor.GRAY + "You have " + ChatColor.AQUA + gameProfile.getKitPvpData().getSouls() + " Souls")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                        new InventoryBuilder(player, new SlotSelectionMenu(player)).open();
                        EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                    })));
            addButton(14, new ItemBuilder(Material.CHEST)
                    .name(new ColorBuilder(ChatColor.YELLOW).bold().create() + "Kit Inventory")
                    .lore(ChatColor.GRAY + "View duplicate kits you")
                    .lore(ChatColor.GRAY + "own and extract souls from them!")
                    .lore("").lore(ChatColor.GRAY + "You have " + ChatColor.DARK_PURPLE + gameProfile.getKitPvpData().getDuplicateKits().size() + " Duplicates")
                    .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                        new InventoryBuilder(player, new DuplicateMenu(player)).sortItems(new RaritySort().reversed()).open();
                        EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                    })));
        }
    }

    public class SlotRollMenu extends GameInventory {

        SlotRollMenu(Player player, int amount) {
            super("Rolling...", 45, new SlotSelectionMenu(player));
            new KitRollRunnable(player, amount, getInventory()).start();
        }
    }

    public class SlotSelectionMenu extends GameInventory {

        public SlotSelectionMenu(Player player) {
            super("Roll Amount", 36, new SelectionMenu(player));

            addButton(10, getSlotItem(player, 1));
            addButton(12, getSlotItem(player, 2));
            addButton(14, getSlotItem(player, 3));
            addButton(16, getSlotItem(player, 4));
            addButton(27, InventoryItems.back.clone().clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
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
                itemBuilder.name(gameProfile.getKitPvpData().getSouls() >= (1 * slotAmount) && gameProfile.hasRank(Rank.WARRIOR)
                        ? ChatColor.GREEN + "" + slotAmount + " Roll" : ChatColor.RED + "" + slotAmount + (slotAmount > 1 ? " Rolls" : " Roll"));
                itemBuilder.lore(ChatColor.GRAY + "Rolls " + slotAmount + (slotAmount > 1 ? " times" : " time") + " for " + ChatColor.AQUA + "" + (1 * slotAmount) + " Souls")
                        .lore("").lore(gameProfile.getKitPvpData().getSouls() >= (1 * slotAmount)
                        ? ChatColor.GRAY + "You'll have " + ChatColor.AQUA + (gameProfile.getKitPvpData().getSouls() - (1 * slotAmount))
                        + (gameProfile.getKitPvpData().getSouls() - (1 * slotAmount) > 1 ? " Souls" : " Soul") + ChatColor.GRAY + " left"
                        : ChatColor.RED + "You need " + ChatColor.AQUA + ((1 * slotAmount) - gameProfile.getKitPvpData().getSouls()
                        + ((1 * slotAmount) - gameProfile.getKitPvpData().getSouls() > 1 ? " Souls" : " Soul") + ChatColor.RED + "!"));
            }
            if (gameProfile.getKitPvpData().getSouls() >= (1 * slotAmount) && slotAmount <= max) {
                itemBuilder.lore("").lore(new ColorBuilder(ChatColor.YELLOW).bold().create() + "CLICK TO ROLL!").clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                    new InventoryBuilder(player, new SlotRollMenu(player, slotAmount)).open();
                    EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                    ScoreboardListener scoreboardListener = new ScoreboardListener();
                    scoreboardListener.updateScoreboardSouls(player, (-1 * slotAmount));
                    gameProfile.getKitPvpData().addSouls(-1 * slotAmount);
                }));
            } else {
                itemBuilder.clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> EventSound.playSound(player, EventSound.ACTION_FAIL)));
            }
            return itemBuilder;
        }
    }

    public class DuplicateMenu extends GameInventory {

        public DuplicateMenu(Player player) {
            super("Duplicate Kits", BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId()).getKitPvpData().getDuplicateKits().size(), 54, new SelectionMenu(player));
            setSearchRows(0, 4);
            alterNavigation(false, false);
            setPageRow(5);
            GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
            if (gameProfile.getKitPvpData().getDuplicateKits().size() == 0) {
                addButton(22, InventoryItems.nothing.clone()
                        .lore(ChatColor.GOLD + "You don't have any duplicate kits!")
                        .lore(ChatColor.GRAY + "Duplicates will show here when you")
                        .lore(ChatColor.GRAY + "receive them after buying kits from")
                        .lore(ChatColor.GRAY + "the Quartermaster!")
                        .lore("")
                        .lore(ChatColor.YELLOW + "Click to buy some kits!")
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            new InventoryBuilder(player, new SlotSelectionMenu(player)).open();
                            EventSound.playSound(player, EventSound.INVENTORY_OPEN_SUBMENU);
                        })));
            } else {
                for (int id : gameProfile.getKitPvpData().getDuplicateKits()) {
                    Kit kit = KitManager.fromId(id);
                    if (kit.getRarity().hasRarity(Rarity.EPIC)) {
                        addItem(new ItemBuilder(kit.getItem())
                                .lore(" ")
                                .lore(ChatColor.YELLOW + "Left-Click" + ChatColor.GRAY + " to salvage!")
                                .lore(ChatColor.YELLOW + "Right-Click" + ChatColor.GRAY + " to gift!")
                                .flag(ItemFlag.HIDE_ATTRIBUTES)
                                .storeObject(Rarity.class, kit.getRarity())
                                .clickEvent(new ClickEvent(ClickEvent.Type.LEFT, () -> {
                                    new InventoryBuilder(player, new ConfirmationMenu(() -> {
                                        int souls = ThreadLocalRandom.current().nextInt(35, 55);
                                        gameProfile.getKitPvpData().getDuplicateKits().remove(new Integer(kit.getId()));
                                        ScoreboardListener scoreboardListener = new ScoreboardListener();
                                        scoreboardListener.updateScoreboardSouls(player, souls);
                                        gameProfile.getKitPvpData().addSouls(souls);
                                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Salvaged " + ChatColor.AQUA + souls + " Souls" + ChatColor.GRAY + " from duplicate "
                                                + kit.getRarity().getColor() + kit.getName() + ChatColor.GRAY + " kit!");
                                        new InventoryBuilder(player, new DuplicateMenu(player)).sortItems(new RaritySort().reversed()).open();
                                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.2F);
                                    }, () -> {
                                        new InventoryBuilder(player, new DuplicateMenu(player)).sortItems(new RaritySort().reversed()).open();
                                        EventSound.playSound(player, EventSound.ACTION_FAIL);
                                    }, new SelectionMenu(player))).open();
                                    EventSound.playSound(player, EventSound.COMMAND_NEEDS_CONFIRMATION);
                                }))
                                .clickEvent(new ClickEvent(ClickEvent.Type.RIGHT, () -> EventSound.playSound(player, EventSound.ACTION_FAIL))));
                    } else {
                        addItem(new ItemBuilder(kit.getItem())
                                .lore(" ")
                                .lore(ChatColor.YELLOW + "Click to salvage!")
                                .flag(ItemFlag.HIDE_ATTRIBUTES)
                                .storeObject(Rarity.class, kit.getRarity())
                                .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                                    int souls = ThreadLocalRandom.current().nextInt(10, 25);
                                    gameProfile.getKitPvpData().getDuplicateKits().remove(new Integer(kit.getId()));
                                    ScoreboardListener scoreboardListener = new ScoreboardListener();
                                    scoreboardListener.updateScoreboardSouls(player, souls);
                                    gameProfile.getKitPvpData().addSouls(souls);
                                    player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Salvaged " + ChatColor.AQUA + souls + " Souls" + ChatColor.GRAY + " from duplicate "
                                            + kit.getRarity().getColor() + kit.getName() + ChatColor.GRAY + " kit!");
                                    new InventoryBuilder(player, new DuplicateMenu(player)).sortItems(new RaritySort().reversed()).open();
                                    EventSound.playSound(player, EventSound.ACTION_SUCCESS);
                                })));
                    }
                }
            }
            int commons = new Long(gameProfile.getKitPvpData().getDuplicateKits().stream().filter(i -> KitManager.fromId(i).getRarity() == Rarity.COMMON).count()).intValue();
            int rares = new Long(gameProfile.getKitPvpData().getDuplicateKits().stream().filter(i -> KitManager.fromId(i).getRarity() == Rarity.RARE).count()).intValue();
            ItemBuilder commonSalvage = new ItemBuilder(Material.CONCRETE_POWDER).durability(7)
                    .name((gameProfile.hasRank(Rank.WARRIOR) ? ChatColor.GRAY : ChatColor.RED) + "Salvage all Commons")
                    .lore(new ColorBuilder(ChatColor.GRAY).italic().create() + "Saves you the time of having")
                    .lore(new ColorBuilder(ChatColor.GRAY).italic().create() + "to manually salvage each kit")
                    .lore(new ColorBuilder(ChatColor.GRAY).italic().create() + "and does it all for you!").lore(" ")
                    .lore(ChatColor.GRAY + "You have " + commons + " Common duplicates");
            if (commons > 0) {
                commonSalvage.lore(" ").lore((gameProfile.hasRank(Rank.WARRIOR) ? ChatColor.YELLOW + "Click to salvage!" : Rank.WARRIOR.getName() + ChatColor.RED + " rank required!"))
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            new InventoryBuilder(player,
                                    new ConfirmationMenu(() -> {
                                        int souls = 0;
                                        List<Integer> toRemove = new ArrayList<>();
                                        for (int i = 0; i < gameProfile.getKitPvpData().getDuplicateKits().size(); i++)
                                            if (KitManager.fromId(gameProfile.getKitPvpData().getDuplicateKits().get(i)).getRarity() == Rarity.COMMON) {
                                                souls += ThreadLocalRandom.current().nextInt(10, 25);
                                                toRemove.add(gameProfile.getKitPvpData().getDuplicateKits().get(i));
                                            }
                                        gameProfile.getKitPvpData().getDuplicateKits().removeAll(toRemove);
                                        ScoreboardListener scoreboardListener = new ScoreboardListener();
                                        scoreboardListener.updateScoreboardSouls(player, souls);
                                        gameProfile.getKitPvpData().addSouls(souls);
                                        new InventoryBuilder(player, new DuplicateMenu(player)).open();
                                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.2F);
                                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Salvaged " + ChatColor.AQUA + souls + " Souls"
                                                + ChatColor.GRAY + " from " + commons + " Common duplicates!");
                                    }, () -> {
                                        new InventoryBuilder(player, this).open();
                                        EventSound.playSound(player, EventSound.ACTION_FAIL);
                                    }, this)).open();
                            EventSound.playSound(player, EventSound.COMMAND_NEEDS_CONFIRMATION);
                        }));
            } else {
                commonSalvage.clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> EventSound.playSound(player, EventSound.ACTION_FAIL)));
            }

            ItemBuilder rareSalvage = new ItemBuilder(Material.CONCRETE_POWDER).durability(11)
                    .name((gameProfile.hasRank(Rank.WARRIOR) ? ChatColor.BLUE : ChatColor.RED) + "Salvage all Rares")
                    .lore(new ColorBuilder(ChatColor.GRAY).italic().create() + "Saves you the time of having")
                    .lore(new ColorBuilder(ChatColor.GRAY).italic().create() + "to manually salvage each kit")
                    .lore(new ColorBuilder(ChatColor.GRAY).italic().create() + "and does it all for you!").lore(" ")
                    .lore(ChatColor.GRAY + "You have " + ChatColor.BLUE + rares + ChatColor.GRAY + " Rare duplicates");
            if (rares > 0) {
                rareSalvage.lore(" ").lore((gameProfile.hasRank(Rank.WARRIOR) ? ChatColor.YELLOW + "Click to salvage!" : Rank.WARRIOR.getName() + ChatColor.RED + " rank required!"))
                        .clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                            new InventoryBuilder(player,
                                    new ConfirmationMenu(() -> {
                                        int souls = 0;
                                        List<Integer> toRemove = new ArrayList<>();
                                        for (int i = 0; i < gameProfile.getKitPvpData().getDuplicateKits().size(); i++)
                                            if (KitManager.fromId(gameProfile.getKitPvpData().getDuplicateKits().get(i)).getRarity() == Rarity.RARE) {
                                                souls += ThreadLocalRandom.current().nextInt(10, 25);
                                                toRemove.add(gameProfile.getKitPvpData().getDuplicateKits().get(i));
                                            }
                                        gameProfile.getKitPvpData().getDuplicateKits().removeAll(toRemove);
                                        ScoreboardListener scoreboardListener = new ScoreboardListener();
                                        scoreboardListener.updateScoreboardSouls(player, souls);
                                        gameProfile.getKitPvpData().addSouls(souls);
                                        new InventoryBuilder(player, new DuplicateMenu(player)).open();
                                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.2F);
                                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Salvaged " + ChatColor.AQUA + souls + " Souls"
                                                + ChatColor.GRAY + " from " + ChatColor.BLUE + rares + ChatColor.GRAY + " Rare duplicates!");
                                    }, () -> {
                                        new InventoryBuilder(player, this).open();
                                        EventSound.playSound(player, EventSound.ACTION_FAIL);
                                    }, this)).open();
                            EventSound.playSound(player, EventSound.COMMAND_NEEDS_CONFIRMATION);
                        }));
            } else {
                rareSalvage.clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> EventSound.playSound(player, EventSound.ACTION_FAIL)));
            }
            addButton(50, commonSalvage);
            addButton(51, rareSalvage);
        }

    }

}
