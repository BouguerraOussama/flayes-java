package controllers.projects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import models.projects.Project;
import services.projects.ProjectService;

import java.sql.SQLException;

public class ProjectController {
    private int categoryId;

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
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ImageView projectImageView;

    @FXML
    private TextField riskPercentageField;

    @FXML
    private Button saveProjectBtn;
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    @FXML
    void saveProject(ActionEvent event) {
        String projectName = projectNameField.getText();
        String description = descriptionTextArea.getText();
        double budget = Double.parseDouble(budgetField.getText());
        int sellingPoints = Integer.parseInt(sellingPointsField.getText());
        String teamBackground = teamBackgroundTextArea.getText();
        String marketStrategy = marketStrategyTextArea.getText();
        String tags = tagsTextArea.getText();
        String targetAudience = "Example Target Audience";  // Placeholder
        String demandInMarket = "Example Demand in Market"; // Placeholder
        String developmentTimeline = "Example Timeline";    // Placeholder
        String riskAnalysis = riskPercentageField.getText(); // Placeholder
        String exitStrategy = "Example Exit Strategy";      // Placeholder
        String dailyPriceOfAssets = "Example Price";        // Placeholder
        String investorsEquity = "Example Equity";          // Placeholder
        // You need to handle date conversion from DatePicker controls

        // Assuming you have a method to get image path or bytes from the ImageView
        String imagePath = ""; // You need to implement this

        double riskPercentage = Double.parseDouble(riskPercentageField.getText());

        // Assuming you have a method to get category ID from the UI


        Project project = new Project(projectName, imagePath, description, null, null,
                null, budget, null, marketStrategy, null, teamBackground, tags,
                null, null, null, categoryId);

        ProjectService projectService = new ProjectService();
        try {
            projectService.createProject(project);
            System.out.println("Project saved successfully.");
            // Clear the fields after successful save if needed
            clearFields();
        } catch (SQLException e) {
            System.err.println("Error saving project: " + e.getMessage());
            // Handle error
        }
    }

    // Method to clear all input fields after successful save
    private void clearFields() {
        projectNameField.clear();
        descriptionTextArea.clear();
        budgetField.clear();
        sellingPointsField.clear();
        teamBackgroundTextArea.clear();
        marketStrategyTextArea.clear();
        tagsTextArea.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        projectImageView.setImage(null);
        riskPercentageField.clear();
    }


}