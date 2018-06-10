package com.battlegroundspvp.worldpvp.kits.common;
/* Created by GamerBah on 8/19/2016 */

import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.util.enums.Rarity;
import com.gamerbah.inventorytoolkit.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Bowman extends Kit {

    public Bowman() {
        super(2, "Bowman", new ItemBuilder(Material.BOW)
                .lore(" ")
                .lore("§a§lKit Contents:")
                .lore("§7   ● Leather Helmet")
                .lore("§7   ● Leather Chestplate (Protection I)")
                .lore("§7   ● Leather Leggings (Protection I)")
                .lore("§7   ● Leather Boots")
                .lore("§7   ● Bow (Power I)")
                .lore("§7   ● Stone Sword"), Rarity.COMMON);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true, false));

        player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).name(Rarity.COMMON.getColor() + "Bowman Helmet").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).name(Rarity.COMMON.getColor() + "Bowman Chestplate").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).name(Rarity.COMMON.getColor() + "Bowman Leggings").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).name(Rarity.COMMON.getColor() + "Bowman Boots").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        ItemStack sword = new ItemStack(new ItemBuilder(Material.STONE_SWORD).name(Rarity.COMMON.getColor() + "Bowman Sword").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        ItemStack bow = new ItemStack(new ItemBuilder(Material.BOW).name(Rarity.COMMON.getColor() + "Trusty Bow").enchantment(Enchantment.ARROW_INFINITE).enchantment(Enchantment.ARROW_DAMAGE, 1).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        player.getInventory().addItem(bow);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.ARROW));
    }
}
