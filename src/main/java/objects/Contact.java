package objects;

public class Contact {
	
	private int id;
	private int userId;
	private int contactId;
	
	public Contact() {
		super();
	}

	public Contact(int id, int userId, int contactId) {
		super();
		this.id = id;
		this.userId = userId;
		this.contactId = contactId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", userId=" + userId + ", contactId=" + contactId + "]";
	}
}
