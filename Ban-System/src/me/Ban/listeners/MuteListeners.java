package me.Ban.listeners;

import me.Ban.main.Main;
import me.Ban.utils.BanAPI;
import me.Ban.utils.MuteAPI;
import me.Ban.utils.UUIDFetcher;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static me.Ban.utils.BanAPI.getEndAsString;
import static me.Ban.utils.BanAPI.getID;

/**
 * Created by elija on 25.06.2017.
 */
public class MuteListeners implements Listener{

    @EventHandler
    public void onChat(ChatEvent e) {
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        String name = UUIDFetcher.getName(p.getUniqueId());

        if(MuteAPI.isMuted(name)) {
            if(System.currentTimeMillis() < MuteAPI.getEnd(name)) {
                if (!e.getMessage().startsWith("/")) {
                    e.setCancelled(true);
                    BungeeCord.getInstance().getPlayer(name).sendMessage(" ");
                    BungeeCord.getInstance().getPlayer(name).sendMessage(Main.pf + "§8§m------------------------------");
                    BungeeCord.getInstance().getPlayer(name).sendMessage(Main.pf + "§7Du bist Gemuted§8!");
                    BungeeCord.getInstance().getPlayer(name).sendMessage(Main.pf + "§7GrundÂ§8: §e" + MuteAPI.getGrund(name) + "§8[§eID: " + MuteAPI.getID(name) + "§8]");
                    BungeeCord.getInstance().getPlayer(name).sendMessage(Main.pf + "§7Gemuted von§8: §a" + MuteAPI.getVonwem(name));
                    BungeeCord.getInstance().getPlayer(name).sendMessage(Main.pf + "§7Verbleibende Zeit§8: " + MuteAPI.getEndAsString(name));
                    BungeeCord.getInstance().getPlayer(name).sendMessage(Main.pf + "§8§m------------------------------");
                    BungeeCord.getInstance().getPlayer(name).sendMessage(" ");
                }
            }
        }



    }
}
