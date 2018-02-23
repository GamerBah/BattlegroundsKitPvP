package com.battlegroundspvp.global.cosmetics.rare.gores;
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

public class CloudGore extends Gore {

    public CloudGore() {
        super(200, ChatColor.BLUE + "Cloud",
                new ItemBuilder(Material.INK_SACK)
                        .durability(15)
                        .name(ChatColor.BLUE + "Cloud")
                        .lore(new ColorBuilder(ChatColor.GRAY).italic().create() + "Light, fluffy, and dead!"), Rarity.RARE, ServerType.KITPVP);
    }

    @Override
    public void onKill(Player killer, Player player) {
        Bukkit.getServer().getOnlinePlayers().forEach(players ->
                ParticleEffect.CLOUD.display(0, 0.5F, 0, 0, 5, player.getLocation().add(0, 1, 0), 25));
    }
}
