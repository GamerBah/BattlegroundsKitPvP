package com.battlegroundspvp.global.cosmetics.common.gores;
/* Created by GamerBah on 10/30/2017 */

import com.battlegroundspvp.util.cosmetic.Gore;
import com.battlegroundspvp.util.enums.Rarity;
import com.battlegroundspvp.util.message.MessageBuilder;
import com.gamerbah.inventorytoolkit.ItemBuilder;
import de.slikey.effectlib.util.ParticleEffect;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class StoneBreakGore extends Gore {

    public StoneBreakGore() {
        super(100, ChatColor.GRAY + "Stone Break",
                new ItemBuilder(Material.STONE)
                        .name(ChatColor.GRAY + "Stone Break")
                        .lore(new MessageBuilder(ChatColor.GRAY).italic().create() + "A death written in the rocks"), Rarity.COMMON, ServerType.KITPVP);
    }

    @Override
    public void onKill(Player killer, Player player) {
        Bukkit.getServer().getOnlinePlayers().forEach(players ->
                ParticleEffect.BLOCK_CRACK.display(0, 0.5F, 0, 0, 5, player.getLocation().add(0, 1, 0), 25));
    }
}
