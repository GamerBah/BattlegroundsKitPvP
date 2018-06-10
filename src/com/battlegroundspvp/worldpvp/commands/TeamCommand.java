package com.battlegroundspvp.worldpvp.commands;
/* Created by GamerBah on 8/7/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.util.enums.EventSound;
import com.battlegroundspvp.worldpvp.utils.teams.TeamMessages;
import com.battlegroundspvp.worldpvp.utils.teams.TeamUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommand implements CommandExecutor {

    private BattlegroundsKitPvP plugin;

    public TeamCommand(BattlegroundsKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        TeamUtils teamUtils = new TeamUtils(plugin);
        TeamMessages teamMessages = new TeamMessages();
        if (args.length > 1 || args.length == 0) {
            EventSound.playSound(player, EventSound.ACTION_FAIL);
            BattlegroundsCore.getInstance().sendIncorrectUsage(player, "/team <[player]/accept/deny/leave>");
        } else {
            if (args[0].equalsIgnoreCase("accept")) {
                if (plugin.getServer().getOnlinePlayers().size() >= 1) {
                    if (teamUtils.hasPendingRequest(player)) {
                        Player requester = teamUtils.getRequester(player);
                        teamMessages.sendAcceptMessage(player, requester);
                        TeamUtils.createTeam(player, requester);
                    } else {
                        EventSound.playSound(player, EventSound.ACTION_FAIL);
                        player.sendMessage(ChatColor.RED + "You do not have a pending team request!");
                        return false;
                    }
                } else {
                    EventSound.playSound(player, EventSound.ACTION_FAIL);
                    player.sendMessage(ChatColor.RED + "Since there are less than 10 players online, you can't form a team!");
                }
            } else if (args[0].equalsIgnoreCase("deny")) {
                if (plugin.getServer().getOnlinePlayers().size() >= 1) {
                    if (teamUtils.hasPendingRequest(player)) {
                        Player requester = teamUtils.getRequester(player);
                        teamMessages.sendDeclineMessage(player, requester);
                        TeamUtils.removePendingRequest(player);
                    } else {
                        EventSound.playSound(player, EventSound.ACTION_FAIL);
                        player.sendMessage(ChatColor.RED + "You do not have a pending team request!");
                        return false;
                    }
                } else {
                    EventSound.playSound(player, EventSound.ACTION_FAIL);
                    player.sendMessage(ChatColor.RED + "Since there are less than 10 players online, you can't form a team!");
                }
            } else if (args[0].equalsIgnoreCase("leave")) {
                if (!teamUtils.isTeaming(player)) {
                    EventSound.playSound(player, EventSound.ACTION_FAIL);
                    player.sendMessage(ChatColor.RED + "You are not on a team!");
                } else {
                    if (BattlegroundsKitPvP.currentTeams.containsKey(player.getName()) || BattlegroundsKitPvP.currentTeams.containsValue(player.getName())) {
                        Player teammate = null;
                        for (String possibleTeammate : BattlegroundsKitPvP.currentTeams.keySet()) {
                            if (BattlegroundsKitPvP.currentTeams.get(possibleTeammate).equals(player.getName())) {
                                teammate = plugin.getServer().getPlayer(possibleTeammate);
                            }
                        }
                        if (teammate == null) {
                            teammate = plugin.getServer().getPlayer(BattlegroundsKitPvP.currentTeams.get(player.getName()));
                        }
                        if (teammate != null) {
                            EventSound.playSound(player, EventSound.TEAM_DISBAND);
                            teammate.sendMessage(ChatColor.RED + player.getName() + "has left the team. Therefore, the team has been disbanded!");
                            TeamUtils.removeTeam(player, teammate);
                        }
                        EventSound.playSound(player, EventSound.TEAM_DISBAND);
                        player.sendMessage(ChatColor.RED + "You have left the team. Therefore, the team has been disbanded!");
                        TeamUtils.removeTeam(player, teammate);
                    }
                }
            } else {
                if (plugin.getServer().getOnlinePlayers().size() >= 1) {
                    Player target = Bukkit.getServer().getPlayerExact(args[0]);
                    if (!Bukkit.getServer().getOnlinePlayers().contains(target)) {
                        EventSound.playSound(player, EventSound.ACTION_FAIL);
                        player.sendMessage(ChatColor.GOLD + " \"" + ChatColor.YELLOW + args[0] + ChatColor.GOLD + "\"" + ChatColor.RED + "is not online or doesn't exist!");
                        return false;
                    } else if (target.getUniqueId().equals(player.getUniqueId())) {
                        EventSound.playSound(player, EventSound.ACTION_FAIL);
                        player.sendMessage(ChatColor.RED + "Team with yourself? How would that even work...");
                    } else {
                        if (teamUtils.hasPendingRequest(target)) {
                            EventSound.playSound(player, EventSound.ACTION_FAIL);
                            player.sendMessage(ChatColor.RED + "That player already has a pending request to team!");
                            return false;
                        } else if (teamUtils.hasPendingRequest(player)) {
                            EventSound.playSound(player, EventSound.ACTION_FAIL);
                            player.sendMessage(ChatColor.RED + "You already have a pending request to team with another player!");
                            return false;
                        } else if (teamUtils.isTeaming(target)) {
                            EventSound.playSound(player, EventSound.ACTION_FAIL);
                            player.sendMessage(ChatColor.RED + "That player is already on a team!");
                            return false;
                        } else if (!BattlegroundsCore.getInstance().getGameProfile(target.getUniqueId()).getPlayerSettings().isTeamRequests()) {
                            EventSound.playSound(player, EventSound.ACTION_FAIL);
                            player.sendMessage(ChatColor.RED + "That player has chosen to not receive team requests!");
                            return false;
                        } else {
                            teamMessages.sendRequestMessage(player, target);
                            TeamUtils.createPendingRequest(target, player);
                            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                                if (teamUtils.hasPendingRequest(target)) {
                                    player.sendMessage(ChatColor.RED + "Your request to team with "
                                            + ChatColor.GOLD + target.getName() + ChatColor.RED + " has expired!");
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 2, 0.5F);

                                    target.sendMessage(ChatColor.GOLD + player.getName() + "'s" + ChatColor.RED + " request to team with you has expired!");
                                    target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BASS, 2, 0.5F);

                                    TeamUtils.removePendingRequest(target);
                                }
                            }, 600L);
                        }
                    }
                } else {
                    EventSound.playSound(player, EventSound.ACTION_FAIL);
                    player.sendMessage(ChatColor.RED + "Since there are less than 10 players online, you can't form a team!");
                }
            }
        }
        return false;
    }

}
