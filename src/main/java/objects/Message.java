package objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

    private int id;
    private int senderId;
    private int receiverId;
    private String text;
    private LocalDateTime timestamp;

    public Message() {
    }

    public Message(int id, int senderId, int receiverId, String text, LocalDateTime timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

	@Override
	public String toString() {
		return "Message [id=" + id + ", senderId=" + senderId + ", receiverId=" + receiverId + ", text=" + text
				+ ", timestamp=" + timestamp + "]";
	}
    
	public String getTimestampString() {
    	return this.timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
