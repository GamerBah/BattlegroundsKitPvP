package com.battlegroundspvp.global.cosmetics.epic.warcries;
/* Created by GamerBah on 10/30/2017 */

import com.battlegroundspvp.util.cosmetic.Warcry;
import com.battlegroundspvp.util.enums.Rarity;
import com.battlegroundspvp.util.message.MessageBuilder;
import com.gamerbah.inventorytoolkit.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ExplosionWarcry extends Warcry {

    public ExplosionWarcry() {
        super(340, new MessageBuilder(ChatColor.GOLD).bold().create() + "Explosion",
                new ItemBuilder(Material.TNT)
                        .name(new MessageBuilder(ChatColor.GOLD).bold().create() + "Explosion")
                        .lore(new MessageBuilder(ChatColor.GRAY).italic().create() + "Kaboom!"), Rarity.EPIC, ServerType.KITPVP);
    }

    @Override
    public void onKill(Player killer, Player player) {
        Bukkit.getServer().getOnlinePlayers().forEach(players -> players.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1F, 1));
    }
}
