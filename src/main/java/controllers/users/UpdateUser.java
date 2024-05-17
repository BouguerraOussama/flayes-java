package controllers.users;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

public class UpdateUser implements Initializable {
    @FXML
    private Label labelUsername;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelPassword;

    @FXML
    private Label User_id;

    @FXML
    private ImageView img;

    @FXML
    private PasswordField jPassword;

    @FXML
    private ComboBox<String> comboboxRole;

    @FXML
    private Label nom_fichier;

    @FXML
    private Label myFile;

    @FXML
    private ImageView image;

    ObservableList<String> role =FXCollections.observableArrayList("[\"ROLE_ADMIN\"]","[\"ROLE_ORGANISATEUR\"]","[\"ROLE_USER\"]");

    private UserService us;
    public static User user = new User();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  Timestamp timestamp = null;
        String user_id = Integer.toString(user.getUser_id());
        labelUsername.setText(user.getUsername());
        labelEmail.setText(user.getEmail());
        labelPassword.setText(user.getPassword());


        myFile.setText(user.getImage_name());
        img.setImage(user.getImg().getImage());
        comboboxRole.setItems(FXCollections.observableArrayList("[\"ROLE_ADMIN\"]","[\"ROLE_ORGANISATEUR\"]","[\"ROLE_USER\"]"));
        this.us = new UserService();

    }

    @FXML
    void onModifie(ActionEvent event) {
        String role =(comboboxRole.getValue());
        us.updateRole(user, role);
    }

    @FXML
    void onChoisi(ActionEvent event) {
        try {
            String filename;
            JFileChooser chooser= new JFileChooser();
            chooser.showOpenDialog(null);
            File f=chooser.getSelectedFile();
            filename = f.getAbsolutePath();
            nom_fichier.setText(filename);
            Image img = new Image(new FileInputStream(filename));
            image.setImage(img);
            //Déplacer l'image
            String newPath="C:\\Users\\user\\Desktop\\User\\src\\main\\resources\\users\\";
            File sourceFile=null;
            File destinationFile=null;
            String nFile = f.getName();
            myFile.setText(nFile);
            sourceFile= new File(filename);
            destinationFile=new File(newPath+nFile);
            if(!destinationFile.exists()){
                Files.copy(sourceFile.toPath() , destinationFile.toPath());
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Image déjà existe");
                alert.showAndWait();
            }

            //Fin déplacer image
        } catch (IOException ex) {
            Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }






    public void setUserUpdate(User u) {
        user.setUser_id(u.getUser_id());
        user.setUsername(u.getUsername());
        user.setEmail(u.getEmail());
        user.setPassword(u.getPassword());
        user.setRole(u.getRole());
        user.setImage_name(u.getImage_name());
        user.setImg(u.getImg());
        user.setStatus(u.getStatus());
        user.setTel(u.getTel());

        System.out.println(u.toString());    }

    public void setText(User user) {
        User_id.setText(String.valueOf(user.getUser_id()));
        labelUsername.setText(user.getUsername());
        labelEmail.setText(user.getEmail());
        labelPassword.setText(user.getPassword());
        myFile.setText(user.getImage_name());
        // Assuming 'img' is an ImageView and 'userImg' is an Image
        if (user.getImg() != null) {
            img.setImage(user.getImg().getImage());
        } else {
            img.setImage(null); // Handle the case when the image is null
        }
    }

    public void setTextFields(User R) {
        User_id.setText(String.valueOf(R.getUser_id()));
        labelUsername.setText(R.getUsername());
        labelEmail.setText(R.getEmail());
        // Assuming the 'R' object has a 'Password' attribute, update accordingly if it's named differently
        labelPassword.setText(R.getPassword());
        // Assuming 'myFile' is a label where you set the image file name
        myFile.setText(R.getImage_name());
        // Assuming 'img' is an ImageView
        if (R.getImg() != null) {
            img.setImage(R.getImg().getImage());
        } else {
            img.setImage(null); // Handle the case when the image is null
        }
        // Assuming comboboxRole is a ComboBox to select user roles
        comboboxRole.setItems(FXCollections.observableArrayList("[\"ROLE_ADMIN\"]","[\"ROLE_ORGANISATEUR\"]","[\"ROLE_USER\"]"));
    }


    }


