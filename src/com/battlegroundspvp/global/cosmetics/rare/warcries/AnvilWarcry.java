package com.battlegroundspvp.global.cosmetics.rare.warcries;
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

public class AnvilWarcry extends Warcry {

    public AnvilWarcry() {
        super(240, ChatColor.BLUE + "Anvil",
                new ItemBuilder(Material.ANVIL)
                        .name(ChatColor.BLUE + "Anvil")
                        .lore(new MessageBuilder(ChatColor.GRAY).italic().create() + "Weapon upgrading, anyone?"), Rarity.RARE, ServerType.KITPVP);
    }

    @Override
    public void onKill(Player killer, Player player) {
        Bukkit.getServer().getOnlinePlayers().forEach(players -> players.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1F, 1));
    }
}
