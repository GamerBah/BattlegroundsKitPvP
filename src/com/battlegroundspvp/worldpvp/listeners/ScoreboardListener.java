package com.battlegroundspvp.worldpvp.listeners;
/* Created by GamerBah on 8/15/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.administration.data.Rank;
import com.battlegroundspvp.util.message.MessageBuilder;
import com.battlegroundspvp.worldpvp.utils.KDRatio;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardListener implements Listener {

    @Getter
    private Map<UUID, Integer> coins = new HashMap<>();
    @Getter
    private Map<UUID, Integer> deaths = new HashMap<>();
    @Getter
    private Map<UUID, String> kds = new HashMap<>();
    @Getter
    private Map<UUID, Integer> kills = new HashMap<>();
    @Getter
    private Map<UUID, String> ranks = new HashMap<>();
    @Getter
    private Map<UUID, Integer> souls = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("PlayerData", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        setupScoreboardTeams(player, board);
        objective.setDisplayName(new MessageBuilder(ChatColor.GOLD).bold().create() + "Battlegrounds");
        if (gameProfile != null) {
            objective.getScore(" ").setScore(10);

            // Rank
            if (gameProfile.getRank().equals(Rank.DEFAULT)) {
                objective.getScore(new MessageBuilder(ChatColor.WHITE).bold().create() + "Rank: " + gameProfile.getRank().getColor().create() + gameProfile.getRank().getName()).setScore(9);
                ranks.put(player.getUniqueId(), gameProfile.getRank().getColor().create() + gameProfile.getRank().getName());
            } else {
                objective.getScore(new MessageBuilder(ChatColor.WHITE).bold().create() + "Rank: " + gameProfile.getRank().getColor().create() + "" + ChatColor.BOLD + gameProfile.getRank().getName().toUpperCase()).setScore(9);
                ranks.put(player.getUniqueId(), gameProfile.getRank().getColor().create() + "" + ChatColor.BOLD + gameProfile.getRank().getName().toUpperCase());
            }

            objective.getScore("  ").setScore(8);

            // Kills, Deaths, KD
            objective.getScore(new MessageBuilder(ChatColor.GREEN).bold().create() + "Kills: " + ChatColor.GRAY + gameProfile.getKitPvpData().getKills()).setScore(7);
            kills.put(player.getUniqueId(), gameProfile.getKitPvpData().getKills());

            objective.getScore(new MessageBuilder(ChatColor.RED).bold().create() + "Deaths: " + ChatColor.GRAY + gameProfile.getKitPvpData().getDeaths()).setScore(6);
            deaths.put(player.getUniqueId(), gameProfile.getKitPvpData().getDeaths());

            KDRatio kdRatio = new KDRatio();
            objective.getScore(new MessageBuilder(ChatColor.YELLOW).bold().create() + "K/D Ratio: " + ChatColor.GRAY + "" + kdRatio.getRatio(gameProfile)).setScore(5);
            kds.put(player.getUniqueId(), ChatColor.GRAY + "" + kdRatio.getRatio(gameProfile));

            objective.getScore("   ").setScore(4);

            // Currencies
            objective.getScore(new MessageBuilder(ChatColor.AQUA).bold().create() + "Souls: " + ChatColor.GRAY + gameProfile.getKitPvpData().getSouls()).setScore(3);
            souls.put(player.getUniqueId(), gameProfile.getKitPvpData().getSouls());

            objective.getScore(new MessageBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Battle Coins: " + ChatColor.GRAY + gameProfile.getCoins()).setScore(2);
            souls.put(player.getUniqueId(), gameProfile.getKitPvpData().getSouls());
        }
        Objective healthObjective = board.registerNewObjective("showhealth", "health");
        healthObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        healthObjective.setDisplayName(ChatColor.RED + "\u2764");
        player.setHealth(player.getHealth());
        player.setScoreboard(board);
    }

    public void reloadScoreboardTeams(Player player, Scoreboard board) {
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        player.setScoreboard(board);
        Team killer = board.getTeam("killer");
        Team buzzkill = board.getTeam("buzzkill");
        Team vengeful = board.getTeam("vengeful");
        Team sadist = board.getTeam("sadist");
        Team fragile = board.getTeam("fragile");
        Team none = board.getTeam("none");

        for (Team team : board.getTeams()) {
            team.setAllowFriendlyFire(true);
            team.setCanSeeFriendlyInvisibles(false);
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        }

        for (Player target : Bukkit.getOnlinePlayers()) {
            GameProfile targetProfile = BattlegroundsCore.getInstance().getGameProfile(target.getUniqueId());
            /*if (targetProfile.getKitPvpData().getTitle().equals(Achievement.Type.BRUTALITY_MASTER.toString())) {
                killer.addEntry(target.getName());
            } else if (targetProfile.getKitPvpData().getTitle().equals(Achievement.Type.BUZZKILL_MASTER.toString())) {
                buzzkill.addEntry(target.getName());
            } else if (targetProfile.getKitPvpData().getTitle().equals(Achievement.Type.VENGEFUL_MASTER.toString())) {
                vengeful.addEntry(target.getName());
            } else if (targetProfile.getKitPvpData().getTitle().equals(Achievement.Type.SADIST_MASTER.toString())) {
                sadist.addEntry(target.getName());
            } else if (targetProfile.getKitPvpData().getTitle().equals(Achievement.Type.DEATHKNELL.toString())) {
                fragile.addEntry(target.getName());
            } else if (targetProfile.getKitPvpData().getTitle().equals("TRAIL_NONE")) {
                none.addEntry(target.getName());
            }

            if (gameProfile.getKitPvpData().getTitle().equals(Achievement.Type.BRUTALITY_MASTER.toString())) {
                target.getScoreboard().getTeam("killer").addEntry(player.getName());
            } else if (gameProfile.getKitPvpData().getTitle().equals(Achievement.Type.BUZZKILL_MASTER.toString())) {
                target.getScoreboard().getTeam("buzzkill").addEntry(player.getName());
            } else if (gameProfile.getKitPvpData().getTitle().equals(Achievement.Type.VENGEFUL_MASTER.toString())) {
                target.getScoreboard().getTeam("vengeful").addEntry(player.getName());
            } else if (gameProfile.getKitPvpData().getTitle().equals(Achievement.Type.SADIST_MASTER.toString())) {
                target.getScoreboard().getTeam("sadist").addEntry(player.getName());
            } else if (gameProfile.getKitPvpData().getTitle().equals(Achievement.Type.DEATHKNELL.toString())) {
                target.getScoreboard().getTeam("fragile").addEntry(player.getName());
            } else if (gameProfile.getKitPvpData().getTitle().equals("TRAIL_NONE")) {
                target.getScoreboard().getTeam("none").addEntry(player.getName());
            }*/
            none.addEntry(target.getName());
            target.getScoreboard().getTeam("none").addEntry(player.getName());
        }
    }

    private void setupScoreboardTeams(Player player, Scoreboard board) {
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        player.setScoreboard(board);
        Team killer = board.registerNewTeam("killer");
        Team buzzkill = board.registerNewTeam("buzzkill");
        Team vengeful = board.registerNewTeam("vengeful");
        Team sadist = board.registerNewTeam("sadist");
        Team fragile = board.registerNewTeam("fragile");
        Team none = board.registerNewTeam("none");
        killer.setSuffix(new MessageBuilder(ChatColor.GOLD).bold().create() + " [KILLER]");
        buzzkill.setSuffix(new MessageBuilder(ChatColor.GOLD).bold().create() + " [BUZZKILL]");
        vengeful.setSuffix(new MessageBuilder(ChatColor.GOLD).bold().create() + " [VENGEFUL]");
        sadist.setSuffix(new MessageBuilder(ChatColor.GOLD).bold().create() + " [SADIST]");
        fragile.setSuffix(new MessageBuilder(ChatColor.GOLD).bold().create() + " [FRAGILE]");

        for (Player target : Bukkit.getOnlinePlayers()) {
            GameProfile targetProfile = BattlegroundsCore.getInstance().getGameProfile(target.getUniqueId());
            /*if (targetProfile.getKitPvpData().getTitle().equals(Achievement.Type.BRUTALITY_MASTER.toString())) {
                killer.addEntry(target.getName());
            } else if (targetProfile.getKitPvpData().getTitle().equals(Achievement.Type.BUZZKILL_MASTER.toString())) {
                buzzkill.addEntry(target.getName());
            } else if (targetProfile.getKitPvpData().getTitle().equals(Achievement.Type.VENGEFUL_MASTER.toString())) {
                vengeful.addEntry(target.getName());
            } else if (targetProfile.getKitPvpData().getTitle().equals(Achievement.Type.SADIST_MASTER.toString())) {
                sadist.addEntry(target.getName());
            } else if (targetProfile.getKitPvpData().getTitle().equals(Achievement.Type.DEATHKNELL.toString())) {
                fragile.addEntry(target.getName());
            } else if (targetProfile.getKitPvpData().getTitle().equals("TRAIL_NONE")) {
                none.addEntry(target.getName());
            }

            if (gameProfile.getKitPvpData().getTitle().equals(Achievement.Type.BRUTALITY_MASTER.toString())) {
                target.getScoreboard().getTeam("killer").addEntry(player.getName());
            } else if (gameProfile.getKitPvpData().getTitle().equals(Achievement.Type.BUZZKILL_MASTER.toString())) {
                target.getScoreboard().getTeam("buzzkill").addEntry(player.getName());
            } else if (gameProfile.getKitPvpData().getTitle().equals(Achievement.Type.VENGEFUL_MASTER.toString())) {
                target.getScoreboard().getTeam("vengeful").addEntry(player.getName());
            } else if (gameProfile.getKitPvpData().getTitle().equals(Achievement.Type.SADIST_MASTER.toString())) {
                target.getScoreboard().getTeam("sadist").addEntry(player.getName());
            } else if (gameProfile.getKitPvpData().getTitle().equals(Achievement.Type.DEATHKNELL.toString())) {
                target.getScoreboard().getTeam("fragile").addEntry(player.getName());
            } else if (gameProfile.getKitPvpData().getTitle().equals("TRAIL_NONE")) {
                target.getScoreboard().getTeam("none").addEntry(player.getName());
            }*/
            none.addEntry(target.getName());
            target.getScoreboard().getTeam("none").addEntry(player.getName());
        }
        for (Team team : board.getTeams()) {
            team.setAllowFriendlyFire(true);
            team.setCanSeeFriendlyInvisibles(false);
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        }
    }

    public void updateScoreboardCoins(Player player, int amount) {
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        Scoreboard board = player.getScoreboard();
        Objective objective = board.getObjective("PlayerData");
        getCoins().put(player.getUniqueId(), gameProfile.getCoins());
        // TODO:
        // BattlegroundsCore.getInstance().getGlobalStats().setTotalCoinsEarned(BattlegroundsCore.getInstance().getGlobalStats().getTotalCoinsEarned() + amount);
        board.resetScores(new MessageBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Battle Coins: " + ChatColor.GRAY + coins.get(player.getUniqueId()));
        objective.getScore(new MessageBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Battle Coins: " + ChatColor.GRAY + (gameProfile.getCoins() + amount)).setScore(2);
        player.setScoreboard(board);
    }

    public void updateScoreboardDeaths(Player player, int amount) {
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        Scoreboard board = player.getScoreboard();
        Objective objective = board.getObjective("PlayerData");
        KDRatio kdRatio = new KDRatio();
        getDeaths().put(player.getUniqueId(), gameProfile.getKitPvpData().getDeaths());
        getKds().put(player.getUniqueId(), ChatColor.GRAY + "" + kdRatio.getRatio(gameProfile));
        // TODO:
        // BattlegroundsCore.getInstance().getGlobalStats().setTotalDeaths(BattlegroundsCore.getInstance().getGlobalStats().getTotalDeaths() + amount);
        board.resetScores(new MessageBuilder(ChatColor.RED).bold().create() + "Deaths: " + ChatColor.GRAY + deaths.get(player.getUniqueId()));
        board.resetScores(new MessageBuilder(ChatColor.YELLOW).bold().create() + "K/D Ratio: " + kds.get(player.getUniqueId()));
        objective.getScore(new MessageBuilder(ChatColor.RED).bold().create() + "Deaths: " + ChatColor.GRAY + (gameProfile.getKitPvpData().getDeaths() + amount)).setScore(6);
        objective.getScore(new MessageBuilder(ChatColor.YELLOW).bold().create() + "K/D Ratio: " + ChatColor.GRAY + ""
                + kdRatio.getRatio(gameProfile.getKitPvpData().getKills(), gameProfile.getKitPvpData().getDeaths() + amount)).setScore(5);
        player.setScoreboard(board);
    }

    public void updateScoreboardKills(Player player, int amount) {
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        Scoreboard board = player.getScoreboard();
        Objective objective = board.getObjective("PlayerData");
        KDRatio kdRatio = new KDRatio();
        getKills().put(player.getUniqueId(), gameProfile.getKitPvpData().getKills());
        getKds().put(player.getUniqueId(), ChatColor.GRAY + "" + kdRatio.getRatio(gameProfile));
        // TODO:
        // BattlegroundsCore.getInstance().getGlobalStats().setTotalKills(BattlegroundsCore.getInstance().getGlobalStats().getTotalKills() + amount);
        board.resetScores(new MessageBuilder(ChatColor.GREEN).bold().create() + "Kills: " + ChatColor.GRAY + kills.get(player.getUniqueId()));
        board.resetScores(new MessageBuilder(ChatColor.YELLOW).bold().create() + "K/D Ratio: " + kds.get(player.getUniqueId()));
        objective.getScore(new MessageBuilder(ChatColor.GREEN).bold().create() + "Kills: " + ChatColor.GRAY + (gameProfile.getKitPvpData().getKills() + amount)).setScore(7);
        objective.getScore(new MessageBuilder(ChatColor.YELLOW).bold().create() + "K/D Ratio: " + ChatColor.GRAY + ""
                + kdRatio.getRatio(gameProfile.getKitPvpData().getKills() + amount, gameProfile.getKitPvpData().getDeaths())).setScore(5);
        player.setScoreboard(board);
    }

    public void updateScoreboardRank(Player player, Rank rank) {
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        Scoreboard board = player.getScoreboard();
        Objective objective = board.getObjective("PlayerData");
        getRanks().put(player.getUniqueId(), gameProfile.getRank().getColor().create() + (gameProfile.hasRank(Rank.WARRIOR)
                ? "" + ChatColor.BOLD + gameProfile.getRank().getName().toUpperCase() : gameProfile.getRank().getName()));
        board.resetScores(new MessageBuilder(ChatColor.WHITE).bold().create() + "Rank: " + ranks.get(player.getUniqueId()));
        if (gameProfile.getRank().equals(Rank.DEFAULT)) {
            objective.getScore(new MessageBuilder(ChatColor.WHITE).bold().create() + "Rank: " + rank.getColor().create() + rank.getName()).setScore(9);
        } else {
            objective.getScore(new MessageBuilder(ChatColor.WHITE).bold().create() + "Rank: " + rank.getColor().create() + "" + ChatColor.BOLD + rank.getName().toUpperCase()).setScore(9);
        }
        player.setScoreboard(board);
    }

    public void updateScoreboardSouls(Player player, int amount) {
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        Scoreboard board = player.getScoreboard();
        Objective objective = board.getObjective("PlayerData");
        getSouls().put(player.getUniqueId(), gameProfile.getKitPvpData().getSouls());
        // TODO:
        // BattlegroundsCore.getInstance().getGlobalStats().setTotalSoulsEarned(BattlegroundsCore.getInstance().getGlobalStats().getTotalSoulsEarned() + amount);
        board.resetScores(new MessageBuilder(ChatColor.AQUA).bold().create() + "Souls: " + ChatColor.GRAY + souls.get(player.getUniqueId()));
        objective.getScore(new MessageBuilder(ChatColor.AQUA).bold().create() + "Souls: " + ChatColor.GRAY + (gameProfile.getKitPvpData().getSouls() + amount)).setScore(3);
        player.setScoreboard(board);
    }

}
