package me.Ban.commands;

import me.Ban.main.Main;
import me.Ban.utils.BanAPI;
import me.Ban.utils.MuteAPI;
import me.Ban.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by elija on 25.06.2017.
 */
public class Command_Check extends Command{

    public Command_Check (String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender.hasPermission("ban.ban")) {
            if(args.length == 1) {

                String mute = "§cNicht Gemutet!";
                String ban = "§cNicht Gebannt!";

                String name = args[0];

                sender.sendMessage("§8§m-------------------------");
                sender.sendMessage("§7Name§8: §a"+name);
                sender.sendMessage("§7UUID§8: §a"+ UUIDFetcher.getUUID(name));
                if(BanAPI.isBanned(name)) {
                    sender.sendMessage("§7Gebannt§8: §aJa");
                    sender.sendMessage("§7Ban-ID§8: §c"+BanAPI.getID(name));
                    sender.sendMessage("§7Verbleibende Zeit§8: "+BanAPI.getEndAsString(name));
                    sender.sendMessage("§7Grund§8: §e"+BanAPI.getGrund(name));
                } else {
                    sender.sendMessage("§7Gebannt§8: §cNein");
                }
                if(MuteAPI.isMuted(name)) {
                    sender.sendMessage("§7Gemutet§8: §aJa");
                    sender.sendMessage("§7Mute-ID§8: §c"+MuteAPI.getID(name));
                    sender.sendMessage("§7Verbleibende Zeit§8: "+MuteAPI.getEndAsString(name));
                    sender.sendMessage("§7Grund§8: §e"+MuteAPI.getGrund(name));
                } else {
                    sender.sendMessage("§7Gemutet§8: §cNein");
                }

                sender.sendMessage("§8§m-------------------------");




            } else {
                sender.sendMessage(Main.pf+"§7Benutze§8: §c/check <Name>");
            }






        }


    }
}
