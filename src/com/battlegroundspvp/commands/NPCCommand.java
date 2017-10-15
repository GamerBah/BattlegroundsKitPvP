package com.battlegroundspvp.commands;
/* Created by GamerBah on 10/14/2017 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.administration.data.Rank;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.worldpvp.utils.npcs.QuartermasterTrait;
import lombok.Getter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NPCCommand implements CommandExecutor {

    @Getter
    private static List<Player> removal = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());

        if (!gameProfile.hasRank(Rank.OWNER)) {
            BattlegroundsCore.getInstance().sendNoPermission(player);
            return true;
        }

        if (args.length != 1) {
            BattlegroundsCore.getInstance().sendIncorrectUsage(player, "/npc <add/remove>");
            return true;
        }

        if (args[0].equalsIgnoreCase("add")) {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "");
            npc.addTrait(QuartermasterTrait.class);
            npc.setProtected(true);
            npc.faceLocation(player.getLocation());
            npc.spawn(player.getLocation());

        } else if (args[0].equalsIgnoreCase("remove")) {
            removal.add(player);
            player.sendMessage(ChatColor.YELLOW + "Click on the NPC you wish to remove.\nUse " + ChatColor.RED + "/npc cancel" + ChatColor.YELLOW + " to cancel.");
            EventSound.playSound(player, EventSound.CLICK);
        } else if (args[0].equalsIgnoreCase("cancel")) {
            if (removal.contains(player)) {
                removal.remove(player);
                player.sendMessage(ChatColor.RED + "Operation cancelled.");
                EventSound.playSound(player, EventSound.ACTION_FAIL);
            }
        } else {
            BattlegroundsCore.getInstance().sendIncorrectUsage(player, "/npc <add/remove>");
            return true;
        }

        return false;
    }
}
