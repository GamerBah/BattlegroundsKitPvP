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

public class MorbidPiggyWarcry extends Warcry {

    public MorbidPiggyWarcry() {
        super(341, new ColorBuilder(ChatColor.GOLD).bold().create() + "Morbid Piggy",
                new ItemBuilder(Material.GRILLED_PORK)
                        .name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Morbid Piggy")
                        .lore(new ColorBuilder(ChatColor.GRAY).italic().create() + "Tonight, we make bacon!"), Rarity.EPIC, ServerType.KITPVP);
    }

    @Override
    public void onKill(Player killer, Player player) {
        Bukkit.getServer().getOnlinePlayers().forEach(players -> players.playSound(player.getLocation(), Sound.ENTITY_PIG_DEATH, 1F, 1));
    }
}