package com.battlegroundspvp.worldpvp.kits.legendary;
/* Created by GamerBah on 8/19/2016 */

import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.utils.messages.ColorBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UltraTank extends Kit {

    public UltraTank() {
        super(46, "Ultra Tank", new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
                .lore(" ")
                .lore("§a§lKit Contents:")
                .lore("§7   ● §bDiamond Helmet §7(Protection I)")
                .lore("§7   ● §bDiamond Chestplate")
                .lore("§7   ● §bDiamond Leggings")
                .lore("§7   ● §bDiamond Boots §7(Protection I)")
                .lore("§7   ● Wooden Sword (Sharpness II)")
                .lore(" ")
                .lore("§c§lPotion Effects:")
                .lore("§7   ● §cSlowness II")
                .flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_ENCHANTS), Rarity.LEGENDARY);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1, false, false));

        player.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Ultra Tank Chestplate").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Ultra Tank Chestplate").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Ultra Tank Leggings").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Ultra Tank Boots").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        ItemStack sword = new ItemStack(new ItemBuilder(Material.WOOD_SWORD).name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Ultra Tank Sword").enchantment(Enchantment.DAMAGE_ALL, 2).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        player.getInventory().addItem(sword);
    }
}
