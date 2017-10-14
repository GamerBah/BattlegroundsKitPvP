package com.battlegroundspvp.worldpvp.kits.Rare;
/* Created by GamerBah on 9/13/2016 */

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.global.utils.kits.KitAbility;
import com.battlegroundspvp.runnables.UpdateRunnable;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.utils.packets.particles.ParticleEffect;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import de.Herbystar.TTA.TTA_Methods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Blaze extends Kit {
    private KitAbility kitAbility = new KitAbility(3, 8.00);
    private List<Player> firerate = new ArrayList<>();

    public Blaze() {
        super(21, "Blaze", new ItemBuilder(Material.FIREBALL)
                .lore(" ")
                .lore("§a§lKit Contents:")
                .lore("§7   ● §fIron Helmet §7(Protection I)")
                .lore("§7   ● Leather Chestplate")
                .lore("§7   ● §fIron Boots")
                .lore("§7   ● §3Blaze Rod §7(Sharpness VII)")
                .lore(" ")
                .lore("§c§lPotion Effects:")
                .lore("§7   ● §aFire Resistance")
                .lore(" ")
                .lore("§b§lAbility:")
                .lore("§7Shoot exploding fireballs")
                .flag(ItemFlag.HIDE_ATTRIBUTES), Rarity.RARE);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));

        player.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).name(Rarity.RARE.getColor() + "Blaze Helmet").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).name(Rarity.RARE.getColor() + "Blaze Chestplate").color(Color.ORANGE).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));
        player.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).name(Rarity.RARE.getColor() + "Blaze Boots").unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        ItemStack sword = new ItemStack(new ItemBuilder(Material.BLAZE_ROD).name(Rarity.RARE.getColor() + "Blaze Rod").enchantment(Enchantment.DAMAGE_ALL, 7).unbreakable().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE));

        player.getInventory().addItem(sword);

        BattlegroundsCore.clearTitle(player);
        TTA_Methods.sendTitle(player, null, 5, 35, 10, ChatColor.GREEN + "Right-Click to use Ability!", 5, 35, 10);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (UpdateRunnable.updating) {
            return;
        }
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD) {
                if (firerate.contains(player)) {
                    return;
                }
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    firerate.add(player);
                    Bukkit.getServer().getScheduler().runTaskLater(BattlegroundsKitPvP.getInstance(), () -> firerate.remove(player), 5L);
                    if (player.getLocation().distance(player.getWorld().getSpawnLocation()) <= 12) {
                        return;
                    }
                    if (!kitAbility.tryUse(player)) {
                        player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
                        return;
                    }

                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        players.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1);
                    }
                    SmallFireball fireball = player.launchProjectile(SmallFireball.class);
                    fireball.setVelocity(player.getLocation().getDirection());
                    fireball.setIsIncendiary(true);
                    fireball.setYield(2.0F);
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof SmallFireball) {
            event.getEntity().remove();
            ((SmallFireball) event.getEntity()).setYield(0F);
            if (event.getEntity().getLocation().distance(event.getEntity().getWorld().getSpawnLocation()) > 20) {
                ParticleEffect.EXPLOSION_LARGE.display(0, 0, 0, 0, 5, event.getEntity().getLocation(), 35);
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.playSound(event.getEntity().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                    if (player.getLocation().distance(event.getEntity().getLocation()) <= 5) {
                        if (event.getEntity().getShooter() != player) {
                            player.damage(3.5, (Player) event.getEntity().getShooter());
                        }
                    }
                }
            }
        }
    }
}
