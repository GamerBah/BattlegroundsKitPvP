package com.battlegroundspvp.global.cosmetics.common.trails;
/* Created by GamerBah on 10/27/2017 */

import com.battlegroundspvp.global.utils.kits.KitAbility;
import com.battlegroundspvp.util.cosmetic.ParticlePack;
import com.battlegroundspvp.util.enums.Rarity;
import com.gamerbah.inventorytoolkit.ItemBuilder;
import de.slikey.effectlib.util.ParticleEffect;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BubblyTrail extends ParticlePack {

    public BubblyTrail() {
        super(120, ChatColor.GRAY + "Bubbly",
                new ItemBuilder(Material.POTION)
                        .data(0)
                        .name(ChatColor.GRAY + "Bubbly")
                        .lore(ChatColor.YELLOW + "Moving Effect: " + ChatColor.GRAY + "Bubbles!"), Rarity.COMMON, ServerType.KITPVP);
    }

    @Override
    public void onMove(Player player) {
        if (KitAbility.getPlayerStatus().containsKey(player))
            if (!KitAbility.getPlayerStatus().get(player).abilityIsOver())
                return;
        ParticleEffect.WATER_SPLASH.display(0.1F, 0.5F, 0.1F, 0, 10, player.getLocation(), 25);
    }

    @Override
    public void run() {
    }

}
