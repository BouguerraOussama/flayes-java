package controllers.projects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.projects.Category;
import models.projects.Project;
import services.projects.ProjectService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainPageController {

    @FXML
    private ScrollPane projectListScrollPane;

    @FXML
    private TableView<Project> projectTableView;

    @FXML
    private TableColumn<Project, String> nameColumn;

    @FXML
    private TableColumn<Category, String> categoryColumn;

    @FXML
    private Label projectNameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label targetAudienceLabel;

    @FXML
    private Label demandInMarketLabel;

    @FXML
    private Label developmentTimelineLabel;

    @FXML
    private Label budgetFundingRequirementsLabel;

    @FXML
    private Label riskAnalysisLabel;

    @FXML
    private Label marketStrategyLabel;

    @FXML
    private Label exitStrategyLabel;

    @FXML
    private Label teamBackgroundLabel;

    @FXML
    private Label tagsLabel;

    @FXML
    private Label uniqueSellingPointsLabel;

    @FXML
    private Label dailyPriceOfAssetsLabel;

    @FXML
    private Label investorsEquityLabel;

    private Timeline timeline;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        projectTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        projectTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayProjectDetails(newValue)
        );

        loadProjectsWithCategories();

        timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> loadProjectsWithCategories()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();
    }

    private void loadProjectsWithCategories() {
        try {
            ProjectService projectService = new ProjectService();
            List<Project> projects = projectService.getProjectsWithCategoryNames();
            projectTableView.setItems(FXCollections.observableArrayList(projects));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle errors or notify the user here
        }
    }

    private void displayProjectDetails(Project project) {
        if (project != null) {
            projectNameLabel.setText(project.getName());
            descriptionLabel.setText(project.getDescription());
            targetAudienceLabel.setText(project.getTargetAudience());
            demandInMarketLabel.setText(project.getDemandInMarket());
            developmentTimelineLabel.setText(project.getDevelopmentTimeline());
            budgetFundingRequirementsLabel.setText(String.valueOf(project.getBudgetFundingRequirements()));
            riskAnalysisLabel.setText(project.getRiskAnalysis());
            marketStrategyLabel.setText(project.getMarketStrategy());
            exitStrategyLabel.setText(project.getExitStrategy());
            teamBackgroundLabel.setText(project.getTeamBackground());
            tagsLabel.setText(project.getTags());
            uniqueSellingPointsLabel.setText(project.getUniqueSellingPoints());
            dailyPriceOfAssetsLabel.setText(project.getDailyPriceOfAssets());
            investorsEquityLabel.setText(project.getInvestorsEquity());

            // Print project details to the console
            System.out.println("Project Details:");
            System.out.println("Name: " + project.getName());
            System.out.println("Description: " + project.getDescription());
            System.out.println("Target Audience: " + project.getTargetAudience());
            System.out.println("Demand in Market: " + project.getDemandInMarket());
            System.out.println("Development Timeline: " + project.getDevelopmentTimeline());
            System.out.println("Budget Funding Requirements: " + project.getBudgetFundingRequirements());
            System.out.println("Risk Analysis: " + project.getRiskAnalysis());
            System.out.println("Market Strategy: " + project.getMarketStrategy());
            System.out.println("Exit Strategy: " + project.getExitStrategy());
            System.out.println("Team Background: " + project.getTeamBackground());
            System.out.println("Tags: " + project.getTags());
            System.out.println("Unique Selling Points: " + project.getUniqueSellingPoints());
            System.out.println("Daily Price of Assets: " + project.getDailyPriceOfAssets());
            System.out.println("Investors Equity: " + project.getInvestorsEquity());
        } else {
            projectNameLabel.setText("");
            descriptionLabel.setText("");
            targetAudienceLabel.setText("");
            demandInMarketLabel.setText("");
            developmentTimelineLabel.setText("");
            budgetFundingRequirementsLabel.setText("");
            riskAnalysisLabel.setText("");
            marketStrategyLabel.setText("");
            exitStrategyLabel.setText("");
            teamBackgroundLabel.setText("");
            tagsLabel.setText("");
            uniqueSellingPointsLabel.setText("");
            dailyPriceOfAssetsLabel.setText("");
            investorsEquityLabel.setText("");
        }
    }

    public void handleDeleteButton(ActionEvent event) {
        try {
            // Load the FXML file for the project interface
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/fxml/projects/Delete_categorie.fxml"));
            Parent root1 = loader1.load();

            // Get the controller of the project interface
            DeleteCategoryController deleteCategory = loader1.getController();

            // Create a new stage for the project interface
            Stage projectStage = new Stage();
            projectStage.setTitle("delete categorie");
            projectStage.setScene(new Scene(root1));

            // Show the project interface
            projectStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleUpdateButton(ActionEvent event) {
        Project selectedProject = projectTableView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/projects/update_project.fxml"));
                Parent root = loader.load();

                // Get the controller and pass the selected project to it
                UpdateProjectController updateController = loader.getController();
                updateController.initData(selectedProject);

                // Create a new stage for the update interface
                Stage projectStage = new Stage();
                projectStage.setTitle("Update Project");
                projectStage.setScene(new Scene(root));
                projectStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Selection", "Please select a project to update.", Alert.AlertType.WARNING);
        }
    }

    public void stop() {
        // Stop the animation when leaving the scene
        timeline.stop();
    }

    public void handleAddButton(ActionEvent event) {
        try {
            // Load the FXML file for the project interface
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/fxml/projects/add_categorie.fxml"));
            Parent root1 = loader1.load();

            // Get the controller of the project interface
            AddCategoryController deleteCategory = loader1.getController();

            // Create a new stage for the project interface
            Stage projectStage = new Stage();
            projectStage.setTitle("delete categorie");
            projectStage.setScene(new Scene(root1));

            // Show the project interface
            projectStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAddProjectButton(ActionEvent event) {
        try {
            // Load the FXML file for the project interface
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/fxml/projects/add_categorie.fxml"));
            Parent root1 = loader1.load();

            // Get the controller of the project interface
            AddCategoryController deleteCategory = loader1.getController();

            // Create a new stage for the project interface
            Stage projectStage = new Stage();
            projectStage.setTitle("delete categorie");
            projectStage.setScene(new Scene(root1));

            // Show the project interface
            projectStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleUppProjectButton(ActionEvent event) {
        try {
            // Load the FXML file for the project interface
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/fxml/projects/update_project.fxml"));
            Parent root1 = loader1.load();

            // Get the controller of the project interface
            UpdateProjectController Update = loader1.getController();

            // Create a new stage for the project interface
            Stage projectStage = new Stage();
            projectStage.setTitle("delete categorie");
            projectStage.setScene(new Scene(root1));

            // Show the project interface
            projectStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteProjectAction(ActionEvent event) {
        Project selectedProject = projectTableView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            try {
                ProjectService projectService = new ProjectService();
                boolean deleted = projectService.deleteProject(selectedProject.getId());
                if (deleted) {
                    projectTableView.getItems().remove(selectedProject);
                    showAlert("Project Deleted", "The project has been successfully deleted.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Deletion Failed", "The project could not be deleted. Please check for dependencies.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Database Error", "Error deleting the project from the database.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("No Selection", "Please select a project to delete.", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
