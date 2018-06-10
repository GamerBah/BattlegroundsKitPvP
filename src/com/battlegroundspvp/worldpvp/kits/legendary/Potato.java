package com.battlegroundspvp.worldpvp.kits.legendary;
/* Created by GamerBah on 9/7/2016 */

import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.util.enums.Rarity;
import com.battlegroundspvp.util.message.MessageBuilder;
import com.gamerbah.inventorytoolkit.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Potato extends Kit {

    public Potato() {
        super(47, "Potato", new ItemBuilder(Material.POISONOUS_POTATO)
                .lore(" ")
                .lore("§a§lKit Contents:")
                .lore("§7   ● §3Potato Helmet §7(Protection V)")
                .lore("§7   ● §3Potato §7(Sharpness VI)")
                .lore("§7   ● §3Poisonous Potato §7(Poison III)")
                .lore(" ")
                .lore("§c§lPotion Effects:")
                .lore("§7   ● §aRegeneration 3")
                .flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_ENCHANTS), Rarity.LEGENDARY);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2, true, false));

        player.getInventory().setHelmet(new ItemBuilder(Material.POTATO_ITEM).name(new MessageBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Potato Helmet").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6));

        ItemStack potato = new ItemStack(new ItemBuilder(Material.POTATO_ITEM).enchantment(Enchantment.DAMAGE_ALL, 5)
                .name(new MessageBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Potato"));
        ItemStack poisonPotato = new ItemStack(new ItemBuilder(Material.POISONOUS_POTATO).enchantment(Enchantment.ARROW_INFINITE).flag(ItemFlag.HIDE_ENCHANTS)
                .name(new MessageBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Poisonous Potato")
                .lore(ChatColor.GRAY + "Poison III").lore(" ")
                .lore(ChatColor.GRAY + "Inflicts those you").lore(ChatColor.GRAY + "hit with Poison III"));

        player.getInventory().addItem(potato);
        player.getInventory().addItem(poisonPotato);
    }
}
