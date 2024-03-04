package database.jdbc;

import objects.User;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;

public class UserOper {

    public static User addUser(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dbHelper.getConnection(conn);
            String sql = "INSERT INTO user (username, passwordHash, salt, email) VALUES "
                    + "( ?, ?, ?, ?);";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setBlob(3, new SerialBlob(user.getSalt()));
            stmt.setString(4, user.getEmail());
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId((int) rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeAll(conn, stmt, rs);
        }
        return user;
    }

    public static User getUser(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = dbHelper.getConnection(conn);
            String sql = "SELECT * FROM user WHERE email =" + " ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            while(rs.next()) {
                user = new User(rs.getInt("ID"), rs.getString("username"), rs.getString("passwordHash"), rs.getBytes("salt"), rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeAll(conn, stmt);
        }
        return user;
    }
    
    public static User getUser(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = dbHelper.getConnection(conn);
            String sql = "SELECT * FROM user WHERE id =" + " ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()) {
                user = new User(rs.getInt("ID"), rs.getString("username"), rs.getString("passwordHash"), rs.getBytes("salt"), rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeAll(conn, stmt);
        }
        return user;
    }
    
    public static boolean checkEmail(String email) {
    	User user = getUser(email);
    	return user == null;
    }
}
