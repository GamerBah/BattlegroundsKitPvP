package com.battlegroundspvp.worldpvp.runnables;
/* Created by GamerBah on 10/14/2017 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ClickEvent;
import com.battlegroundspvp.utils.inventories.InventoryBuilder;
import com.battlegroundspvp.utils.inventories.InventoryItems;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.worldpvp.WorldPvP;
import com.battlegroundspvp.worldpvp.listeners.ScoreboardListener;
import com.battlegroundspvp.worldpvp.menus.QuartermasterMenus;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
                    inventory.setItem(i + (9 * x), new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").durability(5));
                else
                    inventory.setItem(i + (9 * x), InventoryItems.border.clone());

        if (player.getOpenInventory().getTopInventory() == inventory)
            player.updateInventory();

        Kit[] rewards = new Kit[amount];
        for (int i = 0; i < rewards.length; i++) {
            int random = ThreadLocalRandom.current().nextInt(1, 101);
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
                Thread.sleep(500);
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
        private boolean last = false;
        private int sleep = 100;
        private int sound = 0;
        private Thread thread;

        ItemRunnable(Player player, int column, Kit reward, Inventory inventory) {
            this.threadName = player.getName() + "-Column" + column;
            this.player = player;
            this.gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
            this.column = column;
            this.reward = reward;
            this.inventory = inventory;
            if (column == rollColumns[rollColumns.length - 1])
                last = true;
        }

        boolean isReward() {
            if (inventory.getItem(column + 18) != null)
                if (inventory.getItem(column + 18).getType().equals(reward.getItem().getType()))
                    return true;
            return false;
        }

        public void run() {
            while (!Thread.interrupted()) {
                Random random = new Random();
                int i = random.nextInt(WorldPvP.getKitRewards().size());
                inventory.setItem(column + 36, inventory.getItem(column + 27));
                inventory.setItem(column + 27, inventory.getItem(column + 18));
                inventory.setItem(column + 18, inventory.getItem(column + 9));
                inventory.setItem(column + 9, inventory.getItem(column));
                if (count != (last ? 10 : 3))
                    inventory.setItem(column, WorldPvP.getKitRewards().get(i).getItem());
                else
                    inventory.setItem(column, reward.getItem());
                if (player.getOpenInventory().getTopInventory().getName().equalsIgnoreCase("Rolling...")) {
                    if (this.sound == 1) {
                        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 0.5F, 0.95F);
                    }
                    if (this.sound % 2 == 0) {

                        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 0.5F, 0.75F);
                    }
                    if (this.sound == 3) {
                        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 0.5F, 0.55F);
                    }
                }

                try {
                    Thread.sleep(sleep);
                    if (sleep <= 175)
                        this.sleep += 1;
                    else if (sleep <= 250)
                        this.sleep += 5;
                    else if (sleep <= 325)
                        this.sleep += 20;
                    else if (sleep <= 400)
                        this.sleep += 35;
                    else if (sleep <= 500)
                        this.sleep += 50;
                    else
                        count++;
                    this.sound += 1;
                    if (this.sound == 5)
                        this.sound = 1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (sleep > 500)
                    if (isReward()) {
                        thread.interrupt();
                        int coins;
                        if (reward.getRarity() == Rarity.EPIC) {
                            EventSound.playSound(player, EventSound.ITEM_RECEIVE_EPIC);
                            coins = ThreadLocalRandom.current().nextInt(8, 16);
                        } else if (reward.getRarity() == Rarity.LEGENDARY) {
                            EventSound.playSound(player, EventSound.ITEM_RECEIVE_LEGENDARY);
                            coins = ThreadLocalRandom.current().nextInt(16, 31);
                        } else {
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.3F, 1.1F);
                            coins = ThreadLocalRandom.current().nextInt(4, 9);
                        }

                        if (gameProfile.getKitPvpData().getOwnedKits().contains(reward.getId() + ",")) {
                            player.sendMessage(ChatColor.GRAY + "You already have the " + reward.getRarity().getColor() + (reward.getRarity() == Rarity.EPIC
                                    || reward.getRarity() == Rarity.LEGENDARY ? "" + ChatColor.BOLD : "") + reward.getName()
                                    + ChatColor.GRAY + " kit, so you got " + new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + coins + " Battle Coins");
                            ScoreboardListener scoreboardListener = new ScoreboardListener();
                            scoreboardListener.updateScoreboardCoins(player, coins);
                            gameProfile.addCoins(coins);
                        } else {
                            gameProfile.getKitPvpData().addOwnedKit(reward.getId());
                        }

                        if (last) {
                            for (int a : rollColumns) {
                                for (int b = 0; b <= 4; b++)
                                    if (b != 2) {
                                        inventory.setItem((a + 1) + (b * 9), null);
                                        inventory.setItem((a - 1) + (b * 9), null);
                                        inventory.setItem(a + (b * 9), null);
                                    }
                            }

                            if (WorldPvP.getRolling().containsKey(player))
                                WorldPvP.getRolling().remove(player);

                            inventory.setItem(36, InventoryItems.back.clone().clickEvent(new ClickEvent(ClickEvent.Type.ANY, () -> {
                                new InventoryBuilder(player, new QuartermasterMenus().new SlotSelectionMenu(player)).open();
                                EventSound.playSound(player, EventSound.INVENTORY_GO_BACK);
                            })));

                            this.player.openInventory(this.inventory);
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
