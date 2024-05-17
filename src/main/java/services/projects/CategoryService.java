package services.projects;

import models.projects.Category;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    public Category createCategory(Category category) throws SQLException {
        String sql = "INSERT INTO categories (category_name, subfield, type_of_funding, profitability_index) VALUES (?, ?, ?, ?)";
        try (Connection conn = MyDataBase.getInstance().getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, category.getCategoryName());
            pstmt.setString(2, category.getSubfield());
            pstmt.setString(3, category.getTypeOfFunding());
            pstmt.setString(4, category.getProfitabilityIndex());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        category.setId(rs.getInt(1));
                    }
                }
            }
        }
        return category;
    }
    public int createCategoryAndGetId(Category category) throws SQLException {
        String sql = "INSERT INTO categories (category_name, subfield, type_of_funding, profitability_index) VALUES (?, ?, ?, ?)";
        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, category.getCategoryName());
            pstmt.setString(2, category.getSubfield());
            pstmt.setString(3, category.getTypeOfFunding());
            pstmt.setString(4, category.getProfitabilityIndex());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Return the ID of the newly created category
                    }
                }
            }
        }
        return -1; // Return -1 if no category was created (error)
    }


    public Category getCategoryById(int id) throws SQLException {
        String sql = "SELECT * FROM categories WHERE id = ?";
        Category category = null;

        try (Connection conn = MyDataBase.getInstance().getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setSubfield(rs.getString("subfield"));
                category.setTypeOfFunding(rs.getString("type_of_funding"));
                category.setProfitabilityIndex(rs.getString("profitability_index"));
            }
        }
        return category;
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try (Connection conn = MyDataBase.getInstance().getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setSubfield(rs.getString("subfield"));
                category.setTypeOfFunding(rs.getString("type_of_funding"));
                category.setProfitabilityIndex(rs.getString("profitability_index"));
                categories.add(category);
            }
        }
        return categories;
    }

    public boolean updateCategory(Category category) throws SQLException {
        String sql = "UPDATE categories SET category_name = ?, subfield = ?, type_of_funding = ?, profitability_index = ? WHERE id = ?";
        try (Connection conn = MyDataBase.getInstance().getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category.getCategoryName());
            pstmt.setString(2, category.getSubfield());
            pstmt.setString(3, category.getTypeOfFunding());
            pstmt.setString(4, category.getProfitabilityIndex());
            pstmt.setInt(5, category.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    public int findCategoryIdByName(String categoryName) throws SQLException {
        String sql = "SELECT id FROM categories WHERE category_name = ?";
        try (Connection conn = MyDataBase.getInstance().getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, categoryName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return -1; // Category not found
    }
    public int deleteCategory(int id) throws SQLException {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }
    public Category getCategoryByName(String name) throws SQLException {
        String sql = "SELECT * FROM categories WHERE category_name = ?";
        Category category = null;

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setSubfield(rs.getString("subfield"));
                category.setTypeOfFunding(rs.getString("type_of_funding"));
                category.setProfitabilityIndex(rs.getString("profitability_index"));
            }
        }
        return category;
    }

    public boolean updateCategoryByName(String name, Category updatedCategory) throws SQLException {
        String sql = "UPDATE categories SET category_name = ?, subfield = ?, type_of_funding = ?, profitability_index = ? WHERE category_name = ?";

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, updatedCategory.getCategoryName());
            pstmt.setString(2, updatedCategory.getSubfield());
            pstmt.setString(3, updatedCategory.getTypeOfFunding());
            pstmt.setString(4, updatedCategory.getProfitabilityIndex());
            pstmt.setString(5, name);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean deleteCategoryByName(String name) throws SQLException {
        String sql = "DELETE FROM categories WHERE category_name = ?";

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}