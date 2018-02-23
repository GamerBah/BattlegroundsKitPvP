package com.battlegroundspvp.global.cosmetics.epic.trails;
/* Created by GamerBah on 10/21/2017 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.global.utils.kits.KitAbility;
import com.battlegroundspvp.runnables.TrailRunnable;
import com.battlegroundspvp.utils.cosmetics.ParticlePack;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.utils.messages.ColorBuilder;
import de.slikey.effectlib.util.ParticleEffect;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class LavaRainTrail extends ParticlePack {

    public LavaRainTrail() {
        super(320, new ColorBuilder(ChatColor.GOLD).bold().create() + "Lava Rain",
                new ItemBuilder(Material.LAVA_BUCKET)
                        .name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Lava Rain")
                        .lore(ChatColor.YELLOW + "Idle Effect: " + ChatColor.GRAY + "A cloud of smoke raining lava")
                        .lore(ChatColor.YELLOW + "Moving Effect: " + ChatColor.GRAY + "Lava splashing and dripping"),
                Rarity.EPIC, ServerType.KITPVP, 2L);
    }

    @Override
    public void onMove(Player player) {
        if (KitAbility.getPlayerStatus().containsKey(player))
            if (!KitAbility.getPlayerStatus().get(player).abilityIsOver())
                return;
        ParticleEffect.DRIP_LAVA.display(0.2F, 0, 0.2F, 0, 3, player.getLocation().add(0, 0.1D, 0), 25);
        ParticleEffect.LAVA.display(0.1F, 0, 0.1F, 0, 1, player.getLocation().add(0, 0.1D, 0), 25);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            if (!BattlegroundsCore.getAfk().contains(player.getUniqueId()) && TrailRunnable.getStill().contains(player))
                if (BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId()).getKitPvpData().getActiveTrail() == this.getId()) {
                    ParticleEffect.SMOKE_LARGE.display(0.25F, 0.1F, 0.25F, 0, 8, player.getLocation().add(0, 2.5D, 0), 25);
                    ParticleEffect.DRIP_LAVA.display(0.1F, 0, 0.1F, 0, 4, player.getLocation().add(0, 2.5D, 0), 25);
                }
    }
}
