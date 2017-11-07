package com.battlegroundspvp;
/* Created by GamerBah on 10/8/2017 */

import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.global.listeners.CombatListener;
import com.battlegroundspvp.ranked.Ranked;
import com.battlegroundspvp.unranked.Unranked;
import com.battlegroundspvp.worldpvp.WorldPvP;
import com.battlegroundspvp.worldpvp.listeners.ScoreboardListener;
import com.battlegroundspvp.worldpvp.utils.KDRatio;
import com.battlegroundspvp.worldpvp.utils.npcs.QuartermasterTrait;
import lombok.Getter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BattlegroundsKitPvP extends JavaPlugin {

    public static Map<String, String> currentTeams = new ConcurrentHashMap<>();
    public static Map<UUID, Integer> killStreak = new HashMap<>();
    public static Map<Player, Player> pendingFriends = new HashMap<>();
    public static Map<String, String> pendingTeams = new HashMap<>();
    @Getter
    private static HashSet<UUID> afk = new HashSet<>();
    @Getter
    private static BattlegroundsKitPvP instance;
    private static Ranked ranked;
    private static Unranked unranked;
    private static WorldPvP worldPvP;

    public static void update() {
        ScoreboardListener scoreboardListener = new ScoreboardListener();
        for (Player player : BattlegroundsKitPvP.getInstance().getServer().getOnlinePlayers()) {
            GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
            KDRatio kdRatio = new KDRatio();
            scoreboardListener.getRanks().put(player.getUniqueId(), gameProfile.getRank().getColor() + "" + ChatColor.BOLD + gameProfile.getRank().getName().toUpperCase());
            scoreboardListener.getKills().put(player.getUniqueId(), gameProfile.getKitPvpData().getKills());
            scoreboardListener.getDeaths().put(player.getUniqueId(), gameProfile.getKitPvpData().getDeaths());
            scoreboardListener.getKds().put(player.getUniqueId(), ChatColor.GRAY + "" + kdRatio.getRatio(gameProfile));
            scoreboardListener.getSouls().put(player.getUniqueId(), gameProfile.getKitPvpData().getSouls());
            scoreboardListener.getCoins().put(player.getUniqueId(), gameProfile.getCoins());
            scoreboardListener.updateScoreboardRank(player, gameProfile.getRank());
            scoreboardListener.updateScoreboardKills(player, 0);
            scoreboardListener.updateScoreboardDeaths(player, 0);
            scoreboardListener.updateScoreboardSouls(player, 0);
            scoreboardListener.updateScoreboardCoins(player, 0);
        }
    }

    public void onEnable() {
        instance = this;
        worldPvP = new WorldPvP();
        unranked = new Unranked();
        ranked = new Ranked();
        update();

        registerEvents();

        if (CitizensAPI.getTraitFactory().getTrait(QuartermasterTrait.class) != null)
            CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(QuartermasterTrait.class));
    }

    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new CombatListener(this), this);
        worldPvP.registerEvents(pluginManager);
        unranked.registerEvents(pluginManager);
        ranked.registerEvents(pluginManager);
    }


}
