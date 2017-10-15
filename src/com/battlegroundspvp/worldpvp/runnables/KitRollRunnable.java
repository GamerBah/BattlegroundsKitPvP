package com.battlegroundspvp.worldpvp.runnables;
/* Created by GamerBah on 10/14/2017 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.utils.inventories.InventoryItems;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class KitRollRunnable implements Runnable {

    private Thread thread;
    private final String threadName;
    private final Player player;
    private final Kit[] rewards;
    private final Inventory inventory;
    private int[] rollColumns;

    public KitRollRunnable(Player player, int amount, Inventory inventory) {
        this.player = player;
        this.threadName = player.getName() + "-KitRoll";
        this.inventory = inventory;

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
                    inventory.setItem(i + (9 * x), new ItemBuilder(Material.STAINED_GLASS_PANE).name(""));
                else
                    inventory.setItem(i + (9 * x), InventoryItems.border.clone());

        if (player.getOpenInventory().getTopInventory() == inventory)
            player.updateInventory();

        Kit[] rewards = new Kit[amount];
        for (int i = 0; i < rewards.length; i++) {
            int random = ThreadLocalRandom.current().nextInt(1, 101);
            ArrayList<Kit> rewardable = new ArrayList<>();
            for (Kit kits : KitManager.getKits())
                if (random <= kits.getRarity().getMaxChance() && random >= kits.getRarity().getMinChance())
                    rewardable.add(kits);
            player.sendMessage(rewardable.size() + "");
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
                Thread.sleep(350);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    public class ItemRunnable implements Runnable {

        private Thread thread;
        private final String threadName;
        private final GameProfile gameProfile;
        private final Kit reward;
        private final int column;
        private final Inventory inventory;
        private int elapsed = 0;
        private int sound = 0;

        ItemRunnable(Player player, int column, Kit reward, Inventory inventory) {
            this.threadName = player.getName() + "-Column" + column;
            this.gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
            this.column = column;
            this.reward = reward;
            this.inventory = inventory;
        }

        public void run() {
            while (!Thread.interrupted()) {
                Random random = new Random();
                int i = random.nextInt(KitManager.getKits().size());
                inventory.setItem(column + 36, inventory.getItem(column + 27));
                inventory.setItem(column + 27, inventory.getItem(column + 18));
                inventory.setItem(column + 18, inventory.getItem(column + 9));
                inventory.setItem(column + 9, inventory.getItem(column));
                inventory.setItem(column, KitManager.getKits().get(i).getItem());
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
                if (this.elapsed <= 5000) {
                    try {
                        Thread.sleep(100);
                        this.elapsed += 100;
                        this.sound += 1;
                        if (this.sound == 5)
                            this.sound = 1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (this.elapsed <= 7000) {
                    try {
                        Thread.sleep(175);
                        this.elapsed += 175;
                        this.sound += 1;
                        if (this.sound == 5)
                            this.sound = 1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (this.elapsed <= 10000) {
                    try {
                        Thread.sleep(250);
                        this.elapsed += 250;
                        this.sound += 1;
                        if (this.sound == 5)
                            this.sound = 1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (this.elapsed <= 13000) {
                    if (!isReward()) {
                        try {
                            Thread.sleep(375);
                            this.elapsed += 375;
                            this.sound += 1;
                            if (this.sound == 5)
                                this.sound = 1;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        thread.interrupt();
                        // TODO: Reward
                    }
                }
            }
        }

        boolean isReward() {
            if (inventory.getItem(column + 18) != null)
                if (inventory.getItem(column + 18).getType().equals(reward.getItem().getType()))
                    return true;
            return false;
        }

        void start() {
            if (thread == null) {
                thread = new Thread(this, threadName);
                thread.start();
            }
        }
    }

}
