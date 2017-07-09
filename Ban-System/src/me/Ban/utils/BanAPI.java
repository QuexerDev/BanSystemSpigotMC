package me.Ban.utils;

import me.Ban.MySQL.MySQL;
import me.Ban.main.Main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static me.Ban.main.Main.pf;

/**
 * Created by elija on 22.06.2017.
 */
public class BanAPI {

    public static boolean isBanned(String name) {

        ResultSet rs = MySQL.getResult("SELECT * FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

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
	public static void banPlayer(String name, String grund, String vonWem, long Stundenn) {

        if (!isBanned(name)) {
            long now = System.currentTimeMillis();
            long end = now + Stundenn * 1000 * 60 * 60;
            end = end / 1000;
            if (Stundenn == -1) {
                end = -1;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : HH:mm:ss");


            int ID = getNextID();
            int mutezahl = 0;


            MySQL.update("INSERT INTO Ban(UUID,Grund,von,end,current,ID) VALUES ('" + UUIDFetcher.getUUID(name).toString() + "','" + grund + "','" + vonWem + "','" + end + "','" + dateFormat.toString() + "','" + ID + "')");

            ProxiedPlayer p = BungeeCord.getInstance().getPlayer(vonWem);


            TextComponent text = new TextComponent();
            text.setText(Main.pf + "§7Du hast den Spieler §c" + name + " §7erfolgreich §cgebannt§8! ");
            TextComponent extra = new TextComponent();
            extra.setText("§8§l[§c§lVERSEHEN - UNBAN§8§l]");
            extra.setBold(true);

            extra.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/unban " + name + " Ban Versehen"));
            //text.addExtra(extra);
            if (p != null && p != BungeeCord.getInstance().getConsole()) {
                p.sendMessage(" ");
                p.sendMessage(text);
                p.sendMessage(" ");
            }


            for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                if (all.hasPermission("ban.ban")) {
                    all.sendMessage(" ");
                    all.sendMessage(Main.pf + "§7Der Spieler §c" + name + " §7wurde von §a" + vonWem + " §7mit dem Grund§8: §e" + grund + " §7gebannt§8!");
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
                String rrmaining = "§c" + Tage + " §7Tag§8(§7e§8) §c" + Stunden +
			/* 177 */" §7Stunde§8(§7n§8) §c" + Minuten + " §7Minute§8(§7n§8) §c" + Sekunden
                        + " §7Sekunde§8(§7n§8)";






                BungeeCord.getInstance().getPlayer(name).disconnect(  "§8§m------------------------------------------------------------------------\n" +
                                "§7Du wurdest vom §cNetzwerk §7gebannt§8!\n" +
                                "      \n" +
                                "§7Grund§8: §eHacking§8[§eID: " + getID(name) + "§8]\n" +
                                "§7Du wurdest gebannt von§8: §a" + vonWem + "\n" +
                                "§7Verbleibende Zeit§8: " + rrmaining + " §8!\n" +
                                "§7Du kannst in unserem §bForum §7einen Entbannungsantrag stellen§8!\n" +
                                "\n" +
                                "§8§m------------------------------------------------------------------------");

            }


        } else {
            ProxiedPlayer p = BungeeCord.getInstance().getPlayer(vonWem);
            TextComponent text = new TextComponent();
            text.setText(Main.pf + "Â§cDieser Spieler ist bereits gebannt! ");
            TextComponent extra = new TextComponent();
            extra.setText("§8§l[§c§lUNBAN§8§l]");
            extra.setBold(true);

            extra.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/unban " + name + " Ban Versehen"));
            //text.addExtra(extra);
            p.sendMessage(" ");
            p.sendMessage(text);
            p.sendMessage(" ");
        }


    }

    public static void unBanPlayer(String name, String grund, String vonwem) {
        MySQL.update("DELETE FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");
        for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
            if (all.hasPermission("ban.ban")) {
                all.sendMessage(" ");
                all.sendMessage(Main.pf + "§7Der Spieler §c" + name + " §7wurde von §c" + vonwem + " §7mit dem Grund §e" + grund + " §7entbannt§8!");
                all.sendMessage(" ");
            }
        }
    }

    public static long getEnd(String name) {
        ResultSet rs = MySQL.getResult("SELECT * FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

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
        ResultSet rs = MySQL.getResult("SELECT * FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

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
        ResultSet rs = MySQL.getResult("SELECT * FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

        try {
            if (rs.next()) {
                return rs.getString("Grund");
            } else {
                return "§cNicht Gebannt";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cNicht Gebannt";
    }

    public static String getVonwem(String name) {
        ResultSet rs = MySQL.getResult("SELECT * FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

        try {
            if (rs.next()) {
                return rs.getString("von");
            } else {
                return "§cNicht Gebannt";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cNicht Gebannt";

    }

    public static String getBannDatum(String name) {
        ResultSet rs = MySQL.getResult("SELECT * FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

        try {
            if (rs.next()) {
                return rs.getString("current");
            } else {
                return "§cNicht Gebannt";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cNicht Gebannt";

    }

    public static int getIP(String name) {
        ResultSet rs = MySQL.getResult("SELECT * FROM Ban WHERE UUID='" + UUIDFetcher.getUUID(name) + "'");

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

        if (isBanned(name)) {
            Date date = new Date();
            date.setTime(getEnd(name) - System.currentTimeMillis());
            if (getEnd(name) == -1) {
                return "§4Â§lPERMANENT";
            } else {

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
        }
        }

        return "Â§cNicht Gebannt!";


    }
    public static int getNextID() {
        int i = 0;

        ResultSet rs = MySQL.getResult("SELECT * FROM Ban");

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
