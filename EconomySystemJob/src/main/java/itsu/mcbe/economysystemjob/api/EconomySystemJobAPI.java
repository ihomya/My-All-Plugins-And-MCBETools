package itsu.mcbe.economysystemjob.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import itsu.mcbe.economysystem.api.EconomySystemAPI;

public class EconomySystemJobAPI {

    private SQLiteAPI sql;

    public EconomySystemJobAPI() {
        this.sql = new SQLiteAPI();
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
     * @param jobName 職業名（tree-cutter/tree-planter/miner）
     */
    public boolean createUser(String userName, String jobName) {
        if(existsUser(userName)) return false;
        if(jobName.equals("tree-cutter") || jobName.equals("tree-planter") || jobName.equals("miner")) {
        	sql.createUser(userName, jobName);
            return true;
        }
        return false;
    }

    /**
     * ユーザを削除します。（サードパーティからの呼び出しは非推奨）
     * @param userName ユーザ名（String）
     */
    public void deleteUser(String userName) {
        sql.deleteUser(userName);
    }

    public String getJob(String userName) {
        return sql.getJob(userName);
    }

    public boolean setJob(String userName, String jobName) {
        if(getJob(userName) == null || getJob(userName).equals(jobName)) return false;
        if(jobName.equals("tree-cutter") || jobName.equals("tree-planter") || jobName.equals("miner") || jobName.equals("neet")) {
        	sql.setJob(userName, jobName);
            return true;
        }
        return false;
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
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/EconomySystem/EconomyJobData.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS user ("
                        + "id integer primary key autoincrement, "
                        + "name text not null, "
                        + "job text not null"
                    + ")");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void createUser(String userName, String jobName) {
        try {
            if(existsUser(userName)) return;
            statement.executeUpdate(
                    "INSERT INTO user (name, job) VALUES('"
                        + userName + "', '"
                        + jobName
                    + "')");
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

    public String getJob(String userName) {
        try {
            if(existsUser(userName)) {
                ResultSet rs = statement.executeQuery("SELECT * from user WHERE (name = '"+ userName +"')");
                while(rs.next()) {
                    if(rs.getString("job") != null) return rs.getString("job");
                }
                rs.close();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized void setJob(String userName, String jobName) {
        if(existsUser(userName)){
            try {
                statement.execute("UPDATE user SET job = '" + jobName + "' WHERE name = '" + userName + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

