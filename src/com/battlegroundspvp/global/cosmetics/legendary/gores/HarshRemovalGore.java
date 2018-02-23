package com.battlegroundspvp.global.cosmetics.legendary.gores;
/* Created by GamerBah on 10/30/2017 */

import com.battlegroundspvp.utils.cosmetics.Gore;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.utils.messages.ColorBuilder;
import de.slikey.effectlib.util.ParticleEffect;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HarshRemovalGore extends Gore {

    public HarshRemovalGore() {
        super(400, new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Harsh Removal",
                new ItemBuilder(Material.BARRIER)
                        .name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Harsh Removal")
                        .lore(new ColorBuilder(ChatColor.GRAY).italic().create() + "Putting a literal stop to life"), Rarity.LEGENDARY, ServerType.KITPVP);
    }

    @Override
    public void onKill(Player killer, Player player) {
        Bukkit.getServer().getOnlinePlayers().forEach(players -> {
            ParticleEffect.BARRIER.display(0, 0, 0, 0, 1, player.getLocation().add(0, 0.5, 0), 25);
            ParticleEffect.BARRIER.display(0, 0, 0, 0, 1, player.getLocation().add(0, 1.5, 0), 25);
        });
    }
}
