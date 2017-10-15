package com.battlegroundspvp.worldpvp.runnables;
/* Created by GamerBah on 8/23/2017 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.utils.enums.EventSound;
import de.Herbystar.TTA.TTA_Methods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class RespawnTimer implements Runnable {

    private Thread thread;
    private final String threadName;
    private final Player player;
    private final String deathMessage;

    public RespawnTimer(Player player, String deathMessage) {
        this.player = player;
        this.threadName = player.getName() + "-RepawnTimer";
        this.deathMessage = deathMessage;
    }

    public void run() {
        try {
            Thread.sleep(4000);
            for (int i = 5; i > 0; i--) {
                TTA_Methods.sendTitle(player, deathMessage, 0, 25, 0,
                        ChatColor.GRAY + "Respawning in " + i, 0, 25, 0);
                Thread.sleep(1000);
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        BattlegroundsKitPvP.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(BattlegroundsKitPvP.getInstance(), () -> {
            BattlegroundsCore.getInstance().respawn(player);
            EventSound.playSound(player, EventSound.COMMAND_NEEDS_CONFIRMATION);
        });
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

}
