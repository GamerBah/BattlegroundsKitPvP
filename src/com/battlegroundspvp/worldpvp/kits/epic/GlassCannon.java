package com.battlegroundspvp.worldpvp.kits.epic;
/* Created by GamerBah on 8/27/2016 */

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

public class GlassCannon extends Kit {

    public GlassCannon() {
        super(37, "Glass Cannon", new ItemBuilder(Material.STAINED_GLASS)
                .lore(" ")
                .lore("§a§lKit Contents:")
                .lore("§7   ● Leather Helmet")
                .lore("§7   ● Leather Chestplate (Protection I)")
                .lore("§7   ● Leather Leggings")
                .lore("§7   ● Leather Boots")
                .lore("§7   ● Wooden Sword (Sharpness IV)")
                .lore(" ")
                .lore("§c§lPotion Effects:")
                .lore("§7   ● §aStrength II")
                .flag(ItemFlag.HIDE_ATTRIBUTES), Rarity.EPIC);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0, false, false));

        player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Glass Cannon Helmet").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Glass Cannon Chestplate").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Glass Cannon Leggings").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Glass Cannon Boots").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        ItemStack sword = new ItemStack(new ItemBuilder(Material.WOOD_SWORD).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Glass Cannon Sword").enchantment(Enchantment.DAMAGE_ALL, 4).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        player.getInventory().addItem(sword);
    }
}
