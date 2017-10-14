package com.battlegroundspvp.worldpvp.utils;
/* Created by GamerBah on 10/13/2017 */

import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.administration.data.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class ComponentMessages {

    public static BaseComponent[] playerStats(GameProfile gameProfile) {
        KDRatio kdRatio = new KDRatio();
        ChatColor ratioColor = kdRatio.getRatioColor(gameProfile);
        double ratio = ((double) gameProfile.getKitPvpData().getKills() / (double) gameProfile.getKitPvpData().getDeaths());
        ratio = Math.round(ratio * 100.00D) / 100.00D;
        if (gameProfile.getKitPvpData().getDeaths() == 0) {
            ratio = gameProfile.getKitPvpData().getKills();
        }

        return new ComponentBuilder(
                gameProfile.getRank().getColor().create() + "" + (gameProfile.hasRank(Rank.WARRIOR) ? ChatColor.BOLD + gameProfile.getRank().getName().toUpperCase() + " " : "")
                        + (gameProfile.hasRank(Rank.WARRIOR) ? ChatColor.WHITE : ChatColor.GRAY) + gameProfile.getName() + "\n\n"
                        + ChatColor.GRAY + "Kills: " + ChatColor.GREEN + gameProfile.getKitPvpData().getKills() + "\n"
                        + ChatColor.GRAY + "Deaths: " + ChatColor.RED + gameProfile.getKitPvpData().getDeaths() + "\n"
                        + ChatColor.GRAY + "K/D Ratio: " + ratioColor + ratio
                        + "\n\n" + ChatColor.YELLOW + "Click to open player options....").create();
    }

}
