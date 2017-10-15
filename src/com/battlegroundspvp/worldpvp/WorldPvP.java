package com.battlegroundspvp.worldpvp;
/* Created by GamerBah on 10/8/2017 */

import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.worldpvp.commands.SpawnCommand;
import com.battlegroundspvp.worldpvp.commands.SpectateCommand;
import com.battlegroundspvp.worldpvp.commands.TeamCommand;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import com.battlegroundspvp.worldpvp.listeners.ItemSpawnListener;
import com.battlegroundspvp.worldpvp.listeners.ScoreboardListener;
import com.battlegroundspvp.worldpvp.listeners.SpawnProtectListener;
import com.battlegroundspvp.worldpvp.playerevents.*;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class WorldPvP {

    @Getter
    private static List<Player> noFall = new ArrayList<>();

    public void registerEvents(PluginManager pluginManager) {
        pluginManager.registerEvents(new ItemSpawnListener(), BattlegroundsKitPvP.getInstance());
        pluginManager.registerEvents(new ScoreboardListener(), BattlegroundsKitPvP.getInstance());
        pluginManager.registerEvents(new SpawnProtectListener(), BattlegroundsKitPvP.getInstance());
        pluginManager.registerEvents(new KitManager(BattlegroundsKitPvP.getInstance()), BattlegroundsKitPvP.getInstance());

        pluginManager.registerEvents(new PlayerDeath(BattlegroundsKitPvP.getInstance()), BattlegroundsKitPvP.getInstance());
        pluginManager.registerEvents(new PlayerFoodLevelChange(), BattlegroundsKitPvP.getInstance());
        pluginManager.registerEvents(new PlayerItemDrop(), BattlegroundsKitPvP.getInstance());
        pluginManager.registerEvents(new PlayerItemPickup(), BattlegroundsKitPvP.getInstance());
        pluginManager.registerEvents(new PlayerSwapHands(), BattlegroundsKitPvP.getInstance());
        pluginManager.registerEvents(new PlayerInteractItem(), BattlegroundsKitPvP.getInstance());
        pluginManager.registerEvents(new PlayerMove(BattlegroundsKitPvP.getInstance()), BattlegroundsKitPvP.getInstance());
        pluginManager.registerEvents(new PlayerInteractEntity(), BattlegroundsKitPvP.getInstance());
    }

    public void registerCommands() {
        BattlegroundsKitPvP.getInstance().getCommand("spectate").setExecutor(new SpectateCommand());
        BattlegroundsKitPvP.getInstance().getCommand("team").setExecutor(new TeamCommand(BattlegroundsKitPvP.getInstance()));
        BattlegroundsKitPvP.getInstance().getCommand("spawn").setExecutor(new SpawnCommand(BattlegroundsKitPvP.getInstance()));
    }

}
