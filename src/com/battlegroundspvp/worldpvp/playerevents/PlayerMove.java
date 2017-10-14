package com.battlegroundspvp.worldpvp.playerevents;
/* Created by GamerBah on 8/14/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.utils.packets.particles.ParticleEffect;
import com.battlegroundspvp.worldpvp.WorldPvP;
import com.battlegroundspvp.worldpvp.commands.SpectateCommand;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import de.Herbystar.TTA.TTA_Methods;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

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
            Location launcherLoc = null;
            for (Location location : BattlegroundsCore.getInstance().getFLaunchers()) {
                if (BattlegroundsCore.getInstance().getFLaunchers().contains(event.getTo().getBlock().getRelative(BlockFace.DOWN).getLocation())) {
                    launcherLoc = location;
                }
            }
            if (launcherLoc != null) {
                if (player.getGameMode().equals(GameMode.CREATIVE)) {
                    return;
                }
                if (BattlegroundsKitPvP.getAfk().contains(player.getUniqueId())) {
                    return;
                }
                if (!KitManager.isPlayerInKit(player)) {
                    return;
                }
                player.setVelocity(launcherLoc.getDirection().multiply(-3));
                player.setVelocity(new Vector(player.getVelocity().getX(), 1.0D, player.getVelocity().getZ()));
                player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 2, 0.3F);
                WorldPvP.getNoFall().add(player);
                BukkitTask task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
                    ParticleEffect.FIREWORKS_SPARK.display(0, 0.5F, 0, 0.05F, 5, player.getLocation(), 30);
                }, 0L, 1L);
                plugin.getServer().getScheduler().runTaskLater(plugin, task::cancel, 20);
            }
        }
    }


    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {
            if (event.getTo().distance(player.getWorld().getSpawnLocation()) <= 15 || event.getTo().distance(player.getWorld().getSpawnLocation()) >= 60) {
                event.setCancelled(true);
                player.getInventory().addItem(new ItemBuilder(Material.ENDER_PEARL).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Enderpearl"));
            }
        }
    }
}
