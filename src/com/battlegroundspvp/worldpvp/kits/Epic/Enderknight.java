package com.battlegroundspvp.worldpvp.kits.Epic;
/* Created by GamerBah on 8/27/2016 */

import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Enderknight extends Kit {

    public Enderknight() {
        super(39, "Enderknight", new ItemBuilder(Material.ENDER_PEARL)
                .lore(" ")
                .lore("§a§lKit Contents:")
                .lore("§7   ● Chain Helmet")
                .lore("§7   ● Chain Chestplate")
                .lore("§7   ● Chain Leggings")
                .lore("§7   ● Chain Boots")
                .lore("§7   ● §fIron Sword §7(Knockback I)")
                .lore("§7   ● §5Enderpearl §7x5")
                .flag(ItemFlag.HIDE_ATTRIBUTES), Rarity.EPIC);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true, false));

        player.getInventory().setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Enderknight Helmet").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setChestplate(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Enderknight Chestplate").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Enderknight Leggings").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Enderknight Boots").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        ItemStack sword = new ItemStack(new ItemBuilder(Material.IRON_SWORD).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Enderknight Sword").enchantment(Enchantment.KNOCKBACK, 1).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemBuilder(Material.ENDER_PEARL).name(new ColorBuilder(ChatColor.GOLD).bold().create() + "Enderpearl").amount(5));
    }
}
