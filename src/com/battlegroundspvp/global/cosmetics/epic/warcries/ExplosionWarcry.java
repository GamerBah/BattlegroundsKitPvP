package com.battlegroundspvp.global.cosmetics.epic.warcries;
/* Created by GamerBah on 10/30/2017 */

import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.cosmetics.Warcry;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ExplosionWarcry extends Warcry {

    public ExplosionWarcry() {
        super(340, new ColorBuilder(ChatColor.GOLD).bold().create() + "Explosion",
                new ItemBuilder(Material.TNT)
                        .name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Explosion")
                        .lore(new ColorBuilder(ChatColor.GRAY).italic().create() + "Kaboom!"), Rarity.EPIC, ServerType.KITPVP);
    }

    @Override
    public void onKill(Player killer, Player player) {
        Bukkit.getServer().getOnlinePlayers().forEach(players -> players.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1F, 1));
    }
}