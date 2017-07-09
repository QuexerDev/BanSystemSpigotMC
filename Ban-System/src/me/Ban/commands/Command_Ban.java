package me.Ban.commands;

import me.Ban.main.Main;
import me.Ban.utils.BanAPI;
import me.Ban.utils.MuteAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by elija on 25.06.2017.
 */
public class Command_Ban extends Command{


    public Command_Ban(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("ban.ban")) {
            if(args.length == 2) {

                String id = args[1];
                String name = args[0];
                switch (id) {


                        case "1":
                            BanAPI.banPlayer(name, "Hacking", s.getName(), 24 * 30);
                            break;

                        case "2":
                            BanAPI.banPlayer(name, "Trolling", s.getName(), 24 * 4);
                            break;

                        case "3":
                            BanAPI.banPlayer(name, "Bugusing", s.getName(), 24 * 7);
                            break;

                        case "4":
                            BanAPI.banPlayer(name, "Skin", s.getName(), 24 * 2);
                            break;

                        case "5":
                            BanAPI.banPlayer(name, "Name", s.getName(), 24 * 2);
                            break;

                        case "6":
                            BanAPI.banPlayer(name, "Report-Ausnutzung", s.getName(), 24 * 2);
                            break;

                        case "7":
                            BanAPI.banPlayer(name, "Bannumgehung", s.getName(), -1);
                            break;

                        case "8":
                            BanAPI.banPlayer(name, "Hausverbot", s.getName(), -1);
                            break;


                    case "9":
                        MuteAPI.mutePlayer(name, "Beleidigung", s.getName(), 24 * 3);
                        break;

                    case "10":
                        MuteAPI.mutePlayer(name, "Rassismus", s.getName(), 24 * 7);
                        break;

                    case "11":
                        MuteAPI.mutePlayer(name, "Werbung", s.getName(), 24 * 2);
                        break;

                    case "12":
                        MuteAPI.mutePlayer(name, "Spamming", s.getName(), 12);
                        break;

                    case "13":
                        MuteAPI.mutePlayer(name, "Provokation", s.getName(), 12);
                        break;

                    case "14":
                        MuteAPI.mutePlayer(name, "Schweigepfilcht", s.getName(), -1);
                        break;


                } 

            } else {
            	s.sendMessage(Main.pf+"§7Benutze§8: §c/ban <Spieler> <Grund>");
            	s.sendMessage(Main.pf+"§7Für eine Liste mit Gründen benutze§8: §c/reasons");
            }
        }else {
        	s.sendMessage(Main.pf+"§cDazu hast du keine Rechte!");
        }
    }
}
