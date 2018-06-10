package com.battlegroundspvp.worldpvp.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class SpawnProtectListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        Location location = player.getLocation();

        if (location.getBlockY() >= 94) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Location damagerLocation = event.getDamager().getLocation();
        Location damagedLocation = event.getEntity().getLocation();

        if ((damagerLocation.getBlockY() >= 94
                || (damagedLocation.getBlockY() >= 94))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        Location location = event.getEntity().getLocation();

        if (location.getBlockY() >= 94) {
            entity.remove();
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Entity entity = event.getEntity();
        Location location = entity.getLocation();

        if (location.getBlockY() >= 94) {
            event.setCancelled(true);
            entity.remove();
            return;
        }
        /*if (entity instanceof Arrow)
            BattlegroundsCore.getInstance().getGlobalStats().setTotalArrowsFired(BattlegroundsCore.getInstance().getGlobalStats().getTotalArrowsFired() + 1);
        if (entity instanceof EnderPearl)
            BattlegroundsCore.getInstance().getGlobalStats().setTotalEnderpearlsThrown(BattlegroundsCore.getInstance().getGlobalStats().getTotalEnderpearlsThrown() + 1);*/
    }
}
