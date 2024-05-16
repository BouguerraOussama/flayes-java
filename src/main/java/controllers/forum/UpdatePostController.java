package controllers.forum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.forum.Post;
import services.forum.PostService;
import utils.SessionManager;
import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;

public class UpdatePostController {

    private final PostService ps = new PostService();
    public ImageView image;
    private int sqlIdx;
    private int room_id;
    private int user_id = SessionManager.getInstance().getUser_id();

    private Post post;

    @FXML
    private TextField authorTF;

    @FXML
    private TextField contentTF;

    @FXML
    private TextField img_path;

    @FXML
    private ImageView image_view;

    @FXML
    private Label author;

    public void setSqlIdx(int sqlIdx) {
        this.sqlIdx = sqlIdx;
    }

    public void setRoomId(int room_id) {
        this.room_id = room_id;
    }



    @FXML
    void initialize(int rid){
        setRoomId(rid);
    }
    @FXML
    void upload_img(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();
            if (path.endsWith(".jpg") || path.endsWith(".png")) {
                // The path ends with either .jpg or .png
                System.out.println("Selected Image Path: " + path);
                img_path.setText(path);
                ImportPicture(img_path.getText(),image_view);
            } else {
                // The path doesn't end with .jpg or .png
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Invalid file path. Please select a .jpg or .png file.");
                alert.showAndWait();
                img_path.setText("");
            }
        } else {
            System.out.println("No image file selected.");
        }
    }

    void ImportPicture(String path , ImageView view){
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        view.setImage(image);
    }

    int verifyInputIsBlank(String str) {
        return str.isBlank() ? -1 : 0;
    }

    @FXML
    public void update(ActionEvent event) {
        try{
            if (verifyInputIsBlank(authorTF.getText()) == -1 && verifyInputIsBlank(contentTF.getText()) == -1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Invalid Input");
                alert.showAndWait();
                return;
            }
                System.out.println(user_id);
                ps.update(new Post(sqlIdx,room_id,authorTF.getText(),contentTF.getText(),img_path.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Post Updated");
                alert.showAndWait();
                for (TextField textField : Arrays.asList(authorTF, contentTF,img_path)) {
                    textField.setText("");
                }

        }catch(SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
    void initData(Post SelectedItem){
        authorTF.setText(SelectedItem.getAuthor());
        contentTF.setText(SelectedItem.getContent());
        img_path.setText(SelectedItem.getImg_url());
    }
}