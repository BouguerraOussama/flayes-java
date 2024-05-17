/* package controllers.projects;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.projects.Project;

import java.text.SimpleDateFormat;

public class ProjectCardController {
    public Label project_name;
    public Label project_type;
    public Label project_status;
    public Label date_of_expiration;
    public Button projectCardButton;
    private int ProjectKey;

    public void setData(Project project) {
        ProjectKey = project.getId();
        this.project_name.setText(project.getName());
        this.project_type.setText(project.getType());
        switch (project.getUser_status()) {
            case 0:
                this.project_status.setText("no match found");
                this.project_status.setStyle("-fx-text-fill: yellow");
                break;
            case 1:
                this.project_status.setText("ended");
                this.project_status.setStyle("-fx-text-fill: red");
                break;
        }
        //expriation date:
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(project.getEnd_date());
        this.date_of_expiration.setText(formattedDate);

    }

    public Button getProjectCardButton() {
        return projectCardButton;
    }

    public int projectCardButtonClicked() {
        return this.ProjectKey;
    }
}
*/