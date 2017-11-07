package com.battlegroundspvp.global.utils.kits;

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.utils.ColorBuilder;
import de.Herbystar.TTA.TTA_Methods;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class KitAbility {
    @Getter
    private static Map<Player, Status> playerStatus = new HashMap<>();
    private final int defaultCharges;
    private final double defaultDelay;

    public KitAbility(int defaultCharges, double defaultDelay) {
        this.defaultCharges = defaultCharges;
        this.defaultDelay = defaultDelay;
    }

    private void checkStatus(Player player, Status status) {
        if (!status.isRecharged() && status.isExpired()) {
            rechargeStatus(player, status);
        }
    }

    /**
     * Invoked when we need to create a status object for a player.
     *
     * @param player - the player to create for.
     * @return The new status object.
     */
    protected Status createStatus(Player player) {
        return new Status(player, defaultCharges);
    }

    /**
     * Retrieve the cooldown time and charge count for a given player.
     *
     * @param player - the player.
     * @return Associated status.
     */
    public Status getStatus(Player player) {
        Status status = playerStatus.get(player);
        if (status == null) {
            status = createStatus(player);
            playerStatus.put(player, status);
        } else {
            checkStatus(player, status);
        }
        return status;
    }

    /**
     * Invoked when a status must be recharged.
     *
     * @param player - the player to recharge.
     * @param status - the status to update.
     * @return The updated status.
     */
    protected Status rechargeStatus(Player player, Status status) {
        status.setRecharged(true);
        status.setCharges(defaultCharges);
        return status;
    }

    /**
     * Attempt to use this ability. The player must have at least once charge for this operation
     * to be successful. The player's charge count will be decremented by the given amount.
     * <p>
     * Otherwise, initiate the recharging cooldown and return FALSE.
     *
     * @param player - the player.
     * @return TRUE if the operation was successful, FALSE otherwise.
     */
    public boolean tryUse(Player player) {
        player.setLevel(getStatus(player).getCharges());
        return tryUse(player, 1, defaultDelay);
    }

    /**
     * Attempt to use this ability. The player must have at least once charge for this operation
     * to be successful. The player's charge count will be decremented by the given amount.
     * <p>
     * Otherwise, initiate the recharging cooldown and return FALSE.
     *
     * @param player - the player.
     * @param delay  - the duration of the potential cooldown.
     * @return TRUE if the operation was successful, FALSE otherwise.
     */
    public boolean tryUse(Player player, double delay) {
        return tryUse(player, delay);
    }

    /**
     * Attempt to use this ability. The player must have at least once charge for this operation
     * to be successful. The player's charge count will be decremented by the given amount.
     * <p>
     * Otherwise, initiate the recharging cooldown and return FALSE.
     *
     * @param player  - the player.
     * @param charges - the number of charges to consume.
     * @param delay   - the duration of the potential cooldown.
     * @return TRUE if the operation was successful, FALSE otherwise.
     */
    public boolean tryUse(Player player, int charges, double delay) {
        Status status = getStatus(player);
        int current = status.getCharges();

        // Check cooldown
        if (!status.isExpired()) {
            return false;
        }

        if (current <= charges) {
            status.setRecharged(false);
            status.setCharges(0);
            status.setCooldown(delay);
            player.setLevel(0);
            player.setExp(0F);

            BukkitTask task = BattlegroundsKitPvP.getInstance().getServer().getScheduler().runTaskTimer(BattlegroundsKitPvP.getInstance(), () -> {
                if (playerStatus.containsKey(player)) {
                    status.cooldown -= 0.05;
                    float completion = Float.parseFloat(Double.toString(getStatus(player).getRemainingTime(player) / delay));
                    if (completion >= 0)
                        player.setExp(completion);
                }
            }, 0L, 1L);
            BukkitTask later = BattlegroundsKitPvP.getInstance().getServer().getScheduler().runTaskLater(BattlegroundsKitPvP.getInstance(), () -> {
                if (playerStatus.containsKey(player)) {
                    checkStatus(player, status);
                    BattlegroundsCore.clearTitle(player);
                    TTA_Methods.sendTitle(player, null, 5, 25, 7, new ColorBuilder(ChatColor.GREEN).bold().create() + "\u21d1 ABILITY RECHARGED! \u21d1", 5, 25, 7);
                    player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 2F, 0.3F);
                    player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 2F, 1.3F);
                    player.setExp(0F);
                    player.setLevel(getStatus(player).getCharges());
                    task.cancel();
                }
            }, (long) (delay * 20) + 5);
            BattlegroundsKitPvP.getInstance().getServer().getScheduler().runTaskTimer(BattlegroundsKitPvP.getInstance(), () -> {
                if (!playerStatus.containsKey(player)) {
                    task.cancel();
                    later.cancel();
                }
            }, 0L, 20L);
        } else {
            status.setCharges(current - charges);
            player.setLevel(current - charges);
        }

        return current > 0;
    }

    /**
     * Contains the number of charges and cooldown.
     *
     * @author Kristian Stangeland
     */
    public class Status {
        private int charges;
        private double cooldown;
        private Player player;
        private boolean recharged;

        public Status(Player player, int charges) {
            this.player = player;
            this.charges = charges;
            this.cooldown = 0.00;
            this.recharged = true;
        }

        /**
         * Retrieve the number of times the current player can use this ability before initiating a new cooldown.
         *
         * @return Number of charges.
         */
        public int getCharges() {
            return charges;
        }

        /**
         * Set the number of valid charges left
         *
         * @param charges - the new number of charges.
         */
        public void setCharges(int charges) {
            this.charges = charges;
        }

        /**
         * Retrieve the of seconds until the cooldown expires.
         *
         * @return Number of milliseconds until expiration.
         */
        public double getRemainingTime(Player player) {
            Status status = playerStatus.get(player);
            return status.cooldown;
        }

        /**
         * Determine if the cooldown has expired.
         *
         * @return TRUE if it has, FALSE otherwise.
         */
        public boolean isExpired() {
            double rem = getRemainingTime(player);
            return rem <= 0.0;
        }

        /**
         * Determine if this ability has been recharged when the cooldown last expired.
         *
         * @return TRUE if it has, FALSE otherwise.
         */
        public boolean isRecharged() {
            return recharged;
        }

        /**
         * Set if this ability has been recharged when the cooldown last expired.
         *
         * @param recharged - whether or not the ability has been recharged.
         */
        public void setRecharged(boolean recharged) {
            this.recharged = recharged;
        }

        /**
         * Lock the current ability for the duration of the cooldown.
         *
         * @param delay - the number of milliseconds to wait.
         */
        public void setCooldown(double delay) {
            this.cooldown = delay;
        }
    }
}