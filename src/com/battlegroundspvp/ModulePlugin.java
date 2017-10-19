package com.battlegroundspvp;
/* Created by GamerBah on 10/17/2017 */

import com.battlegroundspvp.commands.NPCCommand;
import com.battlegroundspvp.worldpvp.WorldPvP;
import com.battlegroundspvp.worldpvp.playerevents.PlayerInteractEntity;
import com.battlegroundspvp.worldpvp.playerevents.PlayerInteractItem;
import com.battlegroundspvp.worldpvp.playerevents.PlayerRespawn;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;

public class ModulePlugin extends BattleModule {

    @Override
    public String getName() {
        return "KitPvP";
    }

    @Override
    public void onPlayerInteractItem(PlayerInteractEvent event) {
        PlayerInteractItem.interact(event);
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        PlayerInteractEntity.interact(event);
    }

    @Override
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        PlayerRespawn.respawn(event);
    }

    @Override
    public HashMap<String, CommandExecutor> getCommands() {
        // DON'T FORGET TO REGISTER THE COMMAND IN THE CORE PLUGIN.YML!
        HashMap<String, CommandExecutor> commands = new HashMap<>();
        commands.putAll(WorldPvP.getCommands());
        commands.put("npc", new NPCCommand());

        return commands;
    }

}
