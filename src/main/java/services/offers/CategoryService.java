package services.offers;

import models.offers.Category;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CategoryService implements IService<Category> {
    private Connection connection;
    public CategoryService(){
        connection = MyDataBase.getInstance().getConnection();
    }


    @Override
    public int create(Category category) throws SQLException {
        String sql = "INSERT INTO funding (type, attribute1, attribute2, attribute3, textAttribute, score) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getType());
            ps.setFloat(2, category.getAttribute1());
            ps.setFloat(3, category.getAttribute2());
            ps.setFloat(4, category.getAttribute3());
            ps.setString(5, category.getTextAttribute());
            ps.setFloat(6, category.getScore());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                // Retrieve the generated keys
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Return the auto-generated ID
                }
            }
        } catch (SQLException e) {
            // Handle any exceptions here
            e.printStackTrace();
        }
        return -1; // Return -1 if insertion fails or ID cannot be retrieved
    }


    @Override
    public void update(Category o) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM funding WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    @Override
    public List read() throws SQLException {

        return null;
    }

    public Category getSingleCategory(int fundingId) throws SQLException {
        String sql = "select * from funding where id = " + fundingId;
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        Category category = new Category();
        while (rs.next()){
            category.setId(rs.getInt("id"));
            category.setType(rs.getString("type"));
            category.setAttribute1(rs.getFloat("attribute1"));
            category.setAttribute2(rs.getFloat("attribute2"));
            category.setAttribute3(rs.getFloat("attribute3"));
            category.setTextAttribute(rs.getString("textAttribute"));

        }
        return category;
    }


}
