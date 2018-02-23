package com.battlegroundspvp.global.cosmetics.legendary.warcries;
/* Created by GamerBah on 10/30/2017 */

import com.battlegroundspvp.utils.cosmetics.Warcry;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.utils.messages.ColorBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MeowWarcry extends Warcry {

    public MeowWarcry() {
        super(440, new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Meow",
                new ItemBuilder(Material.DIAMOND)
                        .name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Meow")
                        .lore(new ColorBuilder(ChatColor.GRAY).italic().create() + "Meooowwww!"), Rarity.LEGENDARY, ServerType.KITPVP);
    }

    @Override
    public void onKill(Player killer, Player player) {
        Bukkit.getServer().getOnlinePlayers().forEach(players -> players.playSound(player.getLocation(), Sound.ENTITY_CAT_PURREOW, 2F, 1));
    }
}
