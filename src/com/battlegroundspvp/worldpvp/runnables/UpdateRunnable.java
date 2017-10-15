package com.battlegroundspvp.worldpvp.runnables;

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.administration.commands.FreezeCommand;
import com.battlegroundspvp.utils.ColorBuilder;
import com.battlegroundspvp.utils.DiscordBot;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.worldpvp.utils.PluginUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UpdateRunnable implements Runnable {

    public static boolean updating = false;
    private BattlegroundsKitPvP plugin;
    private File updateFile, currentFile;

    public UpdateRunnable(BattlegroundsKitPvP plugin) {
        this.plugin = plugin;

        if (!plugin.getServer().getUpdateFolderFile().exists()) {
            plugin.getServer().getUpdateFolderFile();
        }

        updateFile = new File(plugin.getServer().getUpdateFolderFile().getPath() + File.separator + "BattlegroundsKitPvP.jar");
        currentFile = new File(plugin.getServer().getUpdateFolderFile().getParentFile().getPath() + File.separator + "BattlegroundsKitPvP.jar");
    }

    @Override
    public void run() {
        if (updateFile == null || !updateFile.exists() || updating) {
            return;
        }
        if (!com.battlegroundspvp.runnables.UpdateRunnable.queuedUpdates.isEmpty()) {
            if (!com.battlegroundspvp.runnables.UpdateRunnable.queuedUpdates.contains(BattlegroundsKitPvP.getInstance())) {
                com.battlegroundspvp.runnables.UpdateRunnable.queuedUpdates.add(BattlegroundsKitPvP.getInstance());
            } else {
                if (com.battlegroundspvp.runnables.UpdateRunnable.queuedUpdates.get(0).equals(BattlegroundsKitPvP.getInstance())) {
                    update();
                }
            }
        } else {
            plugin.getServer().broadcastMessage(new ColorBuilder(ChatColor.RED).bold().create() + "SERVER: " + ChatColor.GRAY + "Reloading in 5 seconds for an update. Hang in there!");
            plugin.getServer().broadcastMessage(ChatColor.GRAY + "(You'll get sent back to the spawn once the update is complete)");
            update();
        }

    }

    private void update() {
        updating = true;
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            EventSound.playSound(player, EventSound.CLICK);
            player.setGameMode(GameMode.ADVENTURE);
            player.closeInventory();
            plugin.getServer().getScheduler().runTaskLater(plugin, player::closeInventory, 10L);
            FreezeCommand.reloadFreeze = true;
            for (PotionEffect potionEffect : player.getActivePotionEffects())
                player.removePotionEffect(potionEffect.getType());
            player.setWalkSpeed(0F);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -100, true, false));
            player.setFoodLevel(6);
            player.setSaturation(0);
            player.sendMessage(" ");
            player.sendMessage(ChatColor.YELLOW + "To prevent data corruption, you've been frozen.");
            player.sendMessage(ChatColor.YELLOW + "You'll be unfrozen once the update is complete.\n ");
        }
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            try {
                BattlegroundsCore.syncGameProfiles();
                PluginUtil.unload(plugin);
                try {
                    Files.move(Paths.get(currentFile.getPath()), Paths.get(plugin.getDataFolder().getPath() + File.separator + "BattlegroundsKitPvP.jar"),
                            StandardCopyOption.REPLACE_EXISTING);
                    currentFile = new File(plugin.getDataFolder().getPath() + File.separator + "BattlegroundsKitPvP.jar");
                    Files.move(Paths.get(updateFile.getPath()), Paths.get(plugin.getServer().getUpdateFolderFile().getParentFile().getPath()
                            + File.separator + "BattlegroundsKitPvP.jar"), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException exception) {
                    plugin.getLogger().severe("Unable to complete update!");
                    DiscordBot.errorLoggingChannel.sendMessage("There was an error trying to update BattlegroundsKitPvP! (Code: )").queue();
                    exception.printStackTrace();
                    deleteUpdateFile();
                }

                try {
                    PluginUtil.load("BattlegroundsKitPvP");
                } catch (Throwable throwable) {
                    plugin.getLogger().severe("Unable to preform plugin update!");
                    DiscordBot.errorLoggingChannel.sendMessage("There was an error trying to update BattlegroundsKitPvP! (Code: )").queue();
                    throwable.printStackTrace();
                    revert();
                    return;
                }
                if (com.battlegroundspvp.runnables.UpdateRunnable.queuedUpdates.isEmpty())
                    plugin.getServer().broadcastMessage(new ColorBuilder(ChatColor.RED).bold().create() + "SERVER: " + ChatColor.GRAY
                            + "Update was a " + new ColorBuilder(ChatColor.GREEN).bold().create() + "success" + ChatColor.GRAY + "! Now go have fun!");
            } catch (Throwable throwable) {
                plugin.getLogger().severe("Error during pre-update, sticking with the current version");
                DiscordBot.errorLoggingChannel.sendMessage("There was an error trying to update BattlegroundsKitPvP! (Code: )").queue();
                plugin.getLogger().severe(throwable.getMessage());
                throwable.printStackTrace();
                plugin.getServer().broadcastMessage(new ColorBuilder(ChatColor.RED).bold().create() + "SERVER: " + ChatColor.GRAY
                        + "Stuck with current version due to issues with the update.");
                deleteUpdateFile();
            } finally {
                com.battlegroundspvp.runnables.UpdateRunnable.queuedUpdates.remove(BattlegroundsKitPvP.getInstance());
                if (com.battlegroundspvp.runnables.UpdateRunnable.queuedUpdates.isEmpty()) {
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        EventSound.playSound(player, EventSound.ACTION_SUCCESS);
                        player.setWalkSpeed(0.2F);
                        player.spigot().respawn();
                    }
                    updating = false;
                    FreezeCommand.reloadFreeze = false;
                }
            }
        }, 100);
    }

    private void revert() {
        plugin.getLogger().severe("Reverting to working jarfile, hang tight...");
        plugin.getServer().broadcastMessage(new ColorBuilder(ChatColor.RED).bold().create() + "SERVER: " + ChatColor.GRAY
                + "Update was a " + new ColorBuilder(ChatColor.RED).bold().create() + "failure" + ChatColor.GRAY + "!");
        plugin.getServer().broadcastMessage(new ColorBuilder(ChatColor.RED).bold().create() + "SERVER: " + ChatColor.GRAY
                + "Reverting to the previous version, hang tight...");
        try {
            Files.move(currentFile.toPath(), Paths.get(plugin.getServer().getUpdateFolderFile().getParentFile().getPath()
                            + File.separator + "BattlegroundsKitPvP.jar"),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            plugin.getLogger().severe("Unable to revert!");
            exception.printStackTrace();
            DiscordBot.errorLoggingChannel.sendMessage("There was an error trying to update BattlegroundsKitPvP! (Code: )").queue();
        }
        try {
            PluginUtil.load("BattlegroundsKitPvP");
        } catch (Throwable throwable) {
            plugin.getLogger().severe("Reverted jar has errors, but it'll have to do");
            throwable.printStackTrace();
            DiscordBot.errorLoggingChannel.sendMessage("There was an error trying to update BattlegroundsKitPvP! (Code: )").queue();
        }
        plugin.getServer().broadcastMessage(new ColorBuilder(ChatColor.RED).bold().create() + "SERVER: " + ChatColor.GRAY
                + "Successfully reverted to the previous version.");
    }

    private void deleteUpdateFile() {
        try {
            Files.delete(updateFile.toPath());
        } catch (IOException exception) {
            plugin.getLogger().severe("Unable to delete queued jarfile!");
            exception.printStackTrace();
            DiscordBot.errorLoggingChannel.sendMessage("There was an error trying to update BattlegroundsKitPvP! (Code: )").queue();
        }
    }
}
