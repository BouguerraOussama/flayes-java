package utils;

import models.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public final class SessionManager {
    private static SessionManager instance;
    private static User current_User;
    private Connection connection;
    private static int id;

    private SessionManager() {
        connection = MyDataBase.getInstance().getConnection();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public User getCurrent_User() {
        return current_User;
    }

    public void setCurrent_User(User current_User) {
        SessionManager.current_User = current_User;
    }
    public int getUser_id() {
        return id;
    }

    public void setUser_id(int id) {
        this.id = id;
    }
    public User auth(String email, String password) throws SQLException {
        String query = "SELECT * FROM user WHERE email=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("password");

                    // Remove the version prefix ($2y$) and add the $2a$ prefix
                    String hashed2a = "$2a$" + hashedPassword.substring(4);

                    // Compare the password with the modified hashed password
                    if (BCrypt.checkpw(password, hashed2a)) {
                        User user = new User(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                resultSet.getString("tel"),
                                hashedPassword, // Use the stored hashed password
                                resultSet.getString("roles"),
                                resultSet.getString("image_name"),
                                resultSet.getInt("status"),
                                null // ImageView img; - Not included in the constructor
                        );
                        setUser_id(user.getUser_id()); // Set the id attribute when user is authenticated
                        System.out.println("Login successful");
                        return user;
                    } else {
                        System.out.println("Password does not match");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Login not successful");
        return null;
    }
    public void cleanUserSession() {
        current_User = null;
        id = 0; // Reset id attribute when user session is cleaned
    }
}
