package com.battlegroundspvp.worldpvp;
/* Created by GamerBah on 10/8/2017 */

import com.battlegroundspvp.BattlegroundsKitPvP;
import com.battlegroundspvp.global.utils.kits.Kit;
import com.battlegroundspvp.worldpvp.commands.SpawnCommand;
import com.battlegroundspvp.worldpvp.commands.SpectateCommand;
import com.battlegroundspvp.worldpvp.commands.TeamCommand;
import com.battlegroundspvp.worldpvp.kits.KitManager;
import com.battlegroundspvp.worldpvp.listeners.ItemSpawnListener;
import com.battlegroundspvp.worldpvp.listeners.ScoreboardListener;
import com.battlegroundspvp.worldpvp.listeners.SpawnProtectListener;
import com.battlegroundspvp.worldpvp.playerevents.*;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldPvP {

    @Getter
    private static ArrayList<Kit> kitRewards = new ArrayList<>();
    @Getter
    private static List<Player> noFall = new ArrayList<>();
    @Getter
    private static HashMap<Player, Integer> rolling = new HashMap<>();

    public static HashMap<String, CommandExecutor> getCommands() {
        // DON'T FORGET TO REGISTER THE COMMAND IN THE CORE PLUGIN.YML!
        HashMap<String, CommandExecutor> commands = new HashMap<>();
        commands.put("spectate", new SpectateCommand());
        commands.put("team", new TeamCommand(BattlegroundsKitPvP.getInstance()));
        commands.put("spawn", new SpawnCommand(BattlegroundsKitPvP.getInstance()));

        return commands;
    }

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
        pluginManager.registerEvents(new PlayerMove(BattlegroundsKitPvP.getInstance()), BattlegroundsKitPvP.getInstance());
    }

}
