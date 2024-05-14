package controllers.mainPage;
import controllers.forum.AddRoomController;
import controllers.users.Profil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.users.User;
import okhttp3.*;
import services.users.UserService;
import utils.SessionManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class Front {
    @FXML
    private Button btn_drop;
    @FXML
    private BorderPane Main_content;
    @FXML
    private Button btn_home;
    @FXML
    private Button btn_project;
    @FXML
    private Button btn_events;
    @FXML
    private Button btn_forum;
    @FXML
    private Button btn_invest;
    @FXML
    private Button log_out;
    @FXML
    private Button profil;
    @FXML
    private ImageView imageView;
    @FXML
    private AnchorPane MainPageController;
    @FXML
    private Button btn_user;

    private User user = new User();
    private Profil pc = new Profil();
    private UserService us = new UserService();

    private Pane view;
    private Button activeButton;

    @FXML
    void initialize(URL url, ResourceBundle rb) {
        setActive(btn_home);
        // user = us.getUserConnected();
        user = us.getUserById(SessionManager.getInstance().getUser_id());
        // Get the user and load the image
        String imagePath = "C:\\Users\\user\\Desktop\\Nouveau dossier\\Nouveau dossier\\Flayes-Flayes-\\src\\main\\resources\\images\\";
        String imageFileName = "femme.png";
        String completeFilePath = imagePath + imageFileName;
        File file = new File(completeFilePath);
        Image image = new Image(file.toURI().toString());
        // Set properties to the ImageView
        imageView.setImage(image);
        imageView.setFitHeight(50.0);
        imageView.setFitWidth(50.0);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
    }

    @FXML
    void HomeButtonClicked(ActionEvent actionEvent) {
        setActive(btn_home);
        // Get the user and load the image
        String imagePath = "C:\\Users\\user\\Desktop\\Nouveau dossier\\Nouveau dossier\\Flayes-Flayes-\\src\\main\\resources\\images\\";
        String imageFileName = "femme.png";
        String completeFilePath = imagePath + imageFileName;
        File file = new File(completeFilePath);
        Image image = new Image(file.toURI().toString());
        // Set properties to the ImageView
        imageView.setImage(image);
        imageView.setFitHeight(50.0);
        imageView.setFitWidth(50.0);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
    }

    @FXML
    void ProjectButtonClicked(ActionEvent actionEvent) {
        setActive(btn_project);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/ChatGPTApp.fxml"));
            view = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Main_content.setCenter(view);

    }

    @FXML
    void InvestButtonClicked(ActionEvent actionEvent) {
        setActive(btn_invest);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/offers/AddOffer.fxml"));
            view = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Main_content.setCenter(view);
    }

    @FXML
    void EventsButtonClicked(ActionEvent actionEvent) {
        setActive(btn_events);
        // user = us.getUserConnected();
        user = us.getUserById(SessionManager.getInstance().getUser_id());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/events/Ticketclient.fxml"));
            view = fxmlLoader.load();

            // Show language selection dialog for translation
           // showLanguageSelectionDialog(view);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Main_content.setCenter(view);
    }


    @FXML
    void ForumButtonClicked(ActionEvent actionEvent) {
        Pane view = new Pane();
        setActive(btn_forum);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/forum/AddRoom.fxml"));
            AddRoomController AddRoom = fxmlLoader.getController();
            view = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Main_content.setCenter(view);
    }

    @FXML
    void UserButtonClicked(ActionEvent actionEvent) {
        setActive(btn_user);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/users/ShowReclamation.fxml"));
            view = fxmlLoader.load();
            //user = us.getUserConnected();
            user = us.getUserById(SessionManager.getInstance().getUser_id());
            // Get the user and load the image
            String imagePath = "C:\\Users\\user\\Desktop\\Nouveau dossier\\Nouveau dossier\\Flayes-Flayes-\\src\\main\\resources\\images\\";
            String imageFileName = user.getImage_name();
            String completeFilePath = imagePath + imageFileName;
            File file = new File(completeFilePath);
            Image image = new Image(file.toURI().toString());
            // Set properties to the ImageView
            imageView.setImage(image);
            imageView.setFitHeight(50.0);
            imageView.setFitWidth(50.0);
            imageView.setPreserveRatio(true);
            imageView.setPickOnBounds(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Main_content.setCenter(view);
    }

    @FXML
    void LogoutButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {
        setActive(log_out);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/users/SignIn.fxml"));
        Node node = fxmlLoader.load();
        AnchorPane pane = new AnchorPane(node);
        //user = us.getUserConnected();
        user = us.getUserById(SessionManager.getInstance().getUser_id());
        MainPageController.getChildren().setAll(pane);
        us.logOut(user);
    }

    @FXML
    void profilButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {
        setActive(profil);
        // user = us.getUserConnected();
        user = us.getUserById(SessionManager.getInstance().getUser_id());
        pc.setConnected(user);
        System.out.println("openProfil" + user.toString());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/users/Profil.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        Profil muc = loader.getController();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @FXML
    void QuitButtonClicked(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    void DropButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_drop.getScene().getWindow();
        stage.setIconified(true);
    }

    private void setActive(Button button) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("btn_active");
        }
        button.getStyleClass().add("btn_active");
        activeButton = button;
    }




   
}
