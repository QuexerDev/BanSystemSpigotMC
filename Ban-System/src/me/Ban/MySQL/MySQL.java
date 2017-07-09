package me.Ban.MySQL;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by elija on 11.06.2017.
 */
public class MySQL {

    public static String username;
    public static String password;
    public static String database;
    public static String host;
    public static String port;
    public static Connection con;

    public static void Connect(){
        if(!isConnected()){
            try {
                con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, username, password);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public static void close(){
        if(isConnected()){

            try {
                con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }

    public static void update(String qry){
        if(isConnected()){
            try {
                con.createStatement().executeUpdate(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static ResultSet getResult(String qry){
        if(isConnected()){
            try {
                return con.createStatement().executeQuery(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;


    }

    public static boolean isConnected(){
        return con != null;

    }
    public static void CreateTable(){
        if(isConnected()){
            try {

                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS Ban(UUID VARCHAR(100), Grund VARCHAR(100),von VARCHAR(100), end VARCHAR(1000), current VARCHAR(100), ID INT(100), IP VARCHAR(100))");
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS Mute(UUID VARCHAR(100), Grund VARCHAR(100), von VARCHAR(100), end VARCHAR(1000), current VARCHAR(100), ID INT(100), IP VARCHAR(100))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

}
