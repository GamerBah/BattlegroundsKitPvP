package com.battlegroundspvp.global.cosmetics.rare.warcries;
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

public class HappyPiggyWarcry extends Warcry {

    public HappyPiggyWarcry() {
        super(241, ChatColor.BLUE + "Happy Piggy",
                new ItemBuilder(Material.PORK)
                        .name(ChatColor.BLUE + "Happy Piggy")
                        .lore(new ColorBuilder(ChatColor.GRAY).italic().create() + "Who could hurt such a cute thing?"), Rarity.RARE, ServerType.KITPVP);
    }

    @Override
    public void onKill(Player killer, Player player) {
        Bukkit.getServer().getOnlinePlayers().forEach(players -> players.playSound(player.getLocation(), Sound.ENTITY_PIG_AMBIENT, 1.5F, 1));
    }
}
