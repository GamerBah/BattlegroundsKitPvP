package com.battlegroundspvp.global.cosmetics.common.warcries;
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

public class MoooWarcry extends Warcry {

    public MoooWarcry() {
        super(141, ChatColor.GRAY + "Mooo",
                new ItemBuilder(Material.MILK_BUCKET)
                        .name(ChatColor.GRAY + "Mooo")
                        .lore(new MessageBuilder(ChatColor.GRAY).italic().create() + "*moooooo*"), Rarity.COMMON, ServerType.KITPVP);
    }

    @Override
    public void onKill(Player killer, Player player) {
        Bukkit.getServer().getOnlinePlayers().forEach(players -> players.playSound(player.getLocation(), Sound.ENTITY_COW_AMBIENT, 1F, 1));
    }
}
