package com.battlegroundspvp.worldpvp.playerevents;
/* Created by GamerBah on 8/14/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.utils.Launcher;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.utils.messages.ColorBuilder;
import com.battlegroundspvp.worldpvp.WorldPvP;
import com.battlegroundspvp.worldpvp.commands.SpectateCommand;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import de.Herbystar.TTA.TTA_Methods;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerMove implements Listener {

    @Getter
    private static List<Player> launched = new ArrayList<>();
    private BattlegroundsKitPvP plugin;

    public PlayerMove(BattlegroundsKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getLocation().getBlockY() < 94 && !SpectateCommand.getSpectating().contains(player) && player.getGameMode() != GameMode.CREATIVE) {
            if (!KitManager.isPlayerInKit(player) && player.getGameMode().equals(GameMode.ADVENTURE)) {
                BattlegroundsCore.getInstance().respawn(player);
                TTA_Methods.sendTitle(player, null, 5, 40, 10, ChatColor.GRAY + "Choose a kit first!", 5, 40, 10);
                EventSound.playSound(player, EventSound.ACTION_FAIL);
                return;
            }
        }

        if (event.getTo().getBlock().getType().equals(Material.GOLD_PLATE)) {
            Launcher launcher = null;
            for (Launcher launchers : BattlegroundsCore.getLaunchers()) {
                if (launchers.getLocation().distance(event.getTo().getBlock().getLocation()) < 0.5) {
                    launcher = launchers;
                }
            }
            if (launcher != null) {
                if (player.getGameMode().equals(GameMode.CREATIVE)) {
                    return;
                }
                if (BattlegroundsKitPvP.getAfk().contains(player.getUniqueId())) {
                    return;
                }
                if (!KitManager.isPlayerInKit(player)) {
                    if (launcher.getLocation().distance(launcher.getLocation().getWorld().getSpawnLocation()) > 10) {
                        return;
                    }
                }
                if (launcher.getLocation().distance(launcher.getLocation().getWorld().getSpawnLocation()) > 10)
                    WorldPvP.getNoFall().add(player);
                launcher.launch(player);
            }
        }
    }


    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {
            if (event.getTo().getBlockY() >= 94 || event.getTo().getBlock().getType().equals(Material.BARRIER)) {
                event.setCancelled(true);
                player.getInventory().addItem(new ItemBuilder(Material.ENDER_PEARL).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Enderpearl"));
            }
        }
    }
}
