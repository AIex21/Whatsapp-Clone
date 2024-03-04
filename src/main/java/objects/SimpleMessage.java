package objects;

public class SimpleMessage {
	private int id;
    private int senderId;
    private int receiverId;
    private String text;
    private String timestamp;

    public SimpleMessage(int id, int senderId, int receiverId, String text, String timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.timestamp = timestamp; // Use your new method here
    }
}
