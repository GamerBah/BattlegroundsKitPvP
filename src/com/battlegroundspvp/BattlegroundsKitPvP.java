package com.battlegroundspvp;
/* Created by GamerBah on 10/8/2017 */

import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.global.listeners.CombatListener;
import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.global.utils.kits.KitAbility;
import com.battlegroundspvp.ranked.Ranked;
import com.battlegroundspvp.unranked.Unranked;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.worldpvp.WorldPvP;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import com.battlegroundspvp.worldpvp.listeners.ScoreboardListener;
import com.battlegroundspvp.worldpvp.utils.KDRatio;
import com.battlegroundspvp.worldpvp.utils.UpdateRunnable;
import de.Herbystar.TTA.TTA_Methods;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BattlegroundsKitPvP extends JavaPlugin {

    @Getter
    private static BattlegroundsKitPvP instance;

    @Getter
    private static HashSet<UUID> afk = new HashSet<>();

    public static Map<UUID, Integer> killStreak = new HashMap<>();
    public static Map<String, String> pendingTeams = new HashMap<>();
    public static Map<String, String> currentTeams = new ConcurrentHashMap<>();
    public static Map<Player, Player> pendingFriends = new HashMap<>();


    private static WorldPvP worldPvP;
    private static Unranked unranked;
    private static Ranked ranked;

    public void onEnable() {
        instance = this;
        worldPvP = new WorldPvP();
        unranked = new Unranked();
        ranked = new Ranked();
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new UpdateRunnable(this), 120, 120);
        update();

        registerCommands();
        registerEvents();

    }

    public void onDisable() {
    }

    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new CombatListener(this), this);
        worldPvP.registerEvents(pluginManager);
        unranked.registerEvents(pluginManager);
        ranked.registerEvents(pluginManager);
    }

    private void registerCommands() {

        worldPvP.registerCommands();
        unranked.registerCommands();
        ranked.registerCommands();
    }

    public static void runRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(20);

        if (!WorldPvP.getNoFall().contains(player))
            WorldPvP.getNoFall().add(player);

        if (KitAbility.getPlayerStatus().containsKey(player.getName())) {
            KitAbility.getPlayerStatus().remove(player.getName());
            player.setExp(0);
            player.setLevel(0);
        }

        player.getInventory().setItem(0, new ItemBuilder(Material.NETHER_STAR)
                .name(new ColorBuilder(ChatColor.AQUA).bold().create() + "Kit Selector" + ChatColor.GRAY + " (Right-Click)")
                .lore(ChatColor.GRAY + "Choose which kit you'll use!"));

        if (KitManager.getPreviousKit().containsKey(player.getUniqueId())) {
            Kit kit = KitManager.getPreviousKit().get(player.getUniqueId());
            player.getInventory().setItem(1, new ItemBuilder(Material.BOOK)
                    .name(new ColorBuilder(ChatColor.GREEN).bold().create() + "Previous Kit: " + kit.getRarity().getColor() + (kit.getRarity() == Rarity.EPIC || kit.getRarity() == Rarity.LEGENDARY ?
                            "" + ChatColor.BOLD : "") + kit.getName() + ChatColor.GRAY + " (Right-Click)")
                    .lore(ChatColor.GRAY + "Equips your previous kit"));
        }
    }

    public static void update() {
        ScoreboardListener scoreboardListener = new ScoreboardListener();
        for (Player player : BattlegroundsKitPvP.getInstance().getServer().getOnlinePlayers()) {
            TTA_Methods.removeBossBar(player);
            GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
            KDRatio kdRatio = new KDRatio();
            scoreboardListener.getRanks().put(player.getUniqueId(), gameProfile.getRank().getColor() + "" + ChatColor.BOLD + gameProfile.getRank().getName().toUpperCase());
            scoreboardListener.getKills().put(player.getUniqueId(), gameProfile.getKitPvpData().getKills());
            scoreboardListener.getDeaths().put(player.getUniqueId(), gameProfile.getKitPvpData().getDeaths());
            scoreboardListener.getKds().put(player.getUniqueId(), ChatColor.GRAY + "" + kdRatio.getRatio(gameProfile));
            scoreboardListener.getSouls().put(player.getUniqueId(), gameProfile.getKitPvpData().getSouls());
            scoreboardListener.getCoins().put(player.getUniqueId(), gameProfile.getCoins());
            scoreboardListener.updateScoreboardRank(player);
            scoreboardListener.updateScoreboardKills(player, 0);
            scoreboardListener.updateScoreboardDeaths(player, 0);
            scoreboardListener.updateScoreboardSouls(player, 0);
            scoreboardListener.updateScoreboardCoins(player, 0);
        }
    }

    public static void runProtection() {

    }

}
