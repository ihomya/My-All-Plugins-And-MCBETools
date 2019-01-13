package itsu.mcbe.economysystemland.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Server;
import cn.nukkit.utils.TextFormat;
import itsu.mcbe.economysystem.api.EconomySystemAPI;

public class EconomySystemLandAPI {
	
	public static int LAND_VALUE = 100;

    private SQLiteAPI sql;
    private EconomySystemAPI api;

    public EconomySystemLandAPI(EconomySystemAPI api) {
    	this.api = api;
        this.sql = new SQLiteAPI();
    }

    public boolean existsLand(int x, int z, String world) {
        return sql.existsLand(x, z, world);
    }

    public void createLand(int startX, int startZ, int endX, int endZ, int size, String world, String name) {
        if(sql.createLand(startX, startZ, endX, endZ, size, world, name)) {
        	if(api.getMoney(name) > size * LAND_VALUE) {
        		api.decreaseMoney(name, size * LAND_VALUE);
        		Server.getInstance().getPlayer(name).sendMessage(TextFormat.GREEN + "土地を購入しました。（価格: " + size * LAND_VALUE + api.getUnit() + "）");
        		
        	} else {
        		Server.getInstance().getPlayer(name).sendMessage(TextFormat.RED + "所持金が足りません。（所持金: " + api.getMoney(name) + api.getUnit() + "）");
        	}
        	
        } else {
        	Server.getInstance().getPlayer(name).sendMessage(TextFormat.RED + "この土地は既に購入されています。");
        }
    }
    
    public void deleteLand(int id, String name) {
    	ProtectedLand land = null;
    	if((land = getLandById(id)) != null) {
	    	if(sql.deleteLand(id, name)) {
	    		Server.getInstance().getPlayer(name).sendMessage(TextFormat.GREEN + "土地を売却しました。（売価: " + land.getSize() * LAND_VALUE / 2 + api.getUnit() + "）");
	    		return;
	    	}
    	}
    	Server.getInstance().getPlayer(name).sendMessage(TextFormat.RED + "土地が存在しません。");
    }
    
    public boolean isEditable(int x, int y, String world, String name) {
    	ProtectedLand land = getLand(x, y, world);
    	if(land == null) return true;
    	return land.getOwner().equals(name) || land.getInvite().contains(name) ? true : false;
    }
    
    public boolean inviteLand(int id, String name) {
    	return sql.inviteLand(id, name);
    }
    
    public boolean unInviteLand(int id, String name) {
    	return sql.unInviteLand(id, name);
    }
    
    public List<ProtectedLand> getPlayerLands(String name) {
    	return sql.getPlayerLands(name);
    }
    
    public List<ProtectedLand> getWorldLands(String world) {
    	return sql.getWorldLands(world);
    }

    public ProtectedLand getLand(int x, int z, String world) {
        return sql.getLand(x, z, world);
    }

    public ProtectedLand getLandById(int id) {
        return sql.getLandById(id);
    }
}

class SQLiteAPI {

    private Statement statement;

    public SQLiteAPI() {
        connectSQL();
    }

    public void connectSQL() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/EconomySystem/EconomyLandData.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS land ("
                        + "id integer primary key autoincrement, "
                        + "startx integer not null, "
                        + "startz integer not null, "
                        + "endx integer not null, "
                        + "endz integer not null, "
                        + "size integer not null, "
                        + "world text not null, "
                        + "owner text not null, "
                        + "invite text not null"
                    + ")");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean createLand(int startX, int startZ, int endX, int endZ, int size, String world, String name) {
        try {
        	statement.executeUpdate(
                    "INSERT INTO land (startx, startz, endx, endz, size, world, owner, invite) VALUES("
                        + startX + ", "
                        + startZ + ", "
                        + endX + ", "
                        + endZ + ", "
                        + size + ", '"
                        + world + "', '"
                        + name + "', '"
                        + "NO_INVITED_PLAYERS'"
                    + ")");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existsLand(int x, int z, String world) {
        return getLand(x, z, world) != null ? true : false;
    }

    public synchronized boolean deleteLand(int id, String name) {
    	ProtectedLand land = null;
        try {
        	if((land = getLandById(id)) != null) {
        		if(land.getOwner().equals(name)) {
        			statement.executeUpdate("DELETE from land WHERE id = "+ id);
        			return true;
        		}
        	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return false;
    }

    public ProtectedLand getLand(int x, int z, String world) {
    	try {
        	Connection connection = DriverManager.getConnection("jdbc:sqlite:plugins/EconomySystem/EconomyLandData.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            int id = -1;
        	ResultSet rs = statement.executeQuery("SELECT id FROM land WHERE (startx <= "+ x + " and endx >= " + x + ") AND  (startz <= " + z + " AND endz >= " + z + ") AND world = '" + world + "'");
        	
        	while(rs.next()) {
                id = rs.getInt("id");
            }
        	return id != -1 ? getLandById(id) : null;
        } catch (SQLException e) {
        	e.printStackTrace();
            return null;
        }
    }
    
    public ProtectedLand getLandById(int landId) {
    	try {
        	Connection connection = DriverManager.getConnection("jdbc:sqlite:plugins/EconomySystem/EconomyLandData.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            int id = 0, startX = 0, startZ = 0, endX = 0, endZ = 0, size = 0;
            String worldName = null, owner = null;
            List<String> invite = new ArrayList<>();
        	ResultSet rs = statement.executeQuery("SELECT id, startx, startz, endx, endz, size, world, owner, invite FROM land WHERE id = " + landId);
        	
        	while(rs.next()) {
                id = rs.getInt("id");
                startX = rs.getInt("startx");
                startZ = rs.getInt("startz");
                endX = rs.getInt("endx");
                endZ = rs.getInt("endz");
                size = rs.getInt("size");
                worldName = rs.getString("world");
                owner = rs.getString("owner");
                
                if(rs.getString("invite").equals("NO_INVITED_PLAYERS")) {
                	invite = Collections.emptyList();
                } else {
                	invite = Arrays.asList(rs.getString("invite").split("::"));
                }
            }
        	
        	return new ProtectedLand(id, startX, startZ, endX, endZ, size, worldName, owner, invite);
        } catch (SQLException e) {
        	e.printStackTrace();
            return null;
        }
    }
    
    public List<ProtectedLand> getPlayerLands(String name) {
    	try {
        	Connection connection = DriverManager.getConnection("jdbc:sqlite:plugins/EconomySystem/EconomyLandData.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            List<ProtectedLand> lands = new ArrayList<>();
            List<Integer> ids = new ArrayList<>();
        	ResultSet rs = statement.executeQuery("SELECT id FROM land WHERE owner = '" + name + "'");
        	
        	while(rs.next()) {
                ids.add(rs.getInt("id"));
            }
        	
        	ids.stream().forEach(id -> lands.add(getLandById(id)));
        	return lands;
        } catch (SQLException e) {
        	e.printStackTrace();
            return null;
        }
    }
    
    public List<ProtectedLand> getWorldLands(String world) {
    	try {
        	Connection connection = DriverManager.getConnection("jdbc:sqlite:plugins/EconomySystem/EconomyLandData.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            List<ProtectedLand> lands = new ArrayList<>();
            List<Integer> ids = new ArrayList<>();
        	ResultSet rs = statement.executeQuery("SELECT id FROM land WHERE world = '" + world + "'");
        	
        	while(rs.next()) {
                ids.add(rs.getInt("id"));
            }
        	
        	ids.stream().forEach(id -> lands.add(getLandById(id)));
        	return lands;
        } catch (SQLException e) {
        	e.printStackTrace();
            return null;
        }
    }
    
    public boolean inviteLand(int id, String name) {
    	ProtectedLand land = getLandById(id);
    	if(land == null) return false;
    	
    	if(land.getOwner().equals(name)) return false;
    	if(land.getInvite().contains(name)) return false;
    	
    	if(land.getInvite().size() == 0) {
    		try {
                statement.execute("UPDATE land SET invite = '" + name + "'");
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    		
    	} else {
    		String members = "";
    		
    		for(String str : land.getInvite()) {
    			members += "::" + str;
    		}
    		
    		members = members.substring(2) + "::" + name;
    		
    		try {
                statement.execute("UPDATE land SET invite = '" + members + "'");
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    	}
    	return true;
    }
    
    public boolean unInviteLand(int id, String name) {
    	ProtectedLand land = getLandById(id);
    	if(land == null) return false;
    	
    	if(land.getOwner().equals(name)) return false;
    	if(!land.getInvite().contains(name)) return false;
    	if(land.getInvite().size() == 0) return false;
    	
    	String members = "";
    	List<String> mem = land.getInvite();
    	mem.remove(name);
    	
    	if(mem.size() == 0) mem.add("NO_INVITED_PLAYERS");
		
		for(String str : mem) {
			members += "::" + str;
		}
		
		members = members.substring(2);
		
		try {
            statement.execute("UPDATE land SET invite = '" + members + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    	return true;
    }
    
    public void printAllData() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/EconomySystem/EconomyLandData.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * from land");
            while(rs.next()) {
                System.out.println("-----------------------");
                System.out.println("id = " + rs.getInt("id"));
                System.out.println("owner = " + rs.getString("owner"));
                System.out.println("startx = " + rs.getInt("startx"));
                System.out.println("startz = " + rs.getInt("startz"));
                System.out.println("endx = " + rs.getInt("endx"));
                System.out.println("endz = " + rs.getInt("endz"));
                System.out.println("size = " + rs.getInt("size"));
                System.out.println("world = " + rs.getString("world"));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
