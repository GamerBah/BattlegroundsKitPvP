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

public class Sniper extends Kit {

    public Sniper() {
        super(38, "Sniper", new ItemBuilder(Material.BOW).enchantment(Enchantment.ARROW_KNOCKBACK)
                .lore(" ")
                .lore("§a§lKit Contents:")
                .lore("§7   ● Chain Helmet")
                .lore("§7   ● Chaim Chestplate")
                .lore("§7   ● Chain Leggings")
                .lore("§7   ● Chain Boots")
                .lore("§7   ● Bow (Power II)")
                .lore("§7   ● Stone Sword")
                .flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_ENCHANTS), Rarity.EPIC);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true, false));

        player.getInventory().setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Sniper Helmet").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setChestplate(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Sniper Chainmail").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Sniper Leggings").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Sniper Boots").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        ItemStack bow = new ItemStack(new ItemBuilder(Material.BOW).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Sniper Bow").enchantment(Enchantment.ARROW_DAMAGE, 2).enchantment(Enchantment.ARROW_INFINITE).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        ItemStack sword = new ItemStack(new ItemBuilder(Material.STONE_SWORD).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Sniper Sword").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        player.getInventory().addItem(bow);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.ARROW));
    }
}