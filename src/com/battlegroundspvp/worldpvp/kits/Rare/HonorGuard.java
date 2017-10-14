package com.battlegroundspvp.worldpvp.kits.Rare;
/* Created by GamerBah on 8/27/2016 */

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

public class HonorGuard extends Kit {

    public HonorGuard() {
        super(20, "Honor Guard", new ItemBuilder(Material.SHIELD)
                .lore(" ")
                .lore("§a§lKit Contents:")
                .lore("§7   ● §fIron Helmet")
                .lore("§7   ● §fIron Chestplate")
                .lore("§7   ● Chain Leggings")
                .lore("§7   ● §fIron Boots")
                .lore("§7   ● §fIron Sword §7(Sharpness III)")
                .flag(ItemFlag.HIDE_ATTRIBUTES), Rarity.RARE);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true, false));

        player.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).name(Rarity.RARE.getColor() + "Honor Guard Helmet").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).name(Rarity.RARE.getColor() + "Honor Guard Chestplate").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name(Rarity.RARE.getColor() + "Honor Guard Leggings").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).name(Rarity.RARE.getColor() + "Honor Guard Boots").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        ItemStack sword = new ItemStack(new ItemBuilder(Material.IRON_SWORD).name(Rarity.RARE.getColor() + "Honor Guard Sword").enchantment(Enchantment.DAMAGE_ALL, 3).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        player.getInventory().addItem(sword);
    }
}
