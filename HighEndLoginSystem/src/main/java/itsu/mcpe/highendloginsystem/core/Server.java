package itsu.mcpe.highendloginsystem.core;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import itsu.mcbe.form.core.NukkitFormAPI;

public class Server {

    private static HighEndLoginSystem plugin;
    private static NukkitFormAPI formAPI;
    private static SQLSystem sql;
    
    private static Map<String, Boolean> loggedInPlayer = new HashMap<>();

    public static void setFormAPI(NukkitFormAPI api) {
        formAPI = api;
    }

    public static void setHighEndLoginSystem(HighEndLoginSystem owner) {
        plugin = owner;
    }

    public static NukkitFormAPI getFormAPI() {
        return formAPI;
    }

    public static HighEndLoginSystem getHighEndLoginSystem() {
        return plugin;
    }

    public static void setSQLSyetem(SQLSystem system) {
        sql = system;
    }

    public static SQLSystem getSQLSystem() {
        return sql;
    }
    
    public static void setLoggedInPlayer(Player player, boolean bool) {
    	loggedInPlayer.put(player.getName(), bool);
    }
    
    public static void removeLoggedInPlayer(Player player) {
    	loggedInPlayer.remove(player.getName());
    }
    
    public static boolean isLoggedIn(Player player) {
    	return loggedInPlayer.get(player.getName()) != null ? loggedInPlayer.get(player.getName()) : false;
    }

}
