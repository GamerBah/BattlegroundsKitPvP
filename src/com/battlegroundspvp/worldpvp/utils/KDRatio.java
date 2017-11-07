package com.battlegroundspvp.worldpvp.utils;
/* Created by GamerBah on 8/15/2016 */

import com.battlegroundspvp.administration.data.GameProfile;
import net.md_5.bungee.api.ChatColor;

public class KDRatio {

    public Double getRatio(GameProfile gameProfile) {
        double ratio = ((double) gameProfile.getKitPvpData().getKills() / (gameProfile.getKitPvpData().getDeaths() > 0 ? (double) gameProfile.getKitPvpData().getDeaths() : 1));
        return Math.round(ratio * 100.00D) / 100.00D;
    }

    public Double getRatio(int kills, int deaths) {
        double ratio = ((double) kills / (deaths > 0 ? (double) deaths : 1));
        return Math.round(ratio * 100.00D) / 100.00D;
    }

    public ChatColor getRatioColor(GameProfile gameProfile) {
        ChatColor ratioColor = ChatColor.GRAY;
        double ratio = ((double) gameProfile.getKitPvpData().getKills() / (double) gameProfile.getKitPvpData().getDeaths());
        ratio = Math.round(ratio * 100.00D) / 100.00D;
        if (gameProfile.getKitPvpData().getDeaths() == 0) {
            ratio = gameProfile.getKitPvpData().getKills();
        }
        if (ratio < 0.25D) {
            ratioColor = ChatColor.DARK_RED;
        } else if (ratio >= 0.25D && ratio < 0.50D) {
            ratioColor = ChatColor.RED;
        } else if (ratio >= 0.50D && ratio < 0.75D) {
            ratioColor = ChatColor.GOLD;
        } else if (ratio >= 0.75D && ratio < 1.00D) {
            ratioColor = ChatColor.YELLOW;
        } else if (ratio >= 1.00D && ratio < 1.50D) {
            ratioColor = ChatColor.GREEN;
        } else if (ratio >= 1.50D && ratio < 2.00D) {
            ratioColor = ChatColor.DARK_GREEN;
        } else if (ratio >= 2.00D && ratio < 3.00D) {
            ratioColor = ChatColor.AQUA;
        } else if (ratio >= 3.00D) {
            ratioColor = ChatColor.LIGHT_PURPLE;
        }
        return ratioColor;
    }
}
