package itsu.mcbe.economysystem.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EconomySystemAPI {
	
	private SQLiteAPI sql;
	private String unit;
	private int defaultMoney;
	
	public EconomySystemAPI(String unit, int defaultMoney) {
		this.sql = new SQLiteAPI();
		this.unit = unit;
		this.defaultMoney = defaultMoney;
	}
	
	/**
	 * ユーザが登録されているか取得します。
	 * @param userName ユーザ名（String）
	 * @return boolean
	 */
	public boolean existsUser(String userName) {
		return sql.existsUser(userName);
	}
	
	/**
	 * ユーザを新規作成します。（サードパーティからの呼び出しは非推奨）
	 * @param userName ユーザ名（String）
	 */
	public void createUser(String userName) {
		sql.createUser(userName, defaultMoney);
	}
	
	/**
	 * ユーザを削除します。（サードパーティからの呼び出しは非推奨）
	 * @param userName ユーザ名（String）
	 */
	public void deleteUser(String userName) {
		sql.deleteUser(userName);
	}
	
	/**
	 * ユーザの所持金を取得します。
	 * @param userName ユーザ名（String）
	 * @return ユーザの所持金（int）
	 */
	public int getMoney(String userName) {
		return sql.getMoney(userName);
	}
	
	/**
	 * ユーザの所持金を設定します。
	 * @param userName ユーザ名（String）
	 * @param value 設定する値（int）
	 * @return 設定した後の金額（int）
	 */
	public int setMoney(String userName, int value) {
		sql.setMoney(userName, value);
		return sql.getMoney(userName);
	}
	
	/**
	 * ユーザの所持金を増やします。
	 * @param userName ユーザ名（String）
	 * @param value 増やす金額（int）
	 * @return 増やした後の金額（int）
	 */
	public int increaseMoney(String userName, int value) {
		if(value < 0) return -1;
		sql.setMoney(userName, getMoney(userName) + value);
		return sql.getMoney(userName);
	}
	
	/**
	 * ユーザの所持金を減らします。
	 * @param userName ユーザ名（String）
	 * @param value 減らす金額（int）
	 * @return 減らした後の金額（int）
	 */
	public int decreaseMoney(String userName, int value) {
		if(value < 0) return -1;
		if(sql.getMoney(userName) - value < 0) return -1;
		setMoney(userName, sql.getMoney(userName) - value);
		return sql.getMoney(userName);
	}
	
	/**
	 * 経済システムの単位を取得します。
	 * @return 単位（String）
	 */
	public String getUnit() {
		return unit;
	}
	
	/**
	 * デフォルトで設定される金額を取得します。
	 * @return デフォルトの金額（int）
	 */
	public int getDefaultMoney() {
		return defaultMoney;
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
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/EconomySystem/EconomyData.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS user ("
                        + "id integer primary key autoincrement, "
                        + "name text not null, "
                        + "money integer not null"
                    + ")");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void createUser(String userName, int defaultMoney) {
        try {
            statement.executeUpdate(
                    "INSERT INTO user (name, money) VALUES('"
                        + userName + "', "
                        + defaultMoney
                    + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsUser(String userName) {
        try {
            return statement.executeQuery("SELECT name FROM user WHERE name = '"+ userName +"'").getString("name") != null ? true : false;
        } catch (SQLException e) {
            return false;
        }
    }

    public synchronized boolean deleteUser(String userName) {
        try {
            if(existsUser(userName)) statement.executeUpdate("DELETE from user WHERE name = '"+ userName + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getMoney(String userName) {
        try {
            if(existsUser(userName)) {
                ResultSet rs = statement.executeQuery("SELECT * from user WHERE (name = '"+ userName +"')");
                while(rs.next()) {
                    if(rs.getInt("money") != 0) return rs.getInt("money");
                }
                rs.close();
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public synchronized void setMoney(String userName, int value) {
        if(existsUser(userName)){
            try {
                statement.execute("UPDATE user SET money = " + value + " WHERE name = '" + userName + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
