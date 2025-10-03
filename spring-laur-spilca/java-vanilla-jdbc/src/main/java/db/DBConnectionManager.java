package db;

import exceptions.DBConnectionFailedException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static connections.ConnectionStrings.*;

// SINGLETON DP
public class DBConnectionManager {

    private static final class SingletonHolder {
        private static final DBConnectionManager SINGLETON = new DBConnectionManager();
    }

    private DBConnectionManager() {
    }

    public static DBConnectionManager getInstance() {
        return SingletonHolder.SINGLETON;
    }

    public Connection getConnection() {
        try {

            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            con.setAutoCommit(false);
            return con;

        } catch (SQLException throwable) {
            throw new DBConnectionFailedException();
        }
    }
}
