package com.battlegroundspvp.worldpvp.kits.Legendary;
/* Created by GamerBah on 9/13/2016 */

import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.global.utils.kits.KitAbility;
import com.battlegroundspvp.runnables.UpdateRunnable;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.utils.packets.particles.ParticleEffect;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Ninja extends Kit {
    private KitAbility kitAbility = new KitAbility(1, 25.0);

    public Ninja() {
        super(48, "Ninja", new ItemBuilder(Material.COAL)
                .lore(" ")
                .lore("§a§lKit Contents:")
                .lore("§7   ● Chain Helmet (Protection II)")
                .lore("§7   ● Leather Chestplate")
                .lore("§7   ● Chain Leggings (Thorns I)")
                .lore("§7   ● Leather Boots")
                .lore("§7   ● §fIron Sword")
                .lore(" ")
                .lore("§b§lAbility:")
                .lore("§7Become invisible and gain a speed boost")
                .flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_ENCHANTS), Rarity.LEGENDARY);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true, false));

        player.getInventory().setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Ninja Hood").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Ninja Shirt").color(Color.BLACK).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Ninja Pants").enchantment(Enchantment.THORNS, 1).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Ninja Shoes").color(Color.BLACK).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        ItemStack potato = new ItemStack(new ItemBuilder(Material.IRON_SWORD).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE)
                .name(new ColorBuilder(ChatColor.LIGHT_PURPLE).bold().create() + "Ninja Dagger"));

        player.getInventory().addItem(potato);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (UpdateRunnable.updating) {
            return;
        }
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (player.getLocation().getBlockY() >= 94)
                        return;

                    if (!kitAbility.tryUse(player)) {
                        player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
                        return;
                    }

                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        players.hidePlayer(player);
                        Bukkit.getServer().getScheduler().runTaskLater(BattlegroundsKitPvP.getInstance(), () -> {
                            if (KitAbility.getPlayerStatus().containsKey(player)) {
                                ParticleEffect.SMOKE_LARGE.display(0.3F, 0.5F, 0.3F, 0, 70, player.getLocation().add(0, 0.5, 0), 35);
                                player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 0.5F, 1.3F);
                                players.showPlayer(player);
                            }
                        }, 100L);
                    }
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 0.75F, 1F);
                    ParticleEffect.SMOKE_LARGE.display(0.3F, 0.5F, 0.3F, 0, 70, player.getLocation().add(0, 0.5, 0), 35);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0, false, false));
                    Bukkit.getServer().getScheduler().runTaskLater(BattlegroundsKitPvP.getInstance(), () -> {
                        if (KitAbility.getPlayerStatus().containsKey(player)) {
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2F, 1.5F);
                        }
                    }, 70);
                    Bukkit.getServer().getScheduler().runTaskLater(BattlegroundsKitPvP.getInstance(), () -> {
                        if (KitAbility.getPlayerStatus().containsKey(player)) {
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2F, 1.5F);
                        }
                    }, 80);
                    Bukkit.getServer().getScheduler().runTaskLater(BattlegroundsKitPvP.getInstance(), () -> {
                        if (KitAbility.getPlayerStatus().containsKey(player)) {
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2F, 1.5F);
                        }
                    }, 90);
                }
            }
        }
    }
}
