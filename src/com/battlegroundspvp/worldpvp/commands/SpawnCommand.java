package com.battlegroundspvp.worldpvp.commands;
/* Created by GamerBah on 8/15/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import com.battlegroundspvp.worldpvp.playerevents.PlayerMove;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private BattlegroundsKitPvP plugin;

    public SpawnCommand(BattlegroundsKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (KitManager.isPlayerInKit(player)) {
            KitManager.getPreviousKit().put(player.getUniqueId(), KitManager.getPlayersInKits().get(player.getUniqueId()));
        }
        if (PlayerMove.getLaunched().contains(player)) {
            PlayerMove.getLaunched().remove(player);
        }
        if (BattlegroundsKitPvP.getAfk().contains(player.getUniqueId())) {
            BattlegroundsKitPvP.getAfk().remove(player.getUniqueId());
            player.sendMessage(ChatColor.GRAY + "You are no longer AFK");
            EventSound.playSound(player, EventSound.CLICK);
            BattlegroundsCore.clearTitle(player);
        }

        BattlegroundsCore.getInstance().respawn(player);
        EventSound.playSound(player, EventSound.COMMAND_NEEDS_CONFIRMATION);

        return true;
    }
}
