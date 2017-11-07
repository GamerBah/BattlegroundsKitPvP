package com.battlegroundspvp.global.utils.kits;

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.administration.commands.FreezeCommand;
import com.battlegroundspvp.administration.data.GameProfile;
import com.battlegroundspvp.administration.data.Rank;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.utils.enums.Rarity;
import com.battlegroundspvp.utils.inventories.ItemBuilder;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import lombok.Data;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;

/**
 * Represents a kit
 *
 * @author computerwizjared
 */
@Data
public abstract class Kit implements Listener, CommandExecutor {
    /**
     * ID of the kit
     */
    private int id;
    /**
     * Item representing the kit
     * Contains lore and display name
     */
    private ItemBuilder item = new ItemBuilder(Material.AIR);
    /**
     * Name of the kit
     */
    private String name = "";
    /**
     * Type of the kit
     */
    private Rarity rarity = Rarity.COMMON;

    /**
     * Defines a kit
     *
     * @param name   Name of the kit
     * @param item   Item representing the kit
     * @param rarity Color of the kit
     */
    public Kit(Integer id, String name, ItemBuilder item, Rarity rarity) {
        this.id = id;
        item.name(rarity.getColor() + name);
        this.name = name;
        this.item = item;
        this.rarity = rarity;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equalsIgnoreCase(getName().replaceAll("\\s+", ""))) {
                if (!FreezeCommand.frozenPlayers.contains(player) && !FreezeCommand.frozen && BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId()).getKitPvpData().getOwnedKits().contains(this.getId() + ",")) {
                    wearCheckLevel(player);
                }
                if (!BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId()).getKitPvpData().getOwnedKits().contains(this.getId() + ",")) {
                    player.sendMessage(ChatColor.RED + "You haven't unlocked this kit yet!");
                    EventSound.playSound(player, EventSound.ACTION_FAIL);
                }
            }
        }
        return false;
    }

    /**
     * Wear the kit
     *
     * @param player Player to wear kit
     */
    protected abstract void wear(Player player);

    /**
     * Wear the kit with permissions and messages
     *
     * @param player Player to wear kit
     */
    public void wearCheckLevel(Player player) {
        GameProfile gameProfile = BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId());
        if (!KitManager.isPlayerInKit(player) || (gameProfile.hasRank(Rank.WARRIOR) && player.getLocation().distance(player.getWorld().getSpawnLocation()) < 200)) {
            KitManager.getPlayersInKits().put(player.getUniqueId(), this);

            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }

            player.getInventory().clear();
            player.getInventory().setArmorContents(null);

            wear(player);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 2F, 1.1F);

        } else {
            player.sendMessage(ChatColor.RED + "You have not died yet!");
        }
    }
}
