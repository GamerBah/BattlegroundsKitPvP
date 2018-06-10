package com.battlegroundspvp.worldpvp.runnables;
/* Created by GamerBah on 10/14/2017 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.util.enums.EventSound;
import com.battlegroundspvp.util.enums.Rarity;
import com.battlegroundspvp.util.gui.InventoryItems;
import com.battlegroundspvp.util.message.MessageBuilder;
import com.battlegroundspvp.worldpvp.WorldPvP;
import com.battlegroundspvp.worldpvp.menus.QuartermasterMenus;
import com.gamerbah.inventorytoolkit.InventoryBuilder;
import com.gamerbah.inventorytoolkit.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Random;

public class KitRollRunnable implements Runnable {

    private final Inventory inventory;
    private final Player player;
    private final Kit[] rewards;
    private final String threadName;
    private int[] rollColumns;
    private Thread thread;

    public KitRollRunnable(Player player, int amount, Inventory inventory) {
        this.player = player;
        this.threadName = player.getName() + "-KitRoll";
        this.inventory = inventory;

        if (!WorldPvP.getRolling().containsKey(player))
            WorldPvP.getRolling().put(player, amount);

        if (!WorldPvP.getRollInventory().containsKey(player))
            WorldPvP.getRollInventory().put(player, inventory);

        int[] columns = new int[]{3, 5};
        this.rollColumns = new int[]{4};
        if (amount == 2) {
            columns = new int[]{2, 4, 6};
            this.rollColumns = new int[]{3, 5};
        } else if (amount == 3) {
            columns = new int[]{1, 3, 5, 7};
            this.rollColumns = new int[]{2, 4, 6};
        } else if (amount == 4) {
            columns = new int[]{0, 2, 4, 6, 8};
            this.rollColumns = new int[]{1, 3, 5, 7};
        }
        for (int i : columns)
            for (int x = 0; x <= 4; x++)
                if (x == 2)
                    inventory.setItem(i + (9 * x), InventoryItems.border(DyeColor.LIME));
                else
                    inventory.setItem(i + (9 * x), InventoryItems.border(DyeColor.BLACK));

        if (player.getOpenInventory().getTopInventory() == inventory)
            player.updateInventory();

        Kit[] rewards = new Kit[amount];
        for (int i = 0; i < rewards.length; i++) {
            double random = Math.random();
            ArrayList<Kit> rewardable = new ArrayList<>();
            for (Kit kits : WorldPvP.getKitRewards())
                if (random <= kits.getRarity().getChance() && random >= kits.getRarity().getMinChance(Rarity.COMMON))
                    rewardable.add(kits);
            Random y = new Random();
            int z = y.nextInt(rewardable.size());
            rewards[i] = rewardable.get(z);
        }
        this.rewards = rewards;
    }

    public void run() {
        try {
            Thread.sleep(750);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
            return;
        }
        int x = 0;
        for (int i : rollColumns) {
            new ItemRunnable(player, i, rewards[x++], inventory).start();
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.thread.interrupt();
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    public class ItemRunnable implements Runnable {

        private final int column;
        private final GameProfile gameProfile;
        private final Inventory inventory;
        private final Player player;
        private final Kit reward;
        private final String threadName;
        private int count = 0;
        private boolean last = false, first = false;
        private int sleep = 100, sound = 0;
        private Thread thread;

        ItemRunnable(Player player, int column, Kit reward, Inventory inventory) {
            this.threadName = player.getName() + "-Column" + column;
            this.player = player;
            this.gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
            this.column = column;
            this.reward = reward;
            this.inventory = inventory;
            if (column == rollColumns[rollColumns.length - 1])
                this.last = true;
            if (column == rollColumns[0])
                this.first = true;
        }

        public void run() {
            while (!Thread.interrupted()) {
                Random random = new Random();
                int i = random.nextInt(WorldPvP.getKitRewards().size());
                inventory.setItem(column + 36, inventory.getItem(column + 27));
                inventory.setItem(column + 27, inventory.getItem(column + 18));
                inventory.setItem(column + 18, inventory.getItem(column + 9));
                inventory.setItem(column + 9, inventory.getItem(column));
                if (count != 1) inventory.setItem(column, WorldPvP.getKitRewards().get(i).getItem());
                else inventory.setItem(column, reward.getItem());

                if (player.getOpenInventory().getTopInventory().getName().equalsIgnoreCase("Rolling...")) {
                    if (first) {
                        if (this.sound == 1)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 0.5F, 2F);
                        if (this.sound % 2 == 0)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 0.5F, 1.5F);
                        if (this.sound == 3)
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 0.5F, 1F);
                    }
                }

                try {
                    Thread.sleep(sleep);
                    if (sleep <= 150)
                        this.sleep += 1;
                    else if (sleep <= 275)
                        this.sleep += 15;
                    else if (sleep <= 350)
                        this.sleep += 35;
                    this.sound += 1;
                    if (this.sound == 5)
                        this.sound = 1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (sleep > 350)
                    if (count != 3) {
                        this.count++;
                    } else {
                        thread.interrupt();
                        if (reward.getRarity() == Rarity.EPIC) {
                            Bukkit.getServer().getOnlinePlayers().forEach(p -> {
                                if (p.getLocation().getBlockY() >= 94)
                                    EventSound.playSound(p, EventSound.ITEM_RECEIVE_EPIC);
                            });
                        } else if (reward.getRarity() == Rarity.LEGENDARY) {
                            Bukkit.getServer().getOnlinePlayers().forEach(p -> {
                                if (p.getLocation().getBlockY() >= 94)
                                    EventSound.playSound(p, EventSound.ITEM_RECEIVE_LEGENDARY);
                            });
                        } else player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.3F, 1F);

                        boolean duplicate = gameProfile.getKitPvpData().getOwnedKits().contains(reward.getId());
                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You received the " + reward.getRarity().getColor() + (reward.getRarity() == Rarity.EPIC
                                || reward.getRarity() == Rarity.LEGENDARY ? "" + ChatColor.BOLD : "") + reward.getName()
                                + ChatColor.GRAY + " kit!" + (duplicate ? new MessageBuilder(ChatColor.RED).bold().create() + " \u2021 DUPLICATE \u2021" : ""));

                        if (duplicate) gameProfile.getKitPvpData().addDuplicateKit(reward.getId());
                        else gameProfile.getKitPvpData().addOwnedKit(reward.getId());

                        for (int b = 0; b <= 4; b++) {
                            inventory.setItem((column + 1) + (b * 9), InventoryItems.border(DyeColor.BLACK));
                            inventory.setItem((column - 1) + (b * 9), InventoryItems.border(DyeColor.BLACK));
                            if (b != 2) {
                                ItemBuilder common = new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ");
                                ItemBuilder rare = new ItemBuilder(Material.STAINED_GLASS_PANE).durability(11).name(" ");
                                ItemBuilder epic = new ItemBuilder(Material.STAINED_GLASS_PANE).durability(1).name(" ");
                                ItemBuilder legendary = new ItemBuilder(Material.STAINED_GLASS_PANE).durability(2).name(" ");
                                inventory.setItem(column + (b * 9),
                                        reward.getRarity() == Rarity.LEGENDARY ? legendary : reward.getRarity() == Rarity.EPIC ? epic : reward.getRarity() == Rarity.RARE ? rare : common);
                            }
                        }

                        if (last) {
                            WorldPvP.getRolling().remove(player);
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BattlegroundsCore.getInstance(), () -> {
                                if (WorldPvP.getRollInventory().containsKey(player)) {
                                    if (WorldPvP.getRollInventory().get(player) == inventory) {
                                        WorldPvP.getRollInventory().remove(player);
                                        if (player.getOpenInventory().getTopInventory().getName().equalsIgnoreCase("Rolling...")) {
                                            new InventoryBuilder(player, new QuartermasterMenus().new SlotSelectionMenu(player)).open();
                                            EventSound.playSound(player, EventSound.INVENTORY_GO_BACK);
                                        }
                                    }
                                }
                            }, 200);
                        }
                    }
            }
        }

        void start() {
            if (thread == null) {
                thread = new Thread(this, threadName);
                thread.start();
            }
        }
    }

}
