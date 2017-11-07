package com.battlegroundspvp.worldpvp.utils.teams;

import com.battlegroundspvp.BattlegroundsCore;
import com.battlegroundspvp.utils.enums.EventSound;
import com.battlegroundspvp.utils.messages.TextComponentMessages;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class TeamMessages {

    private TextComponentMessages tcm = new TextComponentMessages(BattlegroundsCore.getInstance());

    public void sendAcceptMessage(Player target, Player sender) {
        EventSound.playSound(sender, EventSound.TEAM_REQUEST_ACCEPT);
        sender.sendMessage(GREEN + "   \u00AB " + AQUA + target.getName() + YELLOW + " has " + GREEN + "accepted " + YELLOW + "your request to team!" + GREEN + " \u00BB");

        EventSound.playSound(target, EventSound.TEAM_REQUEST_ACCEPT);
        target.sendMessage(GREEN + "   \u00AB " + YELLOW + "You have " + GREEN + "accepted " + AQUA + sender.getName() + YELLOW + "'s request to team!" + GREEN + " \u00BB");
    }

    public void sendDeclineMessage(Player target, Player sender) {
        EventSound.playSound(sender, EventSound.TEAM_REQUEST_DENY);
        sender.sendMessage(RED + "   \u00AB " + AQUA + target.getName() + YELLOW + " has " + RED + "declined " + YELLOW + "your request to team!" + RED + " \u00BB");

        EventSound.playSound(target, EventSound.TEAM_REQUEST_DENY);
        target.sendMessage(RED + "   \u00AB " + YELLOW + "You have " + RED + "declined " + AQUA + sender.getName() + YELLOW + "'s request to team!" + RED + " \u00BB");
    }

    public void sendRequestMessage(Player sender, Player target) {
        EventSound.playSound(sender, EventSound.TEAM_REQUEST);
        sender.sendMessage(WHITE + "   \u00AB " + GREEN + "Your request to team has been sent to " + YELLOW + target.getName() + WHITE + " \u00BB");

        EventSound.playSound(target, EventSound.TEAM_REQUEST);
        target.sendMessage(" ");
        target.sendMessage(GOLD + "   \u00AB " + WHITE + "========================================" + GOLD + " \u00BB");
        target.sendMessage(" ");
        target.sendMessage(YELLOW + "           " + sender.getName() + AQUA + " has sent you a request to team!");
        target.sendMessage(" ");
        BaseComponent component = tcm.centerTextSpacesLeft();
        component.addExtra(tcm.teamAcceptButton());
        component.addExtra(tcm.centerTextSpacesMiddle());
        component.addExtra(tcm.teamDenyButton());
        target.spigot().sendMessage(component);
        target.sendMessage(GOLD + "   \u00AB " + WHITE + "========================================" + GOLD + " \u00BB");
        target.sendMessage(" ");
    }
}
