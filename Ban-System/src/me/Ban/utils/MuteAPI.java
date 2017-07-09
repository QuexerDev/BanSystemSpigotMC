package me.Ban.utils;

import me.Ban.MySQL.MySQL;
import me.Ban.main.Main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by elija on 24.06.2017.
 */
public class MuteAPI {

    public static boolean isMuted(String name) {

        ResultSet rs = MySQL.getResult("SELECT * FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

        try {
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;


    }


    @SuppressWarnings("deprecation")
	public static void mutePlayer(String name, String grund, String vonWem, long Stundenn) {

        if (!isMuted(name)) {
            long now = System.currentTimeMillis();
            long end = now + Stundenn * 1000 * 60 * 60;
            end = end/1000;
            if(Stundenn == -1) {
                end = -1;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : HH:mm:ss");
            String current = dateFormat.toString();
            int ID = getNextID();
            int mutezahl = 0;


            MySQL.update("INSERT INTO Mute(UUID, Grund, von, end, current, ID) VALUES ('"+UUIDFetcher.getUUID(name).toString()+"','"+grund+"','"+vonWem+"','"+end+"','"+current+"','"+ID+"')");

            ProxiedPlayer p = BungeeCord.getInstance().getPlayer(vonWem);



            TextComponent text = new TextComponent();
            text.setText(Main.pf+"§7Du hast den Spieler §c"+name+" §7erfolgreich §cgemuted§8! ");
            TextComponent extra = new TextComponent();
            extra.setText("§8§l[§c§lVERSEHEN - UNMUTE§8§l]");
            extra.setBold(true);

            extra.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/unban" + name+" Mute Versehen"));
            p.sendMessage(" ");
           // text.addExtra(extra);
            if (p != null && p != BungeeCord.getInstance().getConsole()) {
                p.sendMessage(text);
            }
            p.sendMessage(" ");

            


            for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                if(all.hasPermission("ban.mute")) {
                    all.sendMessage(" ");
                    all.sendMessage(Main.pf+"§7Der Spieler §c"+name+" §7wurde von §a"+vonWem+" §7mit dem Grund§8: §e"+grund+" §7gemuted§8!");
                    all.sendMessage(" ");

                }
            }
            if (BungeeCord.getInstance().getPlayer(name) != null) {

                int Sekunden = 0;
			/* 156 */
                int Minuten = 0;
			/* 157 */
                int Stunden = 0;
			/* 158 */
                int Tage = 0;
                long difference = getEnd(name) - System.currentTimeMillis();
			/* 159 */
                while (difference >= 1000L) {
				/* 160 */
                    difference -= 1000L;
				/* 161 */
                    Sekunden++;
				/*     */
                }
			/* 163 */
                while (Sekunden >= 60) {
				/* 164 */
                    Sekunden -= 60;
				/* 165 */
                    Minuten++;
				/*     */
                }
			/* 167 */
                while (Minuten >= 60) {
				/* 168 */
                    Minuten -= 60;
				/* 169 */
                    Stunden++;
				/*     */
                }
			/* 171 */
                while (Stunden >= 24) {
				/* 172 */
                    Stunden -= 24;
				/* 173 */
                    Tage++;
				/*     */
                }
			/*     */
			/* 176 */

			/*     */
                String remaining = "§c" + Tage + " §7Tag§8(§7e§8) §c" + Stunden +
			/* 177 */" §7Stunde§8(§7n§8) §c" + Minuten + " §7Minute§8(§7n§8) §c" + Sekunden
                        + " §7Sekunde§8(§7n§8)";


                BungeeCord.getInstance().getPlayer(name).sendMessage(" ");
                BungeeCord.getInstance().getPlayer(name).sendMessage(Main.pf+"§8§m------------------------------");
                BungeeCord.getInstance().getPlayer(name).sendMessage(Main.pf+"§7Du wurdest Gemuted§8!");
                BungeeCord.getInstance().getPlayer(name).sendMessage(Main.pf+"§7Grund§8: §e"+grund+"§8[§eID: " + getID(name) + "§8]");
                BungeeCord.getInstance().getPlayer(name).sendMessage(Main.pf+"§7Gemuted von§8: §a"+vonWem);
                BungeeCord.getInstance().getPlayer(name).sendMessage(Main.pf+"§7Verbleibende Zeit§8: "+remaining);
                BungeeCord.getInstance().getPlayer(name).sendMessage(Main.pf+"§8§m------------------------------");
                BungeeCord.getInstance().getPlayer(name).sendMessage(" ");

            }




        } else {
            ProxiedPlayer p = BungeeCord.getInstance().getPlayer(vonWem);
            TextComponent text = new TextComponent();
            text.setText(Main.pf+"§cDieser Spieler ist bereits gemuted! ");
            TextComponent extra = new TextComponent();
            extra.setText("§8§l[§c§lUNMUTE§8§l]");
            extra.setBold(true);

            extra.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/unban "+name+" Mute Versehen"));
            //text.addExtra(extra);
            BungeeCord.getInstance().getPlayer(name).sendMessage(" ");
            p.sendMessage(text);
            BungeeCord.getInstance().getPlayer(name).sendMessage(" ");
        }


    }

    public static void unBanPlayer(String name, String grund, String vonwem) {
        MySQL.update("DELETE FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");
        for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
            if(all.hasPermission("ban.mute")) {
                all.sendMessage(" ");
                all.sendMessage(Main.pf + "§7Der Spieler §c" + name + " §7wurde von §c" + vonwem + " §7mit dem Grund §e" + grund + " §7entmuted§8!");
                all.sendMessage(" ");
            }
        }
    }

    public static long getEnd(String name) {
        ResultSet rs = MySQL.getResult("SELECT * FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

        try {
            if (rs.next()) {
                if(rs.getLong("end") == -1) {
                   return -1;
                } else {
                    return rs.getLong("end") * 1000;
                }
                } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getID(String name) {
        ResultSet rs = MySQL.getResult("SELECT * FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

        try {
            if (rs.next()) {
                return rs.getInt("ID");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getGrund(String name) {
        ResultSet rs = MySQL.getResult("SELECT * FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

        try {
            if (rs.next()) {
                return rs.getString("Grund");
            } else {
                return "§cNicht Gemutet";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cNicht Gemutet";
    }

    public static String getVonwem(String name) {
        ResultSet rs = MySQL.getResult("SELECT * FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

        try {
            if (rs.next()) {
                return rs.getString("von");
            } else {
                return "§cNicht Gemutet";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cNicht Gemutet";

    }

    public static String getBannDatum(String name) {
        ResultSet rs = MySQL.getResult("SELECT * FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

        try {
            if (rs.next()) {
                return rs.getString("current");
            } else {
                return "§cNicht Gemutet";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cNicht Gemutet";

    }

    public static int getIP(String name) {
        ResultSet rs = MySQL.getResult("SELECT * FROM Mute WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

        try {
            if (rs.next()) {
                return rs.getInt("IP");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getEndAsString(String name) {

        if (isMuted(name)) {
            Date date = new Date();
            date.setTime(getEnd(name));
            if(getEnd(name) == -1) {
                return "§4§lPERMANENT";
            }
            int Sekunden = 0;
			/* 156 */
            int Minuten = 0;
			/* 157 */
            int Stunden = 0;
			/* 158 */
            int Tage = 0;
            long difference = getEnd(name) - System.currentTimeMillis();
			/* 159 */
            while (difference >= 1000L) {
				/* 160 */
                difference -= 1000L;
				/* 161 */
                Sekunden++;
				/*     */
            }
			/* 163 */
            while (Sekunden >= 60) {
				/* 164 */
                Sekunden -= 60;
				/* 165 */
                Minuten++;
				/*     */
            }
			/* 167 */
            while (Minuten >= 60) {
				/* 168 */
                Minuten -= 60;
				/* 169 */
                Stunden++;
				/*     */
            }
			/* 171 */
            while (Stunden >= 24) {
				/* 172 */
                Stunden -= 24;
				/* 173 */
                Tage++;
				/*     */
            }
			/*     */
			/* 176 */

			/*     */
            return "§c" + Tage + " §7Tag§8(§7e§8) §c" + Stunden +
        			/* 177 */" §7Stunde§8(§7n§8) §c" + Minuten + " §7Minute§8(§7n§8) §c" + Sekunden
                    + " §7Sekunde§8(§7n§8)";
        } else {
            return "§cNicht Gemutet";


        }


    }
    public static int getNextID() {
        int i = 0;

        ResultSet rs = MySQL.getResult("SELECT * FROM Mute");

        try {
            while (rs.next()) {
                i++;
            }

            return i +1;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
