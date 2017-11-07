package com.battlegroundspvp;
/* Created by GamerBah on 10/17/2017 */

import com.battlegroundspvp.administration.data.Rank;
import com.battlegroundspvp.global.commands.NPCCommand;
import com.battlegroundspvp.global.cosmetics.common.gores.StoneBreakGore;
import com.battlegroundspvp.global.cosmetics.common.trails.BubblyTrail;
import com.battlegroundspvp.global.cosmetics.common.warcries.CowPunchWarcry;
import com.battlegroundspvp.global.cosmetics.common.warcries.MoooWarcry;
import com.battlegroundspvp.global.cosmetics.epic.trails.LavaRainTrail;
import com.battlegroundspvp.global.cosmetics.epic.trails.RainStormTrail;
import com.battlegroundspvp.global.cosmetics.epic.warcries.ExplosionWarcry;
import com.battlegroundspvp.global.cosmetics.epic.warcries.MorbidPiggyWarcry;
import com.battlegroundspvp.global.cosmetics.legendary.gores.HarshRemovalGore;
import com.battlegroundspvp.global.cosmetics.legendary.trails.FlameWarriorTrail;
import com.battlegroundspvp.global.cosmetics.legendary.warcries.MeowWarcry;
import com.battlegroundspvp.global.cosmetics.legendary.warcries.WeaponsmithWarcry;
import com.battlegroundspvp.global.cosmetics.rare.gores.CloudGore;
import com.battlegroundspvp.global.cosmetics.rare.warcries.AnvilWarcry;
import com.battlegroundspvp.global.cosmetics.rare.warcries.HappyPiggyWarcry;
import com.battlegroundspvp.global.listeners.CombatListener;
import com.battlegroundspvp.utils.cosmetics.Cosmetic;
import com.battlegroundspvp.utils.cosmetics.Gore;
import com.battlegroundspvp.utils.cosmetics.ParticlePack;
import com.battlegroundspvp.utils.cosmetics.Warcry;
import com.battlegroundspvp.worldpvp.WorldPvP;
import com.battlegroundspvp.worldpvp.listeners.ScoreboardListener;
import com.battlegroundspvp.worldpvp.playerevents.PlayerInteractEntity;
import com.battlegroundspvp.worldpvp.playerevents.PlayerInteractItem;
import com.battlegroundspvp.worldpvp.playerevents.PlayerRespawn;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ModulePlugin extends BattleModule {

    @Override
    public Gore getActiveGore(Player player) {
        return (Gore) Cosmetic.fromId(Cosmetic.ServerType.KITPVP, BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId()).getKitPvpData().getActiveGore());
    }

    @Override
    public ParticlePack getActiveTrail(Player player) {
        return (ParticlePack) Cosmetic.fromId(Cosmetic.ServerType.KITPVP, BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId()).getKitPvpData().getActiveTrail());
    }

    @Override
    public Warcry getActiveWarcry(Player player) {
        return (Warcry) Cosmetic.fromId(Cosmetic.ServerType.KITPVP, BattlegroundsCore.getInstance().getGameProfile(player.getUniqueId()).getKitPvpData().getActiveWarcry());
    }

    @Override
    public HashMap<String, CommandExecutor> getCommands() {
        // DON'T FORGET TO REGISTER THE COMMAND IN THE CORE PLUGIN.YML!
        HashMap<String, CommandExecutor> commands = new HashMap<>();
        commands.putAll(WorldPvP.getCommands());
        commands.put("npc", new NPCCommand());

        return commands;
    }

    @Override
    public ArrayList<Cosmetic> getCosmetics() {
        ArrayList<Cosmetic> cosmetics = new ArrayList<>();
        // Common
        cosmetics.add(new StoneBreakGore());
        cosmetics.add(new BubblyTrail());
        cosmetics.add(new CowPunchWarcry());
        cosmetics.add(new MoooWarcry());

        // Rare
        cosmetics.add(new CloudGore());
        cosmetics.add(new AnvilWarcry());
        cosmetics.add(new HappyPiggyWarcry());

        // Epic
        cosmetics.add(new LavaRainTrail());
        cosmetics.add(new RainStormTrail());
        cosmetics.add(new ExplosionWarcry());
        cosmetics.add(new MorbidPiggyWarcry());

        // Legendary
        cosmetics.add(new HarshRemovalGore());
        cosmetics.add(new FlameWarriorTrail());
        cosmetics.add(new MeowWarcry());
        cosmetics.add(new WeaponsmithWarcry());

        return cosmetics;
    }

    @Override
    public List<UUID> getInCombat() {
        List<UUID> tagged = new ArrayList<>();
        tagged.addAll(CombatListener.getTagged().keySet());
        return tagged;
    }

    @Override
    public String getName() {
        return "KitPvP";
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        PlayerInteractEntity.interact(event);
    }

    @Override
    public void onPlayerInteractItem(PlayerInteractEvent event) {
        PlayerInteractItem.interact(event);
    }

    @Override
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        PlayerRespawn.respawn(event);
    }

    @Override
    public void updateScoreboardCoins(Player player, int amount) {
        ScoreboardListener scoreboardListener = new ScoreboardListener();
        scoreboardListener.updateScoreboardCoins(player, amount);
    }

    @Override
    public void updateScoreboardRank(Player player, Rank rank) {
        ScoreboardListener scoreboardListener = new ScoreboardListener();
        scoreboardListener.updateScoreboardRank(player, rank);
    }

}
