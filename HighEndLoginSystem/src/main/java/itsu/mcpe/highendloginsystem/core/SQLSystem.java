package itsu.mcpe.highendloginsystem.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;

public class SQLSystem {

    private Statement statement;

    private Encoder encoder;
    private Decoder decoder;

    public SQLSystem() {
        encoder = Base64.getEncoder();
        decoder = Base64.getDecoder();

        connectSQL();
    }

    public void connectSQL() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:sqlite:" + Server.getHighEndLoginSystem().getDataFolder().toString() + "/LoginData.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate(
                    "CREATE table if not exists player ("
                        + "id integer primary key autoincrement, "
                        + "name text not null, "
                        + "password text not null, "
                        + "mailAddress text not null, "
                        + "ipAddress text not null, "
                        + "clientId text not null, "
                        + "mailPath text not null, "
                        + "autoLogin integer not null, "
                        + "banned integer not null"
                    + ")");
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @SuppressWarnings("deprecation")
    public synchronized void createAccount(Player player, String password, String mailAddress, String mailPath, boolean autoLogin, boolean banned) {
        try {
        	int b = autoLogin ? 0 : 1;
        	int ban = banned ? 0 : 1;
            statement.executeUpdate(
                    "INSERT INTO player(name, password, mailAddress, ipAddress, clientId, mailPath, autoLogin, banned) VALUES('"
                        + player.getName() + "', '"
                        + encoder.encodeToString(password.getBytes()) + "', '"
                        + encoder.encodeToString(mailAddress.getBytes()) + "', '"
                        + player.getAddress() + "', '"
                        + String.valueOf(player.getClientId()) + "', '"
                        + mailPath + "', "
                        + b + ", "
                        + ban
                    + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsAccount(String name) {
        try {
            ResultSet rs = statement.executeQuery("select name from player where name = '"+ name +"'");
            if(rs.getString("name") != null) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public synchronized boolean deleteAccount(String name) {
        try {
            if(existsAccount(name)) statement.executeUpdate("DELETE from player WHERE name = '"+ name + "'");
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Map<String, Object> getAccount(String name) {
        Map<String, Object> list = new HashMap<String, Object>();
        try {
            if(existsAccount(name)) {
                ResultSet rs = statement.executeQuery("SELECT * from player WHERE (name = '"+ name +"')");
                while(rs.next()) {
                    if(rs.getString("id") != null) list.put("id", rs.getInt("id"));
                    if(rs.getString("name") != null) list.put("name", rs.getString("name"));
                    if(rs.getString("password") != null) list.put("password", new String(decoder.decode(rs.getString("password"))));
                    if(rs.getString("mailAddress") != null) list.put("mailAddress", new String(decoder.decode(rs.getString("mailAddress"))));
                    if(rs.getString("ipAddress") != null) list.put("ipAddress", rs.getString("ipAddress"));
                    if(rs.getString("clientId") != null) list.put("clientId", rs.getString("clientId"));
                    if(rs.getString("mailPath") != null) list.put("mailPath", rs.getString("mailPath"));
                    if(rs.getString("autoLogin") != null) list.put("autoLogin", rs.getInt("autoLogin") == 0 ? true : false);
                    if(rs.getString("banned") != null) list.put("banned", rs.getInt("banned") == 0 ? true : false);
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.size() > 0 ? list : null;
    }
    
    public List<Map<String, Object>> getAccounts() {
    	List<Map<String, Object>> result = new ArrayList<>();
    	try {
			ResultSet rs = statement.executeQuery("SELECT * from player");
			
			while(rs.next()) {
	    		Map<String, Object> list = new HashMap<>();
				if(rs.getString("id") != null) list.put("id", rs.getInt("id"));
                if(rs.getString("name") != null) list.put("name", rs.getString("name"));
                if(rs.getString("password") != null) list.put("password", new String(decoder.decode(rs.getString("password"))));
                if(rs.getString("mailAddress") != null) list.put("mailAddress", new String(decoder.decode(rs.getString("mailAddress"))));
                if(rs.getString("ipAddress") != null) list.put("ipAddress", rs.getString("ipAddress"));
                if(rs.getString("clientId") != null) list.put("clientId", rs.getString("clientId"));
                if(rs.getString("mailPath") != null) list.put("mailPath", rs.getString("mailPath"));
                if(rs.getString("autoLogin") != null) list.put("autoLogin", rs.getInt("autoLogin") == 0 ? true : false);
                if(rs.getString("banned") != null) list.put("banned", rs.getInt("banned") == 0 ? true : false);
                result.add(list);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
    	
    }

    public int getId(String name) {
        return (int) getAccount(name).get("id");
    }

    public String getPassword(String name) {
        return (String) getAccount(name).get("password");
    }

    public String getMailAddress(String name) {
        return (String) getAccount(name).get("mailAddress");
    }

    public String getIpAddress(String name) {
        return (String) getAccount(name).get("ipAddress");
    }

    public String getClientId(String name) {
        return (String) getAccount(name).get("clientId");
    }

    public String getMailPath(String name) {
        return (String) getAccount(name).get("mailPath");
    }

    public boolean getAutoLogin(String name) {
        return (boolean) getAccount(name).get("autoLogin");
    }

    public synchronized void updatePassword(String name, String password) {
        if(existsAccount(name)){
            try {
                statement.execute("update player set password = '" + encoder.encodeToString(password.getBytes()) + "' where name = '" + name + "'");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public synchronized void updateMailAddress(String name, String mailAddress) {
        if(existsAccount(name)){
            try {
                statement.execute("update player set mailAddress = '" + encoder.encodeToString(mailAddress.getBytes()) + "' where name = '" + name + "'");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public synchronized void updateIpAddress(String name, String ipAddress) {
        if(existsAccount(name)){
            try {
                statement.execute("update player set ipAddress = '" + ipAddress + "' where name = '" + name + "'");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public synchronized void updateClientID(String name, String clientId) {
        if(existsAccount(name)){
            try {
                statement.execute("update player set clientId = '" + clientId + "' where name = '" + name + "'");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public synchronized void updateMailPath(String name, String mailPath) {
        if(existsAccount(name)){
            try {
                statement.execute("update player set mailPath = '" + mailPath + "' where name = '" + name + "'");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public synchronized void updateAutoLogin(String name, boolean autoLogin) {
        if(existsAccount(name)){
            try {
            	int b = autoLogin ? 0 : 1;
                statement.execute("update player set autoLogin = " + b + " where name = '" + name + "'");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    public boolean isBanned(String name) {
        return (boolean) getAccount(name).get("banned");
    }
    
    public synchronized void setBanned(String name, boolean banned) {
    	if(existsAccount(name)){
            try {
            	int b = banned ? 0 : 1;
                statement.execute("update player set banned = " + b + " where name = '" + name + "'");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
