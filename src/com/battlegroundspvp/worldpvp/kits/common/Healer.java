package com.battlegroundspvp.worldpvp.kits.common;
/* Created by GamerBah on 2/23/2018 */

import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Healer extends Kit {

    public Healer() {
        super(5, "Healer", new ItemBuilder(Material.BOOK_AND_QUILL)
                .lore(" ")
                .lore("§a§lKit Contents:")
                .lore("§7   ● Leather Helmet")
                .lore("§7   ● Leather Chestplate (Protection I)")
                .lore("§7   ● Iron Leggings")
                .lore("§7   ● Leather Boots")
                .lore("§7   ● §fIron Sword §7(Knockback I)")
                .lore("§7   ● §fHealing Tome §7(Vol. I)")
                .lore(" ")
                .lore("§b§lAbility:")
                .lore("§7Heal yourself for §c§l♥♥"), Rarity.COMMON);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true, false));

        player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).name(Rarity.COMMON.getColor() + "Healer Helmet").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).name(Rarity.COMMON.getColor() + "Healer Chestplate").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).name(Rarity.COMMON.getColor() + "Healer Leggings").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).name(Rarity.COMMON.getColor() + "Healer Boots").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        ItemStack sword = new ItemStack(new ItemBuilder(Material.IRON_SWORD).name(Rarity.COMMON.getColor() + "Healer Sword").enchantment(Enchantment.KNOCKBACK, 1).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        ItemStack tome = new ItemStack(new ItemBuilder(Material.ENCHANTED_BOOK).name(Rarity.COMMON.getColor() + "Healing Tome").flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        player.getInventory().addItem(sword);
        player.getInventory().addItem(tome);
    }

}
