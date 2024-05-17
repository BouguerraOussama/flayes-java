package services.events;

import models.events.Category;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatService  {

    private Connection connection;

    public CatService() {
        connection = MyDataBase.getInstance().getConnection();
    }
    public Category search(int id) throws SQLException {
        Category category = null;

        try {
            String sql = "SELECT * FROM category WHERE Idcat = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                category = new Category();
                category.setIdcat(resultSet.getInt("Idcat")); // Use "Idcat" instead of "id"
                category.setName(resultSet.getString("name"));
                // Add more attributes if needed
            }

            // Close the ResultSet and PreparedStatement in a finally block to ensure proper resource management
        } catch (SQLException ex) {
            // Handle any SQL exceptions
            ex.printStackTrace(); // Log the exception or handle it appropriately
            throw ex; // Re-throw the exception to propagate it to the caller
        }
        return category;
    }

    public String getName(int eventId) throws SQLException {
        String name = null;
        String sql = "SELECT name FROM event WHERE Idevent = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString("name");
            }
        }
        return name;
    }


    public void create(Category category) throws SQLException {
        String sql = "INSERT INTO category (name, type, target) " +
                "VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, category.getName());    // Set name
        preparedStatement.setString(2, category.getType());    // Set type
        preparedStatement.setString(3, category.getTarget());  // Set target

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void update(Category category) throws SQLException {
        String sql = "UPDATE category SET name = ?, type = ?, target = ? WHERE Idcat = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, category.getName());
        preparedStatement.setString(2, category.getType());
        preparedStatement.setString(3, category.getTarget());
        preparedStatement.setInt(4, category.getIdcat());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }





    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM category WHERE Idcat = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }



    public List<Category> read() throws SQLException {
        String sql = "SELECT * FROM category";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Category> categories = new ArrayList<>();
        while (rs.next()) {
            Category category = new Category();
            category.setIdcat(rs.getInt("Idcat"));
            category.setName(rs.getString("name"));
            category.setType(rs.getString("type"));
            category.setTarget(rs.getString("target"));
            categories.add(category);
        }
        rs.close();
        statement.close();
        return categories;
    }
}
