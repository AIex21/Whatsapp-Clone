package objects;

import java.util.Arrays;
import java.util.Objects;

public class User {

    private int id;
    private String username;
    private String passwordHash;
    private byte[] salt;
    private String email;

    public User() {
    }

    public User(int id, String username, String passwordHash, byte[] salt, String email) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", salt=" + Arrays.toString(salt) +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(passwordHash, user.passwordHash) && Arrays.equals(salt, user.salt) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, passwordHash, email);
        result = 31 * result + Arrays.hashCode(salt);
        return result;
    }
}
