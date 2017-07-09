package me.Ban.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by elija on 25.06.2017.
 */
public class Commands_Reasons extends Command{

    public Commands_Reasons(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("ban.ban")) {
            s.sendMessage("§8§m--------------------------");
            s.sendMessage("§7Ban§8:");
            s.sendMessage("§cHacking §8- §c#1");
            s.sendMessage("§cTrolling §8- §c#2");
            s.sendMessage("§cBugusing §8- §c#3");
            s.sendMessage("§cSkin §8- §c#4");
            s.sendMessage("§cName §8- §c#5");
            s.sendMessage("§cReport-Ausnutzung §8- §c#6");
            s.sendMessage("§cBannumgehung §8- §c#7");
            s.sendMessage("§cHausverbot §8- §c#8");
            s.sendMessage("§7Mute§8:");
            s.sendMessage("§cBeleidigung §8- §c#9");
            s.sendMessage("§cRassismus §8- §c#10");
            s.sendMessage("§cWerbung §8- §c#11");
            s.sendMessage("§cSpamming §8- §c#12");
            s.sendMessage("§cProvokation §8- §c#13");
            s.sendMessage("§cSchweigepflicht §8-§c#14");
            s.sendMessage("§8§m--------------------------");

        }


    }
}
