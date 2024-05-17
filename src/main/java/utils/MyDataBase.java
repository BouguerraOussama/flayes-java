package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {

    private final String URL = "jdbc:mysql://localhost:3306/3a2";
    private final String USER = "root";
    private final String PASS = "26653671";
    private Connection connection;

    private static MyDataBase instance;

    private MyDataBase() {
        connect();
    }

    public static MyDataBase getInstance() {
        if (instance == null) {
            instance = new MyDataBase();
        }
        return instance;
    }

    private void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Connection established");
            }
        } catch (SQLException e) {
            System.err.println("Error establishing connection: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        try {
            // Check if connection is still valid with a 5 seconds timeout
            if (connection == null || connection.isClosed() || !connection.isValid(5)) {
                System.out.println("Reconnecting to the database...");
                connect();
            }
        } catch (SQLException e) {
            System.err.println("Error checking connection validity: " + e.getMessage());
        }
        return connection;
    }
}
