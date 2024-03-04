package database.jdbc;

import java.util.ArrayList;	
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import objects.Message;
import objects.SimpleMessage;

public class MessageOper {
	
    public static Message addMessage(Message message) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dbHelper.getConnection(conn);
            String sql = "INSERT INTO message (senderId, receiverId, text, timestamp) VALUES "
                    + "( ?, ?, ?, ?);";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, message.getSenderId());
            stmt.setInt(2, message.getReceiverId());
            stmt.setString(3, message.getText());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                message.setId((int) rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeAll(conn, stmt, rs);
        }
        return message;
    }
    
    public static List<Message> getMessages(int senderId, int receiverId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Message> messages = new ArrayList<Message>();

        try {
            conn = dbHelper.getConnection(conn);
            String sql = "SELECT * FROM message WHERE (senderId =" + " ? " +
            		"AND receiverId =" + " ? " + ") OR (senderId =" + " ? " + 
            		"AND receiverId =" + " ? " + ")";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setInt(3, receiverId);
            stmt.setInt(4, senderId);
            rs = stmt.executeQuery();
            while(rs.next()) {
                Message message = new Message(rs.getInt("id"), rs.getInt("senderId"), rs.getInt("receiverId"), rs.getString("text"), rs.getTimestamp("timestamp").toLocalDateTime());
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeAll(conn, stmt);
        }
        return messages;
    }
    
    public static List<SimpleMessage> getSimpleMessages(int senderId, int receiverId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<SimpleMessage> messages = new ArrayList<SimpleMessage>();

        try {
            conn = dbHelper.getConnection(conn);
            String sql = "SELECT * FROM message WHERE (senderId =" + " ? " +
            		"AND receiverId =" + " ? " + ") OR (senderId =" + " ? " + 
            		"AND receiverId =" + " ? " + ")";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setInt(3, receiverId);
            stmt.setInt(4, senderId);
            rs = stmt.executeQuery();
            while(rs.next()) {
                SimpleMessage message = new SimpleMessage(rs.getInt("id"), rs.getInt("senderId"), rs.getInt("receiverId"), rs.getString("text"), getTimestampString(rs.getTimestamp("timestamp").toLocalDateTime()));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeAll(conn, stmt);
        }
        return messages;
    }
    
    private static String getTimestampString(LocalDateTime timestamp) {
    	return timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
