package controllers.users;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.users.User;
import services.users.UserService;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddUser implements Initializable {


    @FXML
    private ImageView image;


    @FXML
    private Label labelTel;

    @FXML
    private TextField textfieldUsername;

    @FXML
    private TextField textfieldEmail;

    @FXML
    private PasswordField jpassword;

    @FXML
    private TextField textfieldTel;
    @FXML
    private Label myFile;

    @FXML
    private Label nom_fichier;

    @FXML
    private ComboBox<String> comboboxRole;

    ObservableList<String> role = FXCollections.observableArrayList();


    private UserService us;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        comboboxRole.setItems(FXCollections.observableArrayList("[\"ROLE_ADMIN\"]", "[\"ROLE_ORGANISATEUR\"]", "[\"ROLE_USER\"]"));
        this.us = new UserService();
    }

    @FXML
    void Select(ActionEvent event) {


    }

    @FXML
    void onAjoute(ActionEvent event) {
        int status = 0;

        String username = textfieldUsername.getText();
        String email = textfieldEmail.getText();
        String password = jpassword.getText();
        String role = (comboboxRole.getValue());

        String image_name = myFile.getText();

        String tel = textfieldTel.getText();
        if (textfieldTel.getText().matches("^[0-9]+$") && textfieldTel.getText().length() != 8) {
            labelTel.setText("veuillez saisir un correct numero");
        } else {
            User u = new User(username, email, tel, password, role, image_name, status);
            us.insertUserPst(u);
        }


    }

    @FXML
    void onChoisi(ActionEvent event) {
        try {
            String filename;
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            if (f != null) {
                filename = f.getAbsolutePath();
                nom_fichier.setText(filename);
                Image img = new Image(new FileInputStream(filename));
                image.setImage(img);
                image.setVisible(true);
                //Déplacer l'image
                String newPath = "C:\\Users\\user\\Desktop\\Flayes-Flayes-offers - Copie\\public\\uploads\\images\\" ;
                File sourceFile = null;
                File destinationFile = null;
                String nFile = f.getName();
                myFile.setText(nFile);
                sourceFile = new File(filename);
                destinationFile = new File(newPath + nFile);
                Files.copy(sourceFile.toPath(), destinationFile.toPath());
            } else {
                System.out.println("Image error");
            }


            //Fin déplacer image
        } catch (IOException ex) {
            Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}