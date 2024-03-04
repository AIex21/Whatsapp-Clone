package database.databaseAccess;
import java.util.ResourceBundle;

public class DatabaseConfig {

    private static ResourceBundle rb = ResourceBundle.getBundle("database.databaseAccess.DatabaseConfig");

    public static String getDriver() {
        return rb.getString("driver");
    }

    public static String getUrl() {
        return rb.getString("url");
    }

    public static String getUser() {
        return rb.getString("user");
    }

    public static String getPassword() {
        return rb.getString("password");
    }
}
