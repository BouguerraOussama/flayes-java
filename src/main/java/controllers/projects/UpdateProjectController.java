package controllers.projects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.projects.Project;
import services.projects.ProjectService;

import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateProjectController {

    @FXML
    private TextField projectNameField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField budgetField;

    @FXML
    private TextField sellingPointsField;

    @FXML
    private TextArea teamBackgroundTextArea;

    @FXML
    private TextArea marketStrategyTextArea;

    @FXML
    private TextArea tagsTextArea;

    @FXML
    private TextField riskPercentageField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ImageView projectImageView;

    private Project currentProject;
    // In UpdateProjectController
    public void setCurrentProject(Project project) {
        this.currentProject = project;
        populateUI(); // Make sure this method exists and correctly initializes the UI components
    }
    private void populateUI() {
        if (currentProject != null) {
            projectNameField.setText(currentProject.getName());
            descriptionTextArea.setText(currentProject.getDescription());
            // Populate other fields similarly
        }
    }



    // Method to initialize controller with project data
    public void initializeWithProjectData(Project project) {
        this.currentProject = project;
        projectNameField.setText(project.getName());
        descriptionTextArea.setText(project.getDescription());
        budgetField.setText(String.valueOf(project.getBudgetFundingRequirements()));
        sellingPointsField.setText(project.getUniqueSellingPoints());
        teamBackgroundTextArea.setText(project.getTeamBackground());
        marketStrategyTextArea.setText(project.getMarketStrategy());
        tagsTextArea.setText(project.getTags());
        riskPercentageField.setText(project.getRiskAnalysis());
        // Initialize startDatePicker and endDatePicker if your project model supports these fields
        // projectImageView.setImage(new Image(project.getImagePath())); // Assuming you have a way to get image path

        // Placeholder for date pickers and image view initialization
        // startDatePicker.setValue(LocalDate.parse(project.getStartDate()));
        // endDatePicker.setValue(LocalDate.parse(project.getEndDate()));
        // projectImageView.setImage(new Image(project.getImagePath()));
    }

    // Method to handle the save button action
    @FXML
    void saveProject(ActionEvent event) {
        try {
            // Validate input
            if (!validateInput()) {
                showAlert("Validation Error", "Please fill in all required fields correctly.", Alert.AlertType.ERROR);
                return;
            }

            // Update project with new data from UI elements
            currentProject.setName(projectNameField.getText());
            currentProject.setDescription(descriptionTextArea.getText());
            currentProject.setBudgetFundingRequirements(Double.parseDouble(budgetField.getText()));
            currentProject.setUniqueSellingPoints(sellingPointsField.getText());
            currentProject.setTeamBackground(teamBackgroundTextArea.getText());
            currentProject.setMarketStrategy(marketStrategyTextArea.getText());
            currentProject.setTags(tagsTextArea.getText());
            currentProject.setRiskAnalysis(riskPercentageField.getText());
            // Update other fields like start and end dates, image path, etc., as necessary

            ProjectService projectService = new ProjectService();
            boolean success = projectService.updateProject(currentProject);
            if (success) {
                showAlert("Success", "Project updated successfully.", Alert.AlertType.INFORMATION);
                closeStage();
            } else {
                showAlert("Failed", "Failed to update project.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Error updating project: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers for budget and risk percentage.", Alert.AlertType.ERROR);
        }
    }

    // Validate user input
    private boolean validateInput() {
        // Implement validation logic, return false if validation fails
        return !projectNameField.getText().isEmpty() && !descriptionTextArea.getText().isEmpty();
        // Add further validation as necessary
    }

    // Utility method to show alert dialogs
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Close the stage of this controller
    private void closeStage() {
        Stage stage = (Stage) projectNameField.getScene().getWindow();
        stage.close();
    }
}
