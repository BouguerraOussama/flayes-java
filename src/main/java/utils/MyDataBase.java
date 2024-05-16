package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {

    private final String URL = "jdbc:mysql://localhost:3306/integration_flayes";
    private final String USER = "root";
    private final String PASS = "";
    private Connection connection;

    private static MyDataBase instance;

    private MyDataBase() {
        try {
            // Lazy initialization of connection
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static synchronized MyDataBase getInstance() {
        if (instance == null)
            instance = new MyDataBase();
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String unicode = "useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
                connection = DriverManager.getConnection(URL + "?" + unicode, USER, PASS);
                System.out.println("Connection opened");
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("Error getting connection: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Close connection method
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
