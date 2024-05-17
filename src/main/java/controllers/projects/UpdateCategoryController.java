package controllers.projects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.projects.Category;
import services.projects.CategoryService;

import java.sql.SQLException;

public class UpdateCategoryController {

    @FXML
    private TextField categoryNameField;
    @FXML
    private TextField subfieldField;
    @FXML
    private ComboBox<String> typeOfFundingCombo;
    @FXML
    private ComboBox<String> profitabilityIndexCombo;
    @FXML
    private Button saveButton;

    private CategoryService categoryService;
    private Category category; // Assuming this is a class variable representing the category being edited

    public UpdateCategoryController() {
        categoryService = new CategoryService();
        // Initialize your service here if necessary
    }

    @FXML
    private void initialize() {
        // Initialize UI components, e.g., loading category data into fields if this is an edit operation
        // You might need to pass the category object to this controller in a real application

        // Example, assuming category is already set
        if (category != null) {
            categoryNameField.setText(category.getCategoryName());
            subfieldField.setText(category.getSubfield());
            // Initialize combo boxes similarly
        }
    }

    @FXML
    private void handleSaveAction(ActionEvent event) throws SQLException {
        // Validate input fields
        // Update category object with new values from input fields
        category.setCategoryName(categoryNameField.getText());
        category.setSubfield(subfieldField.getText());
        // Update other fields similarly

        // Call the service method to update the category in the database
        boolean success = categoryService.updateCategory(category);

        if (success) {
            // Handle success (e.g., show a confirmation message)
        } else {
            // Handle failure (e.g., show an error message)
        }
    }
}