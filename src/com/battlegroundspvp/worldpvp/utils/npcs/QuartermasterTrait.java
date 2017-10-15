package com.battlegroundspvp.worldpvp.utils.npcs;
/* Created by GamerBah on 10/14/2017 */

import com.battlegroundspvp.BattlegroundsKitPvP;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("Quartermaster")
public class QuartermasterTrait extends Trait {

    public QuartermasterTrait() {
        super("Quartermaster");
    }

    //Run code when your trait is attached to a NPC.
    //This is called BEFORE onSpawn, so npc.getBukkitEntity() will return null
    //This would be a good place to load configurable defaults for new NPCs.
    @Override
    public void onAttach() {
        BattlegroundsKitPvP.getInstance().getServer().getLogger().info(npc.getName() + " has been assigned Quartermaster Trait!");
    }

    //run code when the NPC is removed. Use this to tear down any repeating tasks.
    @Override
    public void onRemove() {
        BattlegroundsKitPvP.getInstance().getServer().getLogger().info(npc.getName() + " has been removed!");
    }
}
