package com.battlegroundspvp.global.listeners;

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.administration.data.Rank;
import com.battlegroundspvp.runnables.UpdateRunnable;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.worldpvp.WorldPvP;
import com.battlegroundspvp.worldpvp.commands.SpectateCommand;
import de.Herbystar.TTA.TTA_Methods;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class CombatListener implements Listener {

    @Getter
    private static HashMap<UUID, Integer> tagged = new HashMap<>();
    private BattlegroundsKitPvP plugin;
    private HashMap<UUID, Long> logged = new HashMap<>();

    public CombatListener(BattlegroundsKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamager() instanceof Player && event.getEntity() instanceof ArmorStand) {
            Player player = (Player) event.getDamager();
            GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());

            if (gameProfile.hasRank(Rank.OWNER)) {
                if (player.getGameMode() == GameMode.CREATIVE) {
                    event.setCancelled(false);
                    event.getEntity().remove();
                } else {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();


            if ((damaged.getLocation().getBlockY() >= 94
                    || (damager.getLocation().getBlockY() >= 94))) {
                event.setCancelled(true);
                return;
            }

            /*if (HackPreventionTools.getTargetPlayer(damager, 4) == null) {
                event.setCancelled(true);
                return;
            }*/

            if (SpectateCommand.getSpectating().contains(damager) || SpectateCommand.getSpectating().contains(damaged)) {
                event.setCancelled(true);
                return;
            }

            if (isTeamed(damaged, damager) || isTeamed(damager, damaged))
                event.setCancelled(true);

            checkTagged(damaged, damager);
            checkTagged(damager, damaged);

            if (damager.getInventory().getItemInMainHand().getType().equals(Material.POISONOUS_POTATO)) {
                damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 2, true, true));
            }
        }

        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                ProjectileSource shooter = arrow.getShooter();
                Player damager = (Player) shooter;
                Player damaged = (Player) event.getEntity();

                if ((damaged.getLocation().getBlockY() >= 94
                        || (damager.getLocation().getBlockY() >= 94))) {
                    event.setCancelled(true);
                    return;
                }

                if (isTeamed(damaged, damager) || isTeamed(damager, damaged))
                    event.setCancelled(true);

                if (damager.getName().equals(damaged.getName())) {
                    event.setCancelled(true);
                    return;
                }
                String health;
                if (damaged.getHealth() % 2 == 0) {
                    health = (((int) damaged.getHealth()) / 2) + "";
                } else {
                    health = (((int) damaged.getHealth()) / 2) + ".5";
                }

                TTA_Methods.sendTitle(damager, null, 0, 0, 0, new ColorBuilder(ChatColor.YELLOW).bold().create() + damaged.getName()
                        + new ColorBuilder(ChatColor.GRAY).bold().create() + " \u00BB " + new ColorBuilder(ChatColor.WHITE).bold().create() + health
                        + new ColorBuilder(ChatColor.RED).bold().create() + " \u2764", 0, 0, 0);

                checkTagged(damaged, damager);
                checkTagged(damager, damaged);
            }
        }

        if (event.getDamager() instanceof SmallFireball && event.getEntity() instanceof Player) {
            SmallFireball smallFireball = (SmallFireball) event.getDamager();
            if (smallFireball.getShooter() instanceof Player) {
                ProjectileSource shooter = smallFireball.getShooter();
                Player damager = (Player) shooter;
                Player damaged = (Player) event.getEntity();

                if (damaged.getLocation().distance(damaged.getWorld().getSpawnLocation()) <= 12) {
                    event.setCancelled(true);
                    return;
                }

                if (isTeamed(damaged, damager) || isTeamed(damager, damager))

                    if (damager.getName().equals(damaged.getName())) {
                        event.setCancelled(true);
                        return;
                    }

                checkTagged(damaged, damager);
                checkTagged(damager, damaged);
            }
        }
    }

    public void checkTagged(Player damaged, Player damager) {
        if (!tagged.containsKey(damaged.getUniqueId())) {
            damaged.sendMessage(ChatColor.GRAY + "You're now in combat with " + ChatColor.RED + damager.getName());
            tagged.put(damaged.getUniqueId(),
                    new BukkitRunnable() {
                        public void run() {
                            tagged.remove(damaged.getUniqueId());
                            damaged.sendMessage(ChatColor.GRAY + "You're no longer in combat.");
                        }
                    }.runTaskLater(plugin, 240).getTaskId());
        } else {
            plugin.getServer().getScheduler().cancelTask(tagged.get(damaged.getUniqueId()));
            tagged.put(damaged.getUniqueId(),
                    new BukkitRunnable() {
                        public void run() {
                            tagged.remove(damaged.getUniqueId());
                            damaged.sendMessage(ChatColor.GRAY + "You're no longer in combat.");
                        }
                    }.runTaskLater(plugin, 240).getTaskId());
        }
    }

    public boolean isTeamed(Player damaged, Player damager) {
        if (BattlegroundsKitPvP.currentTeams.containsKey(damaged.getName()) || BattlegroundsKitPvP.currentTeams.containsKey(damager.getName())) {
            if (BattlegroundsKitPvP.currentTeams.get(damaged.getName()).equals(damager.getName()) || BattlegroundsKitPvP.currentTeams.get(damager.getName()).equals(damaged.getName())) {
                if (plugin.getServer().getOnlinePlayers().size() >= 1) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (tagged.containsKey(player.getUniqueId())) {
            tagged.remove(player.getUniqueId());
            plugin.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + player.getName() + ChatColor.RED + " has logged out while in combat!");
            logged.put(player.getUniqueId(), System.currentTimeMillis() + 120000);
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (tagged.containsKey(player.getUniqueId())
                && (event.getMessage().toLowerCase().startsWith("/spawn"))
                && (event.getMessage().toLowerCase().startsWith("/spectate"))) {
            player.sendMessage(ChatColor.RED + "You cannot use that command during combat!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (tagged.containsKey(player.getUniqueId())) {
            plugin.getServer().getScheduler().cancelTask(tagged.get(player.getUniqueId()));
            tagged.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();

        if (logged.containsKey(uuid)) {
            if (System.currentTimeMillis() < logged.get(uuid)) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ChatColor.RED + "\nYou are banned for combat logging.\nYour ban will expire 2 minutes from when you were banned.");
            } else {
                logged.remove(uuid);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (player.getLocation().getBlockY() >= 94) {
                event.setCancelled(true);
                return;
            }

            if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                if (WorldPvP.getNoFall().contains(player)) {
                    event.setCancelled(true);
                    player.setFallDistance(0);
                    WorldPvP.getNoFall().remove(player);
                }
                if (UpdateRunnable.updating) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
