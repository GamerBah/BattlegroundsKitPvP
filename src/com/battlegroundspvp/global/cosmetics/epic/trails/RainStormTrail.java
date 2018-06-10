package com.battlegroundspvp.global.cosmetics.epic.trails;
/* Created by GamerBah on 10/30/2017 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.global.utils.kits.KitAbility;
import com.battlegroundspvp.runnable.game.TrailRunnable;
import com.battlegroundspvp.util.cosmetic.ParticlePack;
import com.battlegroundspvp.util.enums.Rarity;
import com.battlegroundspvp.util.message.MessageBuilder;
import com.gamerbah.inventorytoolkit.ItemBuilder;
import de.slikey.effectlib.util.ParticleEffect;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RainStormTrail extends ParticlePack {

    public RainStormTrail() {
        super(321, new MessageBuilder(ChatColor.GOLD).bold().create() + "Rain Storm",
                new ItemBuilder(Material.WATER_BUCKET)
                        .name(new MessageBuilder(ChatColor.GOLD).bold().create() + "Rain Storm")
                        .lore(ChatColor.YELLOW + "Idle Effect: " + ChatColor.GRAY + "Clouds and dripping water")
                        .lore(ChatColor.YELLOW + "Moving Effect: " + ChatColor.GRAY + "Water splashing and dripping"),
                Rarity.EPIC, ServerType.KITPVP, 2L);
    }

    @Override
    public void onMove(Player player) {
        if (KitAbility.getPlayerStatus().containsKey(player))
            if (!KitAbility.getPlayerStatus().get(player).abilityIsOver())
                return;
        ParticleEffect.DRIP_WATER.display(0.2F, 0, 0.2F, 0, 3, player.getLocation().add(0, 0.1D, 0), 25);
        ParticleEffect.WATER_SPLASH.display(0.1F, 0, 0.1F, 0, 10, player.getLocation().add(0, 0.2D, 0), 25);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            if (!BattlegroundsCore.getAfk().contains(player.getUniqueId()) && TrailRunnable.getStill().contains(player))
                if (BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId()).getKitPvpData().getActiveTrail() == this.getId()) {
                    ParticleEffect.CLOUD.display(0.25F, 0.1F, 0.25F, 0, 6, player.getLocation().add(0, 2.5D, 0), 25);
                    ParticleEffect.DRIP_WATER.display(0.15F, 0F, 0.15F, 0, 2, player.getLocation().add(0, 2.5D, 0), 25);
                }
    }
}
