package controllers.projects;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.projects.Project;
import services.projects.ProjectService;

public class UpdateProjectController {

    @FXML
    private TextField projectNameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField targetAudienceField;

    @FXML
    private TextField demandInMarketField;

    @FXML
    private TextField developmentTimelineField;

    @FXML
    private TextField budgetFundingRequirementsField;

    @FXML
    private TextField riskAnalysisField;

    @FXML
    private TextField marketStrategyField;

    @FXML
    private TextField exitStrategyField;

    @FXML
    private TextField teamBackgroundField;

    @FXML
    private TextField tagsField;

    @FXML
    private TextField uniqueSellingPointsField;

    @FXML
    private TextField dailyPriceOfAssetsField;

    @FXML
    private TextField investorsEquityField;

    private Project currentProject;

    public void initData(Project project) {
        this.currentProject = project;
        populateFields();
    }

    private void populateFields() {
        if (currentProject != null) {
            projectNameField.setText(currentProject.getName());
            descriptionField.setText(currentProject.getDescription());
            targetAudienceField.setText(currentProject.getTargetAudience());
            demandInMarketField.setText(currentProject.getDemandInMarket());
            developmentTimelineField.setText(currentProject.getDevelopmentTimeline());
            budgetFundingRequirementsField.setText(String.valueOf(currentProject.getBudgetFundingRequirements()));
            riskAnalysisField.setText(currentProject.getRiskAnalysis());
            marketStrategyField.setText(currentProject.getMarketStrategy());
            exitStrategyField.setText(currentProject.getExitStrategy());
            teamBackgroundField.setText(currentProject.getTeamBackground());
            tagsField.setText(currentProject.getTags());
            uniqueSellingPointsField.setText(currentProject.getUniqueSellingPoints());
            dailyPriceOfAssetsField.setText(currentProject.getDailyPriceOfAssets());
            investorsEquityField.setText(currentProject.getInvestorsEquity());
        }
    }

    @FXML
    private void saveProject(Project currentProject) {
        if (currentProject != null) {
            currentProject.setName(projectNameField.getText());
            currentProject.setDescription(descriptionField.getText());
            currentProject.setTargetAudience(targetAudienceField.getText());
            currentProject.setDemandInMarket(demandInMarketField.getText());
            currentProject.setDevelopmentTimeline(developmentTimelineField.getText());
            currentProject.setBudgetFundingRequirements(Double.parseDouble(budgetFundingRequirementsField.getText()));
            currentProject.setRiskAnalysis(riskAnalysisField.getText());
            currentProject.setMarketStrategy(marketStrategyField.getText());
            currentProject.setExitStrategy(exitStrategyField.getText());
            currentProject.setTeamBackground(teamBackgroundField.getText());
            currentProject.setTags(tagsField.getText());
            currentProject.setUniqueSellingPoints(uniqueSellingPointsField.getText());
            currentProject.setDailyPriceOfAssets(dailyPriceOfAssetsField.getText());
            currentProject.setInvestorsEquity(investorsEquityField.getText());

            try {
                ProjectService projectService = new ProjectService();
                projectService.updateProject(currentProject);
                showAlert("Project Updated", "The project has been successfully updated.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Update Failed", "An error occurred while updating the project.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("No Project Selected", "No project is selected for updating.", Alert.AlertType.WARNING);
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
