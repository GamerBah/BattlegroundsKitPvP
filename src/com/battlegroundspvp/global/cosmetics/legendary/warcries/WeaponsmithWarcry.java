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
import org.bukkit.inventory.ItemFlag;

public class WeaponsmithWarcry extends Warcry {

    public WeaponsmithWarcry() {
        super(441, new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Weaponsmith",
                new ItemBuilder(Material.IRON_SWORD)
                        .name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Weaponsmith")
                        .lore(new ColorBuilder(ChatColor.GRAY).italic().create() + "Forged from the death of my enemies!")
                        .flag(ItemFlag.HIDE_ATTRIBUTES), Rarity.LEGENDARY, ServerType.KITPVP);
    }

    @Override
    public void onKill(Player killer, Player player) {
        Bukkit.getServer().getOnlinePlayers().forEach(players -> players.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1F, 1));
    }
}
