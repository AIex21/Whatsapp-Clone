package database.jdbc;
import database.databaseAccess.DatabaseConfig;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Hashtable;

public class dbHelper {
    static final String JDBC_DRIVER = DatabaseConfig.getDriver();
    static final String DB_URL = DatabaseConfig.getUrl();
    // Database credentials
    static final String USER = DatabaseConfig.getUser();
    static final String PASS = DatabaseConfig.getPassword();

    public static Connection getConnection(Connection conn) {
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection getConnectionDataSource(Connection conn) {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
            InitialContext ctx = new InitialContext(env);
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/TestDB");
            conn = ds.getConnection();
        } catch(NamingException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            // nothing we can do
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeAll (Connection conn, Statement stmt) {
        closeAll(conn, stmt, null);
    }
}
