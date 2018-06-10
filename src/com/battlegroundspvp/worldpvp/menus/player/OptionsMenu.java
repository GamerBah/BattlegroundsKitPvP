package com.battlegroundspvp.worldpvp.menus.player;
/* Created by GamerBah on 8/15/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.util.message.MessageBuilder;
import com.gamerbah.inventorytoolkit.GameInventory;
import com.gamerbah.inventorytoolkit.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class OptionsMenu extends GameInventory {

    public OptionsMenu(Player player, Player target) {
        super("Options for " + target.getName(), null);
        GameProfile playerData = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        GameProfile targetData = BattlegroundsCore.getInstance().getGameProfile(target.getUniqueId());
        addButton(11, new ItemBuilder(Material.BARRIER)
                .name(new MessageBuilder(ChatColor.RED).bold().create() + "Report Player"));
        addButton(13, new ItemBuilder(Material.FEATHER)
                .name(new MessageBuilder(ChatColor.GREEN).bold().create() + "Send a Private Message")
                .lore(ChatColor.GRAY + "Use " + ChatColor.RED + "/msg " + target.getName() + " <message> " + ChatColor.GRAY + "to send your message!"));
        addButton(15, new ItemBuilder(Material.DIAMOND_SWORD).flag(ItemFlag.HIDE_ATTRIBUTES).enchantment(Enchantment.DIG_SPEED, 2)
                .name(new MessageBuilder(ChatColor.AQUA).bold().create() + "Send a Team Request"));
    }

}
