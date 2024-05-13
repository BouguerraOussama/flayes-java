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

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddProjectController {
    /**services**/
    ProjectService projectService = new ProjectService();
    public Button SubmitbuttonClicked;
    public HBox typeError;
    public TextField projectTypeTF;
    public HBox descriptionError;
    public TextArea projectDescriptionTA;
    public HBox titleError;
    public TextField projectNameTF;
    public VBox projectCardsVbox;
    List<Project> projects = new ArrayList<>();

    @FXML
    void initialize() {
        titleError.setVisible(false);
        descriptionError.setVisible(false);
        typeError.setVisible(false);

        try{
            loadingProjects();
        }catch (IOException  | SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public Button getSubmitbuttonClicked() {
        return SubmitbuttonClicked;
    }

    public ProjectService getProjectService() {
        return projectService;
    }

    public void SubmitbuttonClicked(ActionEvent actionEvent){

        /*check text fields are empty*/
        titleError.setVisible(projectNameTF.getText().isEmpty());
        descriptionError.setVisible(projectDescriptionTA.getText().isEmpty());
        typeError.setVisible(projectTypeTF.getText().isEmpty());

        // If any error labels are visible, do not proceed with submission
        if (titleError.isVisible() || descriptionError.isVisible() || typeError.isVisible()) {
            return;
        }
        //proceeding to submission of the form to data base
        try{

            projectService.create(new Project(projectNameTF.getText(), projectDescriptionTA.getText(), projectTypeTF.getText(), 0, 0));

            loadingProjects();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("project added successfully");
            alert.showAndWait();
            projectNameTF.setText("");
            projectDescriptionTA.setText("");
            projectTypeTF.setText("");
        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void loadingProjects() throws SQLException, IOException {
        projects = projectService.read();
        projectCardsVbox.getChildren().clear();
        for (Project project:projects)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/projects/ProjectCard.fxml"));

            Pane view = loader.load();
            ProjectCardController projectCardController = loader.getController();
            projectCardController.setData(project);

            projectCardsVbox.getChildren().add(view);

        }
    }

}
