package me.Ban.listeners;

import me.Ban.utils.BanAPI;
import me.Ban.utils.MuteAPI;
import me.Ban.utils.UUIDFetcher;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static me.Ban.utils.BanAPI.getEndAsString;
import static me.Ban.utils.BanAPI.getID;

/**
 * Created by elija on 25.06.2017.
 */
public class BanListeners implements Listener{



        @EventHandler
        public void onLogin(LoginEvent e) {
            String name = UUIDFetcher.getName(e.getConnection().getUniqueId());
            if(BanAPI.isBanned(name)) {
                if(System.currentTimeMillis() < BanAPI.getEnd(name)) {
                    e.setCancelled(true);
                    e.setCancelReason("§8§m------------------------------------------------------------------------\n" +
                            "§7Du bist vom §cNetzwerk §7gebannt§8!\n" +
                            "      \n" +
                            "§7Grund§8: §eHacking§8[§eID: " + getID(name) + "§8]\n" +
                            "§7Du wurdest gebannt von§8: §a" + BanAPI.getVonwem(name) + "\n" +
                            "§7Verbleibende Zeit§8: " + getEndAsString(name) + " §8!\n" +
                            "\n" +
                            "§7Du kannst auf unserem §bTeamSpeak §7oder in unserem §bForum §7einen Entbannungsantrag stellen§8!\n" +
                            "\n" +
                            "§8§m------------------------------------------------------------------------");
                } else {
                    MuteAPI.unBanPlayer(name, "System [Automatischer Unban]", "Konsole");
                }
            }



        }



}
