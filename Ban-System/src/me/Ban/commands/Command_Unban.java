package me.Ban.commands;

import me.Ban.main.Main;
import me.Ban.utils.BanAPI;
import me.Ban.utils.MuteAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Created by elija on 25.06.2017.
 */
public class Command_Unban extends Command{

    public Command_Unban(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("ban.ban")) {
            if(args.length == 3) {
                String name = args[0];
                String typ = args[1];
                String grund = args[2];

                if(typ.equalsIgnoreCase("mute")) {
                    if(MuteAPI.isMuted(name)) {
                        MuteAPI.unBanPlayer(name, grund, s.getName());
                    } else {
                        s.sendMessage(Main.pf+"§7Dieser Spieler ist §cnicht §7gemutet§8!");
                    }
                } else if(typ.equalsIgnoreCase("ban")) {
                    if(BanAPI.isBanned(name)) {
                        BanAPI.unBanPlayer(name, grund, s.getName());
                    } else {
                        s.sendMessage(Main.pf+"§7Dieser Spieler ist §cnicht §7gebannt§8!");
                    }
                } else {
                    s.sendMessage(Main.pf+"§7Benutze§8: §c/unban <Name> <Mute|Ban> <Grund>");
                }



            } else {
                s.sendMessage(Main.pf+"§7Benutze§8: §c/unban <Name> <Mute|Ban> <Grund>");
            }
        }
    }
}
