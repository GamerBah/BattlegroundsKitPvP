package com.battlegroundspvp.worldpvp.commands;
/* Created by GamerBah on 8/15/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.administration.data.Rank;
import de.Herbystar.TTA.TTA_Methods;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class SpectateCommand implements CommandExecutor {

    @Getter
    private static ArrayList<Player> spectating = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        GameProfile playerData = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());

        if (!playerData.hasRank(Rank.WARRIOR)) {
            BattlegroundsCore.getInstance().sendNoPermission(player);
            return true;
        }
        BattlegroundsCore.getInstance().respawn(player);
        if (!spectating.contains(player)) {
            player.getInventory().clear();
            spectating.add(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true, true));
            player.setAllowFlight(true);
            player.setFlying(true);
            TTA_Methods.sendTitle(player, ChatColor.GREEN + "You are now spectating!", 5, 30, 10, null, 5, 30, 10);
            return true;
        }

        if (spectating.contains(player)) {
            spectating.remove(player);
            TTA_Methods.sendTitle(player, ChatColor.GREEN + "You are no longer spectating!", 5, 30, 10, null, 5, 30, 10);
            return true;
        }

        return false;
    }
}
