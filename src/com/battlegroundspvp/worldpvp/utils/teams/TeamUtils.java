package com.battlegroundspvp.worldpvp.utils.teams;

import com.battlegroundspvp.BattlegroundsKitPvP;
import org.bukkit.entity.Player;

public class TeamUtils {

    private BattlegroundsKitPvP plugin;

    public TeamUtils(BattlegroundsKitPvP plugin) {
        this.plugin = plugin;
    }

    public static void createPendingRequest(Player target, Player sender) {
        BattlegroundsKitPvP.pendingTeams.put(target.getName(), sender.getName());
    }

    public static void createTeam(Player target, Player sender) {
        BattlegroundsKitPvP.currentTeams.put(target.getName(), sender.getName());
        removePendingRequest(target);
        // TODO: Set Glowing through ProtocolLib
        //GlowAPI.setGlowing(sender, GlowAPI.Color.WHITE, target);
        //GlowAPI.setGlowing(target, GlowAPI.Color.WHITE, sender);
    }

    public static void removePendingRequest(Player target) {
        if (!BattlegroundsKitPvP.pendingTeams.isEmpty() || BattlegroundsKitPvP.pendingTeams.containsKey(target.getName())) {
            BattlegroundsKitPvP.pendingTeams.remove(target.getName());
        }
    }

    public static void removeTeam(Player player, Player target) {
        if (!BattlegroundsKitPvP.currentTeams.isEmpty()) {
            if (BattlegroundsKitPvP.currentTeams.containsKey(target.getName()) || BattlegroundsKitPvP.currentTeams.containsKey(player.getName())) {
                if (BattlegroundsKitPvP.currentTeams.get(target.getName()).equals(player.getName())) {
                    BattlegroundsKitPvP.currentTeams.remove(target.getName());
                    // TODO: Remove Glowing through ProtocolLib
                    //GlowAPI.setGlowing(player, null, target);
                    //GlowAPI.setGlowing(target, null, player);
                } else {
                    if (BattlegroundsKitPvP.currentTeams.containsKey(player.getName())) {
                        if (BattlegroundsKitPvP.currentTeams.get(player.getName()).equals(target.getName())) {
                            BattlegroundsKitPvP.currentTeams.remove(player.getName());
                            // TODO: Remove Glowing through ProtocolLib
                            //GlowAPI.setGlowing(player, null, target);
                            //GlowAPI.setGlowing(target, null, player);
                        }
                        // TODO: Remove Glowing through ProtocolLib
                        //GlowAPI.setGlowing(player, null, target);
                        //GlowAPI.setGlowing(target, null, player);
                    }
                }
            }
        }
    }

    public Player getRequester(Player target) {
        return plugin.getServer().getPlayer(BattlegroundsKitPvP.pendingTeams.get(target.getName()));
    }

    public boolean hasPendingRequest(Player target) {
        return BattlegroundsKitPvP.pendingTeams.containsKey(target.getName());
    }

    public boolean isTeaming(Player target) {
        for (Player sender : plugin.getServer().getOnlinePlayers()) {
            if (BattlegroundsKitPvP.currentTeams.containsKey(target.getName()) || BattlegroundsKitPvP.currentTeams.containsValue(sender.getName())) {
                return true;
            }
        }
        return false;
    }

}
