package controllers.projects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.projects.Category;
import services.projects.CategoryService;

import java.io.IOException;
import java.sql.SQLException;

public class AddCategoryController {

    @FXML
    private Label labelErrorMessage;

    @FXML
    private TextField nameField;

    @FXML
    private TextField subfieldField;

    @FXML
    private ComboBox<String> typeOfFundingComboBox;

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private Button btnSubmit;

    @FXML
    private void handleInputKeyTyped(KeyEvent event) {
        // Optional validation logic can be added here
    }

    @FXML
    private void handleSubmitButtonClicked() {
        String name = nameField.getText().trim();
        String subfield = subfieldField.getText().trim();
        String typeOfFunding = typeOfFundingComboBox.getValue();
        Integer selectedYear = yearComboBox.getValue();


        if (name.isEmpty() || subfield.isEmpty() || typeOfFunding == null || selectedYear == null) {
            labelErrorMessage.setText("Please fill in all fields.");
            return;
        }

        // Proceed with adding the category

        // Example usage of methods from the CategoryService class
        CategoryService categoryService = new CategoryService();
        Category newCategory = new Category(name, subfield, typeOfFunding, String.valueOf(selectedYear));
        try {
            int categoryId = categoryService.createCategoryAndGetId(newCategory);

            // Check if the category was successfully created
            if (categoryId != -1) {
                // Category created successfully, now you can open the project interface with the category ID
                openProjectInterface(categoryId);
            } else {
                labelErrorMessage.setText("Failed to add the category.");
            }
        } catch (SQLException e) {
            labelErrorMessage.setText("An error occurred while adding the category: " + e.getMessage());
        }
    }






    private void openProjectInterface(int categoryId) {
        try {
            // Load the FXML file for the project interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/projects/add_project.fxml"));
            Parent root = loader.load();

            // Get the controller of the project interface
            ProjectController projectController = loader.getController();

            // Pass the category ID to the project controller
            projectController.setCategoryId(categoryId);

            // Create a new stage for the project interface
            Stage projectStage = new Stage();
            projectStage.setTitle("Project Interface");
            projectStage.setScene(new Scene(root));

            // Show the project interface
            projectStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        // Set up combobox items
        typeOfFundingComboBox.getItems().addAll("Crowdfunding", "Inventure Funds", "Self-funded");
        typeOfFundingComboBox.setValue("Self-funded");

        // Generate years starting from current year minus 5 until current year plus 5

        for (int i = 1 ; i <=10; ++i) {
            yearComboBox.getItems().add(i);
        }
        yearComboBox.setValue(1);

        // Listeners to disable submit button if any text field is empty
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            btnSubmit.setDisable(newValue == null || newValue.isEmpty());
        });
        subfieldField.textProperty().addListener((observable, oldValue, newValue) -> {
            btnSubmit.setDisable(newValue == null || newValue.isEmpty());
        });
    }
}