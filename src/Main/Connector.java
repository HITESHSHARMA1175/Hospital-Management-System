package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Muhammad Ali
 */
public class Connector {

    public static void main(String[] args) {
        ConnectDb();
    }

    public static Connection ConnectDb() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String username = getSetting("HOSPITAL_DB_USER", "hospital.db.user", "root");
            String password = getSetting("HOSPITAL_DB_PASSWORD", "hospital.db.password", null);
            if (password == null) {
                throw new SQLException(
                        "Set HOSPITAL_DB_PASSWORD or the hospital.db.password system property before starting the application.");
            }
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/hospital?useSSL=false&serverTimezone=UTC",
                    username,
                    password);
            return connection;
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Unable to connect to MySQL at 127.0.0.1:3306.\n"
                    + "Make sure MySQL Server is installed and running, the hospital database exists, "
                    + "and the credentials in Connector.java are correct.\n\n"
                    + ex.getMessage(),
                    "Database Connection Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return connection;
    }

    private static String getSetting(String environmentVariable, String systemProperty, String defaultValue) {
        String value = System.getProperty(systemProperty);
        if (value == null || value.isBlank()) {
            value = System.getenv(environmentVariable);
        }
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
