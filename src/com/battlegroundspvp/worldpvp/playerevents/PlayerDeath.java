package com.battlegroundspvp.worldpvp.playerevents;
/* Created by GamerBah on 8/9/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.administration.commands.ChatCommands;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.global.listeners.CombatListener;
import com.battlegroundspvp.global.utils.kits.KitAbility;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.cosmetics.Cosmetic;
import com.battlegroundspvp.utils.cosmetics.Gore;
import com.battlegroundspvp.utils.cosmetics.Warcry;
import com.battlegroundspvp.utils.cosmetics.defaultcosmetics.DefaultGore;
import com.battlegroundspvp.worldpvp.listeners.ScoreboardListener;
import com.battlegroundspvp.worldpvp.runnables.RespawnTimer;
import com.battlegroundspvp.worldpvp.utils.ComponentMessages;
import de.Herbystar.TTA.TTA_Methods;
import de.slikey.effectlib.util.ParticleEffect;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerDeath implements Listener {

    private BattlegroundsKitPvP plugin;

    public PlayerDeath(BattlegroundsKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        Location deathLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

        player.setHealth(20.0);

        if (player.getWorld().getBlockAt(player.getLocation().add(0, 2, 0)).getType() == Material.AIR)
            player.teleport(player.getLocation().add(0, 2, 0));

        player.setGameMode(GameMode.SPECTATOR);
        player.setFlySpeed(0f);

        if (KitAbility.getPlayerStatus().containsKey(player))
            KitAbility.getPlayerStatus().remove(player);

        CombatListener.getTagged().remove(player.getUniqueId());
        player.getInventory().setHeldItemSlot(0);

        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        ScoreboardListener scoreboardListener = new ScoreboardListener();
        scoreboardListener.updateScoreboardDeaths(player, 1);
        gameProfile.getKitPvpData().addDeath(1);

        if (killer == null) {
            ParticleEffect.LAVA.display(0, 0.2F, 0, 1, 20, player.getLocation(), 100);
            int i = ThreadLocalRandom.current().nextInt(1, 3 + 1);
            if (plugin.getServer().getOnlinePlayers().size() >= 15 || ChatCommands.chatSilenced) {
                event.setDeathMessage(null);
            } else {
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
                    if (i == 1)
                        event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " lost a fight with gravity");
                    if (i == 2)
                        event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " became one with the ground");
                    if (i == 3)
                        event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " forgot their parachute");
                } else if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LAVA) {
                    event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " tried to swim in lava");
                } else if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                    if (i == 1)
                        event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " forgot to come up for air");
                    if (i == 2)
                        event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " thought they were a fish");
                    if (i == 3)
                        event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " didn't bring their oxygen tank");
                } else if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK
                        || player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE) {
                    if (i == 1)
                        event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " didn't stop, drop, and roll");
                    if (i == 2)
                        event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " couldn't put out the flames");
                    if (i == 3)
                        event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " mistakenly said, \"Flame on!\"");
                } else if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID) {
                    if (i == 1)
                        event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " fell into the unknown");
                    if (i == 2)
                        event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " took a trip to space");
                    if (i == 3)
                        event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " fell forever");
                } else {
                    event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " died");
                }
            }

            new RespawnTimer(player, new ColorBuilder(ChatColor.RED).bold().create() + "You died!").start();
            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (i == 1) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "Sadly, Earth has gravity...", 5, 80, 0);
                } else if (i == 2) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "" + ChatColor.ITALIC + "'I believe I can fly'", 5, 80, 0);
                } else if (i == 3) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "Flying is for airplanes, not people.", 5, 80, 0);
                }
            } else if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LAVA) {
                if (i == 1) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "Reminder: Lava is REALLY HOT.", 5, 80, 0);
                } else if (i == 2) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "Yea, that orange stuff? IT BURNS!", 5, 80, 0);
                } else if (i == 3) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "That probably gave you 5th-degree burns", 5, 80, 0);
                }
            } else if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                if (i == 1) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "Did you really think you were a fish?", 5, 80, 0);
                } else if (i == 2) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "" + ChatColor.ITALIC + "Just keep swimming, just keep swimming...", 5, 80, 0);
                } else if (i == 3) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "There's this neat thing called air. You needed it.", 5, 80, 0);
                }
            } else if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK
                    || player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE) {
                if (i == 1) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "Tip: Stop, Drop, and Roll", 5, 80, 0);
                } else if (i == 2) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "One day, Minecraft will have fire extinguishers", 5, 80, 0);
                } else if (i == 3) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "Perfect for roasting marshmallows!", 5, 80, 0);
                }
            } else if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID) {
                if (i == 1) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "How, exactly, did you get down there?", 5, 80, 0);
                } else if (i == 2) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "Warning: The Void is not safe for people.", 5, 80, 0);
                } else if (i == 3) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "Welcome... to SPACE!", 5, 80, 0);
                }
            } else {
                if (i == 1) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "Looks like you need life insurance!", 5, 80, 0);
                } else if (i == 2) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "That could've been done differently...", 5, 80, 0);
                } else if (i == 3) {
                    TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create()
                            + "You died!", 5, 80, 0, ChatColor.GRAY + "The point of the game is to" + ChatColor.ITALIC + "stay alive", 5, 80, 0);
                }
            }
            BattlegroundsCore.getInstance().getGlobalStats().setTotalSuicides(BattlegroundsCore.getInstance().getGlobalStats().getTotalSuicides() + 1);
            return;
        }

        GameProfile killerProfile = BattlegroundsCore.getInstance().getGameProfile(killer.getUniqueId());

        if (killerProfile.getKitPvpData().getActiveGore() == new DefaultGore().getId()) {
            ParticleEffect.LAVA.display(0, 0.2F, 0, 1, 20, player.getLocation(), 100);
        } else {
            Gore gore = (Gore) Gore.fromId(Cosmetic.ServerType.KITPVP, killerProfile.getKitPvpData().getActiveGore());
            gore.onKill(killer, player);
        }

        Warcry warcry = (Warcry) Warcry.fromId(Cosmetic.ServerType.KITPVP, killerProfile.getKitPvpData().getActiveWarcry());
        warcry.onKill(killer, player);

        scoreboardListener.updateScoreboardKills(killer, 1);
        killerProfile.getKitPvpData().addKill(1);

        gameProfile.getKitPvpData().setLastKilledBy(killerProfile.getId());

        TextComponent killerTCM = new TextComponent(killer.getName());
        killerTCM.setColor(ChatColor.GOLD);
        killerTCM.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentMessages.playerStats(killerProfile)));
        killerTCM.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/options " + killer.getName()));

        TextComponent killedTCM = new TextComponent(player.getName());
        killedTCM.setColor(ChatColor.RED);
        killedTCM.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentMessages.playerStats(gameProfile)));
        killedTCM.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/options " + player.getName()));

        TextComponent wkb = new TextComponent(" killed ");
        wkb.setColor(ChatColor.GRAY);
        wkb.setHoverEvent(null);

        BaseComponent baseComponent = new TextComponent("");
        baseComponent.addExtra(killerTCM);
        baseComponent.addExtra(wkb);
        baseComponent.addExtra(killedTCM);

        if (plugin.getServer().getOnlinePlayers().size() >= 15 || ChatCommands.chatSilenced) {
            event.setDeathMessage(null);
        } else {
            event.setDeathMessage(null);
            plugin.getServer().spigot().broadcast(baseComponent);
        }

        String health;
        if (killer.getHealth() % 2 == 0) {
            health = ChatColor.GRAY + "They had " + ChatColor.RED + (((int) killer.getHealth()) / 2) + " hearts" + ChatColor.GRAY + " left";
        } else {
            health = ChatColor.GRAY + "They had " + ChatColor.RED + (((int) killer.getHealth()) / 2) + ".5 hearts" + ChatColor.GRAY + " left";
        }

        TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create() + "Killed by "
                + new ColorBuilder(ChatColor.GOLD).bold().create() + killer.getName(), 5, 80, 0, health, 5, 80, 0);
        new RespawnTimer(player, new ColorBuilder(ChatColor.RED).bold().create() + "Killed by "
                + new ColorBuilder(ChatColor.GOLD).bold().create() + killer.getName()).start();

        if (killer.getName().equals(player.getName()))
            return;

        killer.setHealth(20);

        int souls = ThreadLocalRandom.current().nextInt(4, 9 + 1);
        int coins = 0;
        if (ThreadLocalRandom.current().nextInt(1, 8 + 1) == 1) {
            coins = ThreadLocalRandom.current().nextInt(1, 4 + 1);
        }
        String eOwner = "";
        boolean essence = plugin.getConfig().getBoolean("essenceActive");
        if (essence) {
            switch (plugin.getConfig().getInt("essenceIncrease")) {
                case 50:
                    souls = (int) Math.ceil(souls * 1.5);
                    coins = (int) Math.ceil(coins * 1.5);
                case 100:
                    souls = (int) Math.ceil(souls * 2);
                    coins = (int) Math.ceil(coins * 2);
                case 150:
                    souls = (int) Math.ceil(souls * 2.5);
                    coins = (int) Math.ceil(coins * 2.5);
            }
            eOwner = plugin.getConfig().getString("essenceOwner");
        }

        if (BattlegroundsKitPvP.killStreak.containsKey(killer.getUniqueId())) {
            BattlegroundsKitPvP.killStreak.put(killer.getUniqueId(), BattlegroundsKitPvP.killStreak.get(killer.getUniqueId()) + 1);
            int killstreak = BattlegroundsKitPvP.killStreak.get(killer.getUniqueId());
            if (killstreak % 5 == 0) {
                plugin.getServer().broadcastMessage(ChatColor.GOLD + killer.getName() + ChatColor.GRAY + " is on a " + new ColorBuilder(ChatColor.RED).bold().create() + killstreak + " killstreak!");
                scoreboardListener.updateScoreboardSouls(killer, (souls * (killstreak / 5)));
                scoreboardListener.updateScoreboardCoins(killer, coins);
                killerProfile.getKitPvpData().addSouls(souls * (killstreak / 5));
                killerProfile.addCoins(coins);

                TTA_Methods.sendActionBar(killer, ChatColor.AQUA + "[+" + souls * ((killstreak / 5) + 1) + " Souls]" + ChatColor.LIGHT_PURPLE
                        + (coins != 0 ? " [+" + coins + (coins == 1 ? " Battle Coin]" : " Battle Coins]") : "")
                        + (essence ? ChatColor.YELLOW + " [" + eOwner + "'s Essence]" : ""));
                TTA_Methods.sendTitle(killer, new ColorBuilder(ChatColor.GREEN).bold().create() + killstreak
                        + " Killstreak!", 2, 20, 10, ChatColor.GRAY + "You killed " + ChatColor.RED + player.getName(), 2, 25, 10);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1);
                if (killerProfile.getKitPvpData().getLastKilledBy() == (gameProfile.getId())) {
                    TTA_Methods.sendTitle(killer, new ColorBuilder(ChatColor.YELLOW).bold().create() + "REVENGE!", 2, 20, 10,
                            ChatColor.GRAY + "You killed " + ChatColor.RED + player.getName(), 2, 20, 10);
                    killerProfile.getKitPvpData().addRevengeKill();
                    killerProfile.getKitPvpData().setLastKilledBy(-1);
                }
            }
        } else {
            BattlegroundsKitPvP.killStreak.put(killer.getUniqueId(), 1);
            scoreboardListener.updateScoreboardSouls(killer, souls);
            scoreboardListener.updateScoreboardCoins(killer, coins);
            killerProfile.getKitPvpData().addSouls(souls);
            killerProfile.addCoins(coins);

            TTA_Methods.sendActionBar(killer, ChatColor.AQUA + "[+" + souls + " Souls]"
                    + ChatColor.LIGHT_PURPLE + (coins != 0 ? " [+" + coins + (coins == 1 ? " Battle Coin]" : " Battle Coins]") : "")
                    + (essence ? ChatColor.YELLOW + " [" + eOwner + "'s Essence]" : ""));
            if (killerProfile.getKitPvpData().getLastKilledBy() == (gameProfile.getId())) {
                TTA_Methods.sendTitle(killer, new ColorBuilder(ChatColor.YELLOW).bold().create() + "REVENGE!", 2, 40, 10,
                        ChatColor.GRAY + "You killed " + ChatColor.RED + player.getName(), 2, 40, 10);
                killerProfile.getKitPvpData().addRevengeKill();
                killerProfile.getKitPvpData().setLastKilledBy(-1);
            } else {
                TTA_Methods.sendTitle(killer, null, 0, 0, 0, ChatColor.GRAY + "You killed " + ChatColor.RED + player.getName(), 2, 30, 10);
            }
        }

        if (BattlegroundsKitPvP.killStreak.containsKey(player.getUniqueId())) {
            if (BattlegroundsKitPvP.killStreak.get(player.getUniqueId()) >= 5) {
                plugin.getServer().broadcastMessage(ChatColor.GOLD + killer.getName()
                        + ChatColor.GRAY + " ended " + ChatColor.RED + player.getName()
                        + "'s " + ChatColor.GRAY + "killstreak of " + new ColorBuilder(ChatColor.RED).bold().create() + BattlegroundsKitPvP.killStreak.get(player.getUniqueId()) + "!");
                killerProfile.getKitPvpData().addKillstreakEnded();

                TTA_Methods.sendTitle(player, new ColorBuilder(ChatColor.RED).bold().create() + "Killed by "
                                + new ColorBuilder(ChatColor.GOLD).bold().create() + killer.getName(), 5, 60, 0,
                        ChatColor.YELLOW + "You reached a " + new ColorBuilder(ChatColor.GOLD).bold().create() + BattlegroundsKitPvP.killStreak.get(player.getUniqueId())
                                + ChatColor.YELLOW + " killstreak! Nice!", 5, 60, 0);

                BattlegroundsCore.getInstance().getGlobalStats().setTotalKillstreaksEnded(BattlegroundsCore.getInstance().getGlobalStats().getTotalKillstreaksEnded() + 1);
            }
            BattlegroundsKitPvP.killStreak.remove(player.getUniqueId());
        }
    }
}
