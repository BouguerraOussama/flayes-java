package controllers.projects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.projects.Project;
import services.projects.ProjectService;
import utils.SessionManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddProjectController {

    private final ProjectService projectService = new ProjectService();
    private final List<Project> projects = new ArrayList<>();

    @FXML
    private Button Submitbutton;
    @FXML
    private HBox titleError, descriptionError, typeError;
    @FXML
    private TextField projectNameTF, projectTypeTF;
    @FXML
    private TextArea projectDescriptionTA;
    @FXML
    private VBox projectCardsVbox;

    @FXML
    void initialize() {
        hideErrorMessages();
        try {
            loadProjects();
        } catch (IOException | SQLException e) {
            showErrorDialog("Error initializing projects", e.getMessage());
        }
    }

    @FXML
    private void SubmitbuttonClicked(ActionEvent actionEvent) {
        if (validateForm()) {
            try {
                projectService.create(new Project(
                        projectNameTF.getText(),
                        projectDescriptionTA.getText(),
                        projectTypeTF.getText(),
                        0, 0, SessionManager.getInstance().getUser_id()
                ));
                loadProjects();
                showInfoDialog("Success", "Project added successfully");
                clearForm();
            } catch (SQLException | IOException e) {
                showErrorDialog("Error adding project", e.getMessage());
            }
        }
    }

    private void loadProjects() throws SQLException, IOException {
        projects.clear();
        projects.addAll(projectService.read());
        projectCardsVbox.getChildren().clear();
        for (Project project : projects) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/projects/ProjectCard.fxml"));
            Pane projectCard = loader.load();
            ProjectCardController projectCardController = loader.getController();
            projectCardController.setData(project);
            projectCardsVbox.getChildren().add(projectCard);
        }
    }

    private boolean validateForm() {
        boolean valid = true;
        if (projectNameTF.getText().isEmpty()) {
            titleError.setVisible(true);
            valid = false;
        } else {
            titleError.setVisible(false);
        }
        if (projectDescriptionTA.getText().isEmpty()) {
            descriptionError.setVisible(true);
            valid = false;
        } else {
            descriptionError.setVisible(false);
        }
        if (projectTypeTF.getText().isEmpty()) {
            typeError.setVisible(true);
            valid = false;
        } else {
            typeError.setVisible(false);
        }
        return valid;
    }

    private void hideErrorMessages() {
        titleError.setVisible(false);
        descriptionError.setVisible(false);
        typeError.setVisible(false);
    }

    private void clearForm() {
        projectNameTF.clear();
        projectDescriptionTA.clear();
        projectTypeTF.clear();
        hideErrorMessages();
    }

    private void showInfoDialog(String title, String content) {
        showAlert(Alert.AlertType.INFORMATION, title, content);
    }

    private void showErrorDialog(String title, String content) {
        showAlert(Alert.AlertType.ERROR, title, content);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
