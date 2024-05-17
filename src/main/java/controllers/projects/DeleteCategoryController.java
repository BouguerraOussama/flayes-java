package controllers.projects;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import models.projects.Category;
import services.projects.CategoryService;
import services.projects.ProjectService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeleteCategoryController implements Initializable {

    @FXML
    private ComboBox<String> categoryComboBox;

    private CategoryService categoryService;
    private ProjectService projectService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryService = new CategoryService();
        projectService = new ProjectService();
        populateCategoryComboBox();
    }

    private void populateCategoryComboBox() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            for (Category category : categories) {
                categoryComboBox.getItems().add(category.getCategoryName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
        }
    }

    @FXML
    private void handleDeleteButton() {
        String selectedCategory = categoryComboBox.getValue();
        if (selectedCategory != null) {
            try {
                int categoryId = categoryService.findCategoryIdByName(selectedCategory);
                if (categoryId != -1) {
                    if (projectService.hasDependentProjects(categoryId)) {
                        // Show confirmation dialog
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("Dependent Projects Detected");
                        alert.setContentText("There are dependent projects associated with this category. "
                                + "Do you want to delete them first?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            // Delete dependent projects
                            projectService.deleteProjectsByCategory(categoryId);
                            // Now, delete the category
                            categoryService.deleteCategory(categoryId);
                            categoryComboBox.getItems().remove(selectedCategory);
                        }
                    } else {
                        // No dependent projects, directly delete the category
                        categoryService.deleteCategory(categoryId);
                        categoryComboBox.getItems().remove(selectedCategory);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database error
            }
        }
    }
}