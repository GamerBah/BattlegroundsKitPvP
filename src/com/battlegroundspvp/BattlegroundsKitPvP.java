package com.battlegroundspvp;
/* Created by GamerBah on 10/8/2017 */

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class BattlegroundsKitPvP extends JavaPlugin {

    @Getter
    private BattlegroundsKitPvP instance;

    public void onEnable() {
        instance = this;
    }

    public void onDisable() {

    }

}
