package com.battlegroundspvp.worldpvp.playerevents;
/* Created by GamerBah on 10/16/2017 */

import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.global.utils.kits.KitAbility;
import com.battlegroundspvp.util.enums.Rarity;
import com.battlegroundspvp.util.message.MessageBuilder;
import com.battlegroundspvp.worldpvp.WorldPvP;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import com.gamerbah.inventorytoolkit.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn {

    public static void respawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);
        player.setFlySpeed(0.1F);
        player.setExp(0);
        player.setLevel(0);
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(20);

        if (!WorldPvP.getNoFall().contains(player))
            WorldPvP.getNoFall().add(player);

        KitAbility.getPlayerStatus().remove(player);

        player.getInventory().setItem(0, new ItemBuilder(Material.NETHER_STAR)
                .name(new MessageBuilder(ChatColor.AQUA).bold().create() + "Kit Selector" + ChatColor.GRAY + " (Right-Click)")
                .lore(ChatColor.GRAY + "Choose which kit you'll use!"));

        if (KitManager.getPreviousKit().containsKey(player.getUniqueId())) {
            Kit kit = KitManager.getPreviousKit().get(player.getUniqueId());
            player.getInventory().setItem(1, new ItemBuilder(Material.BOOK)
                    .name(new MessageBuilder(ChatColor.GREEN).bold().create() + "Previous Kit: " + kit.getRarity().getColor() + (kit.getRarity() == Rarity.EPIC || kit.getRarity() == Rarity.LEGENDARY ?
                            "" + ChatColor.BOLD : "") + kit.getName() + ChatColor.GRAY + " (Right-Click)")
                    .lore(ChatColor.GRAY + "Equips your previous kit"));
        }
    }

}
