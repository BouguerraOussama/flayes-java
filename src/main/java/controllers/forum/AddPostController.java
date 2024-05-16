package controllers.forum;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import models.forum.Post;
import models.users.User;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import services.forum.PostService;
import utils.SessionManager;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AddPostController {

    private final PostService ps = new PostService();
    public VBox vBox;
    @FXML
    private VBox ListPosts;
    private int room_id;
    private int user_id = SessionManager.getInstance().getUser_id();

    @FXML
    private TextField authorTF;

    @FXML
    private TextArea contentTF;

    @FXML
    private TextField img_path;

    @FXML
    private ImageView image_view;
    @FXML
    private Label descriptionError = new Label();

    @FXML
    private Label descriptionSuccess = new Label();

    @FXML
    private Label SubcatSuccess = new Label();

    @FXML
    private Label SubcatError = new Label();

    public Label typeSuccess = new Label();
    public Label typeError = new Label();

    @FXML
    private VBox Investment_type_Pane;

    @FXML
    private Label author;
    private Post post;

    public void setRoomId(int room_id) {
        this.room_id = room_id;
    }


    @FXML
    void initialize(int rid) throws SQLException {
        setRoomId(rid);
        Investment_type_Pane.getChildren().clear();
        SubcatError.setVisible(false);
        SubcatSuccess.setVisible(false);
        descriptionError.setVisible(false);
        descriptionSuccess.setVisible(false);
        typeSuccess.setVisible(false);
        typeError.setVisible(false);
        try{
            List<Post> posts = ps.readByRoomId(room_id);
            if (!posts.isEmpty()) {
                setPosts(posts);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Not Found !");
                alert.setContentText("No Posts for this Room");
                alert.showAndWait();
            }
        } catch(SQLException e){
        throw new RuntimeException(e);
    }
}

    public void setPosts(List<Post> posts) throws SQLException {
        System.out.println(room_id);
        ListPosts.getChildren().clear();
        for (Post value : posts) {
            FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/forum/AddPostCard.fxml"));
                try {
                AnchorPane card = fxmlLoader.load();
                AddPostCardController cardController = fxmlLoader.getController();
                cardController.setData(value);
                    ListPosts.getChildren().add(card);
                cardController.setUpController(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    void add(ActionEvent event) {
        try{
            if (!inputControl()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Invalid Input");
                alert.showAndWait();
                return;
            }
            //API to mask bad words
            BadWordDetection();
            //retrieve the ID of the user
            ps.create(new Post(room_id,authorTF.getText(),contentTF.getText(),img_path.getText(),user_id));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Post added");
            alert.showAndWait();
            for (TextInputControl textField : Arrays.asList(authorTF, contentTF,img_path)) {
                textField.setText("");
            }
        }catch(SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean inputControl(){
        if (verifyInputIsBlank(authorTF.getText()) < 0) {
            SubcatError.setVisible(true);
            SubcatSuccess.setVisible(false);
        } else {
            SubcatError.setVisible(false);
            SubcatSuccess.setVisible(true);
        }

        if (verifyInputIsBlank(contentTF.getText()) < 0) {
            descriptionError.setVisible(true);
            descriptionSuccess.setVisible(false);
        } else {
            descriptionError.setVisible(false);
            descriptionSuccess.setVisible(true);
        }
        // If any error labels are visible, do not proceed with submission
        return !SubcatError.isVisible() && !descriptionError.isVisible();
    }
    @FXML
    void translate(ActionEvent event) {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        try {
            for (TextInputControl textField : Arrays.asList(authorTF, contentTF)) {
                String targetLanguage = "fr";
                String textToTranslate = textField.getText();
                String requestBody = String.format("source_language=en&target_language=%s&text=%s", targetLanguage, URLEncoder.encode(textToTranslate, StandardCharsets.UTF_8));

                client.prepare("POST", "https://text-translator2.p.rapidapi.com/translate")
                        .setHeader("content-type", "application/x-www-form-urlencoded")
                        .setHeader("X-RapidAPI-Key", "77280ef4bfmsh5a1c660eaa091abp16b165jsned5b547e4d20")
                        .setHeader("X-RapidAPI-Host", "text-translator2.p.rapidapi.com")
                        .setBody(requestBody)
                        .execute()
                        .toCompletableFuture()
                        .thenAccept(response -> {
                            try {
                                String status = response.getStatusText();
                                String responseBody = response.getResponseBody();
                                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                                String translatedText = jsonObject.getAsJsonObject("data").get("translatedText").getAsString();
                                if ("OK".equals(status) && translatedText != null) {
                                    textField.setText(translatedText);
                                } else {
                                    System.out.println("Translation failed. Status: " + status);
                                }
                            } catch (JsonParseException e) {
                                System.err.println("Error parsing JSON response: " + e.getMessage());
                            } catch (Exception e) {
                                System.err.println("Error processing response: " + e.getMessage());
                            }
                        })
                        .exceptionally(ex -> {
                            System.err.println("Error executing request: " + ex.getMessage());
                            return null;
                        })
                        .join();
            }
        } catch (Exception e) {
            System.err.println("Error in translation: " + e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                System.err.println("Error closing HTTP client: " + e.getMessage());
            }
        }
    }


    private void BadWordDetection() throws IOException {
        String description = contentTF.getText();
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("POST", "https://profanity-cleaner-bad-word-filter.p.rapidapi.com/profanity")
                .setHeader("content-type", "application/json")
                .setHeader("X-RapidAPI-Key", "77280ef4bfmsh5a1c660eaa091abp16b165jsned5b547e4d20")
                .setHeader("X-RapidAPI-Host", "profanity-cleaner-bad-word-filter.p.rapidapi.com")
                .setBody("{\"text\": \"" + description + "\", \"maskCharacter\": \"x\", \"language\": \"en\"}")
                .execute()
                .toCompletableFuture()
                .thenApply(response -> {
                    try {
                        int status = response.getStatusCode();
                        String responseBody = response.getResponseBody();
                        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                        String maskedText = jsonObject.get("clean").getAsString();
                        if (status == 200 && maskedText != null){
                            contentTF.setText(maskedText);
                        }
                        return maskedText;
                    } catch ( JsonParseException e) {
                        System.err.println(e.getMessage());
                        return null;
                    }
                })
                .thenAccept(System.out::println)
                .join();
        client.close();
    }

    @FXML
    void refresh(ActionEvent event) throws SQLException {
        initialize(room_id);
    }



}
