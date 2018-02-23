package com.battlegroundspvp.worldpvp.kits;
/* Created by GamerBah on 8/15/2016 */


import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.worldpvp.WorldPvP;
import com.battlegroundspvp.worldpvp.kits.common.*;
import com.battlegroundspvp.worldpvp.kits.epic.Baker;
import com.battlegroundspvp.worldpvp.kits.epic.Enderknight;
import com.battlegroundspvp.worldpvp.kits.epic.GlassCannon;
import com.battlegroundspvp.worldpvp.kits.epic.Sniper;
import com.battlegroundspvp.worldpvp.kits.legendary.Ninja;
import com.battlegroundspvp.worldpvp.kits.legendary.Potato;
import com.battlegroundspvp.worldpvp.kits.legendary.UltraTank;
import com.battlegroundspvp.worldpvp.kits.rare.Blaze;
import com.battlegroundspvp.worldpvp.kits.rare.Breaker;
import com.battlegroundspvp.worldpvp.kits.rare.HonorGuard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class KitManager implements Listener {

    @Getter
    private static List<Kit> kits = new ArrayList<>();
    @Getter
    private static Map<UUID, Kit> playersInKits = new HashMap<>();
    @Getter
    private static Map<UUID, Kit> previousKit = new HashMap<>();

    public KitManager(BattlegroundsKitPvP plugin) {
        // Common Kits
        kits.add(new Warrior());
        kits.add(new Bowman());
        kits.add(new Tank());
        kits.add(new Scout());
        kits.add(new Healer());

        // Rare Kits
        kits.add(new Breaker());
        kits.add(new HonorGuard());
        kits.add(new Blaze());

        // Epic Kits
        kits.add(new GlassCannon());
        kits.add(new Sniper());
        kits.add(new Enderknight());
        kits.add(new Baker());

        // Legendary Kits
        kits.add(new UltraTank());
        kits.add(new Potato());
        kits.add(new Ninja());

        for (Kit kit : kits) {
            plugin.getServer().getPluginManager().registerEvents(kit, plugin);
            getCommandMap().register(plugin.getConfig().getName(), getCommand(kit.getName().replaceAll("\\s+", ""), plugin));
            plugin.getCommand(kit.getName().replaceAll("\\s+", "")).setExecutor(kit);
            WorldPvP.getKitRewards().add(kit);
        }
        WorldPvP.getKitRewards().remove(0);
    }

    public static PluginCommand getCommand(String name, Plugin plugin) {
        PluginCommand command = null;

        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return command;
    }

    public static CommandMap getCommandMap() {
        CommandMap commandMap = null;

        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);

                commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commandMap;
    }

    public static boolean isPlayerInKit(Player player, Class<? extends Kit> kit) {
        return playersInKits.containsKey(player.getUniqueId()) && playersInKits.get(player.getUniqueId()).getClass().equals(kit);
    }

    public static boolean isPlayerInKit(Player player) {
        return playersInKits.containsKey(player.getUniqueId());
    }

    public static boolean isPlayerInKit(Player player, Kit kit) {
        return playersInKits.containsKey(player.getUniqueId()) && playersInKits.get(player.getUniqueId()).equals(kit);
    }

    public static Kit fromId(int id) {
        for (Kit kit : kits)
            if (kit.getId() == id)
                return kit;
        return new Warrior();
    }

    @EventHandler
    public final void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (isPlayerInKit(player)) {
            previousKit.put(player.getUniqueId(), playersInKits.get(player.getUniqueId()));
            playersInKits.remove(player.getUniqueId());
        }
    }

    public final void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (isPlayerInKit(player)) {
            playersInKits.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public final void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (isPlayerInKit(player)) {
            playersInKits.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public final void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (isPlayerInKit(player)) {
            previousKit.put(player.getUniqueId(), playersInKits.get(player.getUniqueId()));
            playersInKits.remove(player.getUniqueId());
        }
    }

}
