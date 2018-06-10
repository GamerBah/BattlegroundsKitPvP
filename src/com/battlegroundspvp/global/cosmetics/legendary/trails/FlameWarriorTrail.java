package com.battlegroundspvp.global.cosmetics.legendary.trails;
/* Created by GamerBah on 10/21/2017 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.global.listeners.CombatListener;
import com.battlegroundspvp.global.utils.kits.KitAbility;
import com.battlegroundspvp.runnable.game.TrailRunnable;
import com.battlegroundspvp.util.cosmetic.ParticlePack;
import com.battlegroundspvp.util.enums.Rarity;
import com.battlegroundspvp.util.message.MessageBuilder;
import com.gamerbah.inventorytoolkit.ItemBuilder;
import de.slikey.effectlib.util.ParticleEffect;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FlameWarriorTrail extends ParticlePack {

    private double phi = 0;

    public FlameWarriorTrail() {
        super(420, new MessageBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Flame Warrior",
                new ItemBuilder(Material.FLINT_AND_STEEL)
                        .name(new MessageBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Flame Warrior")
                        .lore(ChatColor.YELLOW + "Idle Effect: " + ChatColor.GRAY + "A flame helix rotating around you")
                        .lore(ChatColor.YELLOW + "Moving Effect: " + ChatColor.GRAY + "Flames and smoke"),
                Rarity.LEGENDARY, ServerType.KITPVP, 3L);
    }

    @Override
    public void onMove(Player player) {
        if (KitAbility.getPlayerStatus().containsKey(player))
            if (!KitAbility.getPlayerStatus().get(player).abilityIsOver())
                return;
        ParticleEffect.FLAME.display(0, 0, 0, 0, 1, player.getLocation().add(0, 0.1, 0), 25);
        ParticleEffect.FLAME.display(0, 0, 0, 0, 1, player.getLocation().add(0, 0.2, 0), 25);
        ParticleEffect.FLAME.display(0, 0, 0, 0, 1, player.getLocation().add(0, 0.3, 0), 25);
        ParticleEffect.SMOKE_LARGE.display(0.1F, 0.5F, 0.1F, 0, 5, player.getLocation(), 25);
    }

    @Override
    public void run() {
        phi += Math.PI / 24;
        for (double t = 0; t <= 2 * Math.PI; t = t + Math.PI / 12) {
            for (double i = 0; i <= 2; i = i + 1) {
                // w1 * (h * pi - t) * w2 * cos(...)
                double x = 0.5 * (2 * Math.PI - t) * 0.375 * Math.cos(t + phi + i * Math.PI);
                double y = 0.425 * t;
                double z = 0.5 * (2 * Math.PI - t) * 0.375 * Math.sin(t + phi + i * Math.PI);
                for (Player player : BattlegroundsKitPvP.getInstance().getServer().getOnlinePlayers()) {
                    if (!BattlegroundsCore.getAfk().contains(player.getUniqueId()) && TrailRunnable.getStill().contains(player))
                        if (!CombatListener.getTagged().containsKey(player.getUniqueId()))
                            if (BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId()).getKitPvpData().getActiveTrail() == this.getId()) {
                                Location location = player.getLocation();
                                location.add(x, y, z);
                                ParticleEffect.FLAME.display(0, 0, 0, 0, 1, location, 25);
                                location.subtract(x, y, z);
                            }
                }
            }
        }
    }
}
