package me.Ban.main;

import me.Ban.MySQL.MySQL;
import me.Ban.commands.Command_Ban;
import me.Ban.commands.Command_Check;
import me.Ban.commands.Command_Unban;
import me.Ban.commands.Commands_Reasons;
import me.Ban.listeners.BanListeners;
import me.Ban.listeners.MuteListeners;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by elija on 22.06.2017.
 */
public class Main extends Plugin{

    public static String pf;
    public static File file = new File("plugins//BanSystemByQuexer", "config.yml");
    public static File ordner = new File("plugins//BanSystemByQuexe");
    public static Configuration cfg;


    @Override
    public void onEnable() {

        if(!file.exists()) {
            try {
                file.createNewFile();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!ordner.exists()) {

                ordner.mkdir();

        }



        try {
            cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        loadConfig();

        

        MySQL.Connect();
        MySQL.CreateTable();

        BungeeCord.getInstance().getPluginManager().registerListener(this, new BanListeners());
        BungeeCord.getInstance().getPluginManager().registerListener(this, new MuteListeners());
        getProxy().getPluginManager().registerCommand(this, new Command_Check("check"));
        getProxy().getPluginManager().registerCommand(this, new Command_Ban("ban"));
        getProxy().getPluginManager().registerCommand(this, new Command_Unban("unban"));
        getProxy().getPluginManager().registerCommand(this, new Commands_Reasons("reasons"));
    }
    public void loadConfig() {
        if(getConfig().get("Settings.prefix") != null) {
            pf = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Settings.prefix"));
        } else {
            getConfig().set("Settings.prefix", "&8[&cBanSystem&8] ");
        }

        if(getConfig().get("MySQL.username") != null) {
        	 MySQL.username = getConfig().getString("MySQL.username");
             MySQL.password = getConfig().getString("MySQL.password");
             MySQL.database = getConfig().getString("MySQL.database");
             MySQL.host = getConfig().getString("MySQL.host");
             MySQL.port = getConfig().getString("MySQL.port");
        } else {
            getConfig().set("MySQL.username", "root");
            getConfig().set("MySQL.password", "password");
            getConfig().set("MySQL.database", "database");
            getConfig().set("MySQL.host", "host");
            getConfig().set("MySQL.port", "port");
        }






    }
    public static void saveConfig(){
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg,file);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static Configuration getConfig() {
        return cfg;
    }
}
