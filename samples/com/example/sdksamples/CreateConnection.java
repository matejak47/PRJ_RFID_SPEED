
package com.example.sdksamples;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author matee
 */
public class CreateConnection {
    public static Connection getConnection(String user, String password, int port, String dbName) {
        String url = "jdbc:mysql://localhost:" + port + "/" + dbName;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Připojení k databázi bylo úspěšné.");
        } catch (SQLException e) {
            System.err.println("Chyba při připojování k databázi: " + e.getMessage());
        }
        return connection;
    }
}
