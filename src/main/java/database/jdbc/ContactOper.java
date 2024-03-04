package database.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import objects.Contact;

public class ContactOper {
	
	public static Contact addContact(Contact contact) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dbHelper.getConnection(conn);
            String sql = "INSERT INTO contact (userId, contactId) VALUES "
                    + "( ?, ?);";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, contact.getUserId());
            stmt.setInt(2, contact.getContactId());
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                contact.setId((int) rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeAll(conn, stmt, rs);
        }
        return contact;
    }
	
	public static Contact getContact(int userId, int contactId) {
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Contact contact = null;

        try {
            conn = dbHelper.getConnection(conn);
            String sql = "SELECT * FROM contact WHERE userId =" + " ? " + "AND contactId =" + " ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, contactId);
            rs = stmt.executeQuery();
            while(rs.next()) {
                contact = new Contact(rs.getInt("id"), rs.getInt("userId"), rs.getInt("contactId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeAll(conn, stmt);
        }
        return contact;
	}
    
    public static List<Contact> getContacts(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Contact> contacts = new ArrayList<Contact>();

        try {
            conn = dbHelper.getConnection(conn);
            String sql = "SELECT * FROM contact WHERE userId =" + " ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            while(rs.next()) {
                Contact contact = new Contact(rs.getInt("id"), rs.getInt("userId"), rs.getInt("contactId"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeAll(conn, stmt);
        }
        return contacts;
    }
}
