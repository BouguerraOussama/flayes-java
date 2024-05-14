package controllers.forum;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.forum.Room;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import services.forum.RoomService;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.asynchttpclient.*;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.TextField;

public class AddRoomController {

    private final RoomService rs = new RoomService();
    private static Timer searchTimeoutToken;

    private int Room_id;
    public Label cat;
    public Label subcat;
    public Label desc;

    @FXML
    private TextArea descriptionTF = new TextArea();

    @FXML
    private ChoiceBox<String> categoryTF = new ChoiceBox<>();

    @FXML
    private TextField sub_categoryTF = new TextField();

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
    private ImageView room_type_icon;

    @FXML
    private VBox Investment_type_Pane;

    @FXML
    private PieChart RoomStatistics = new PieChart();

    @FXML
    private Button AddRoomBtn;

    @FXML
    private Button UpdateRoom;

    @FXML
    private Button DeleteRoom;

    @FXML
    private Button TranslateBtn;

    @FXML
    private Button ViewStatisticsBtn;

    @FXML
    private Button ViewPostsBtn;

    private Button activeButton;
    @FXML
    private VBox Buttons;


    private ObservableList<Room> ObservableList;
    @FXML
    private TableView<Room> RoomTableView;

    @FXML
    private TableColumn<Room, String> CategoryCol;

    @FXML
    private TableColumn<Room, String> SubCategoryCol;

    @FXML
    private TableColumn<Room, String> DescriptionCol;


    ObservableList<String> options = FXCollections.observableArrayList(
            "Finance",
            "Agriculture",
            "Industry",
            "Art",
            "Business",
            "Manufacturing",
            "Education"
    );

    @FXML
    void initialize() {
        Investment_type_Pane.getChildren().clear();
        SubcatError.setVisible(false);
        SubcatSuccess.setVisible(false);
        descriptionError.setVisible(false);
        descriptionSuccess.setVisible(false);
        typeSuccess.setVisible(false);
        typeError.setVisible(false);
        try{
            List<Room> RoomList = rs.read();
            ObservableList = FXCollections.observableList(RoomList);
            RoomTableView.setItems(ObservableList);
            CategoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
            SubCategoryCol.setCellValueFactory(new PropertyValueFactory<>("sub_category"));
            DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ViewStatisticsBtn.setOnAction(event -> {
            try {
                ViewStatistics();
            } catch (SQLException | InvocationTargetException e) {
                e.printStackTrace();
                // Handle the exception
            }
        });
    }

    public void setRoom_Id(int id){
        this.Room_id = id;
    }
    public  Button getAddRoomClicked(){return AddRoomBtn;}
    public Button getTranslateBtnClicked(){return TranslateBtn;}
    public Button getViewStatisticsClicked(){return ViewStatisticsBtn;}
    public ImageView getRoom_type_icon(){
        return room_type_icon;
    }
    public Button getRoomDeleted(){return DeleteRoom;}
    public Button getRoomUpdated(){return UpdateRoom;}

    public Button getViewStatisticsBtn(){return ViewPostsBtn;}


    @FXML
    void AddRoom(ActionEvent event) {
        try {
            if (!inputControl()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Invalid Input !");
                alert.showAndWait();
                return;
            }
            // Get the selected category
            String selectedCategory = categoryTF.getValue();
            // Import picture based on selected category
            ImportPicture(room_type_icon);
            // Create and save the room
            rs.create(new Room(selectedCategory, sub_categoryTF.getText(), descriptionTF.getText()));

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Room added");
            alert.showAndWait();

            // Reset category to default and clear text fields
            categoryTF.setValue("Finance");
            for (TextInputControl textField : Arrays.asList(sub_categoryTF, descriptionTF)) {
                textField.setText("");
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    void ImportPicture(ImageView view) {
        // Map category names to image paths
        Map<String, String> categoryImageMap = new HashMap<>();
        categoryImageMap.put("Finance", "/Users/khalil/Desktop/Java Projects/Forum/Flayes-Flayes-/src/main/resources/images/Finance.jpg");
        categoryImageMap.put("Agriculture", "/Users/khalil/Desktop/Java Projects/Forum/Flayes-Flayes-/src/main/resources/images/agriculture.jpg");
        categoryImageMap.put("Industry", "/Users/khalil/Desktop/Java Projects/Forum/Flayes-Flayes-/src/main/resources/images/Industry.jpg");
        categoryImageMap.put("Art", "/Users/khalil/Desktop/Java Projects/Forum/Flayes-Flayes-/src/main/resources/images/art.jpg");
        categoryImageMap.put("Business", "/Users/khalil/Desktop/Java Projects/Forum/Flayes-Flayes-/src/main/resources/images/business.jpg");
        categoryImageMap.put("Manufacturing", "/Users/khalil/Desktop/Java Projects/Forum/Flayes-Flayes-/src/main/resources/images/manufacturing.jpg"); // Assuming this is correct
        categoryImageMap.put("Education", "/Users/khalil/Desktop/Java Projects/Forum/Flayes-Flayes-/src/main/resources/images/education.jpg");

        // Get the selected category
        String selectedCategory = categoryTF.getValue();

        // Check if the selected category exists in the map
        if (selectedCategory != null && categoryImageMap.containsKey(selectedCategory)) {
            String path = categoryImageMap.get(selectedCategory);
            File file = new File(path);
            Image image = new Image(file.toURI().toString());
            view.setImage(image);
        } else {
            // Handle the case where the selected category is not found in the map
            System.out.println("Invalid category");
        }
    }



    private boolean inputControl(){
        if (sub_categoryTF.getText().isBlank()) {
            SubcatError.setVisible(true);
            SubcatSuccess.setVisible(false);
        } else {
            SubcatError.setVisible(false);
            SubcatSuccess.setVisible(true);
        }

        if (descriptionTF.getText().isBlank()) {
            descriptionError.setVisible(true);
            descriptionSuccess.setVisible(false);
        } else {
            descriptionError.setVisible(false);
            descriptionSuccess.setVisible(true);
        }
        // If any error labels are visible, do not proceed with submission
        return !SubcatError.isVisible() && !descriptionError.isVisible() && (sub_categoryTF.getText().matches("^[a-zA-Z]*$"));
    }

    private void setActive(Button button) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("btn_active");
        }
        button.getStyleClass().add("btn_active");
        activeButton = button;
    }

    private void BadWordDetection() throws IOException {
        String description = descriptionTF.getText();
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
                            descriptionTF.setText(maskedText);
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
    void translate(ActionEvent event) throws IOException {
        AsyncHttpClient client;
        client = new DefaultAsyncHttpClient();
        for (TextInputControl textField : Arrays.asList(sub_categoryTF, descriptionTF)) {
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
                    .thenApply(response -> {
                        try {
                            String status = response.getStatusText();
                            String responseBody = response.getResponseBody();
                            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                            String translatedText = jsonObject.getAsJsonObject("data").get("translatedText").getAsString();
                            if (status.equals("OK") && translatedText!= null){
                                textField.setText(translatedText);
                            }
                            return translatedText;
                        } catch (JsonParseException e) {
                            System.err.println(e.getMessage());
                            return null;
                        }
                    })
                    .thenAccept(System.out::println)
                    .join();
        }
        client.close();

    }

//    @FXML
//    void AutoSuggestion() {
//        descriptionTF.setOnKeyTyped(keyEvent -> {
//            String input = keyEvent.getCharacter();
//            if (!input.isEmpty()) { // Check if input is not empty (not a delete key)
//                AsyncHttpClient client = new DefaultAsyncHttpClient();
//                client.prepare("GET", "https://auto-suggest-queries.p.rapidapi.com/suggestqueries?query=+"+input+"%20to")
//                        .setHeader("X-RapidAPI-Key", "77280ef4bfmsh5a1c660eaa091abp16b165jsned5b547e4d20")
//                        .setHeader("X-RapidAPI-Host", "auto-suggest-queries.p.rapidapi.com")
//                        .execute()
//                        .toCompletableFuture()
//                        .thenAccept(response -> {
//                            try {
//                                int status = response.getStatusCode();
//                                if (status == 200) {
//                                    String responseBody = response.getResponseBody();
//                                    JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
//                                    if (!jsonArray.isEmpty()) {
//                                        String firstSuggestion = jsonArray.get(0).getAsString();
//                                        System.out.println("First suggestion: " + firstSuggestion);
//                                        Platform.runLater(() -> descriptionTF.setText(firstSuggestion));
//                                    } else {
//                                        System.out.println("No suggestions found.");
//                                    }
//                                } else {
//                                    System.out.println("HTTP error: " + status);
//                                }
//                            } catch (JsonParseException e) {
//                                System.err.println("Error processing response: " + e.getMessage());
//                            }
//                        })
//                        .exceptionally(ex -> {
//                            System.err.println("Error executing request: " + ex.getMessage());
//                            return null;
//                        })
//                        .thenRun(()->{
//                            try{
//                                client.close();
//                            }catch(IOException ex){
//                                try {
//                                    throw new IOException();
//                                } catch (IOException e) {
//                                    throw new RuntimeException(e);
//                                }
//                            }
//                        });
//            }
//        });
//    }

    @FXML
    void AutoSuggestion(){
        descriptionTF.setOnKeyReleased(keyEvent -> {
            String input = descriptionTF.getText().trim(); // Get text from TextField
            if (!input.isEmpty()) {
                if (searchTimeoutToken != null) {
                    searchTimeoutToken.cancel(); // Cancel previous timer if exists
                }
                searchTimeoutToken = new Timer();
                searchTimeoutToken.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> autoSuggestion(input)); // Run autosuggest after delay
                    }
                }, 250); // Delay of 250 milliseconds
            }
        });
    }

    public void autoSuggestion(String input) {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        try {
            Response response = client.prepare("GET", "https://auto-suggest-queries.p.rapidapi.com/suggestqueries")
                    .addQueryParam("query", input)
                    .addHeader("X-RapidAPI-Key", "77280ef4bfmsh5a1c660eaa091abp16b165jsned5b547e4d20")
                    .addHeader("X-RapidAPI-Host", "auto-suggest-queries.p.rapidapi.com")
                    .execute()
                    .toCompletableFuture()
                    .get();
            int status = response.getStatusCode();
            if (status == 200) {
                String responseBody = response.getResponseBody();
                String suggestion = responseBody.split("\"")[3];
                System.out.println("Suggestion: " + suggestion);
                Platform.runLater(() -> descriptionTF.setText(suggestion));
            } else {
                System.out.println("HTTP error: " + status);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }




    @FXML
    void ViewStatistics() throws SQLException, InvocationTargetException {
        try {
            List<Room> roomList = rs.read();
            if (roomList != null) {
                // Group rooms by category
                Map<String, List<Room>> roomsByCategory = roomList.stream()
                        .collect(Collectors.groupingBy(Room::getCategory));

                // Take the 3 longest lists
                List<List<Room>> threeLongestLists = roomsByCategory.values().stream()
                        .sorted((l1, l2) -> l2.size() - l1.size())
                        .limit(3)
                        .toList();

                // Print the 3 longest lists
                threeLongestLists.forEach(list -> {
                    System.out.println("Category: " + list.get(0).getCategory());
                    System.out.println("Number of rooms: " + list.size());
                    System.out.println("Rooms: " + list);
                });

                // Create a new PieChart instance
                PieChart roomStatistics = new PieChart();

                // Add data to the pie chart
                for (List<Room> list : threeLongestLists) {
                    String category = list.get(0).getCategory();
                    int numberOfRooms = list.size();
                    roomStatistics.getData().add(new PieChart.Data(category, numberOfRooms));
                    RoomStatistics.getData().add(new PieChart.Data(category, numberOfRooms));
                }
                RoomStatistics.toFront();
                // Set up the stage
                Stage primaryStage = new Stage();
                // Set up the scene
                Scene scene = new Scene(roomStatistics, 600, 400);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Top 3 Rooms Pie Chart");
                primaryStage.show();
            }
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @FXML
    public void deleteRoom(ActionEvent actionEvent) {
        RoomTableView.setItems(ObservableList);
        try {
            int myIndex = RoomTableView.getSelectionModel().getSelectedIndex();
            Room selectedEle =  RoomTableView.getSelectionModel().getSelectedItem();
            if (myIndex != -1) { // Check if an item is selected
                int id = RoomTableView.getSelectionModel().getSelectedItem().getRoom_id();
                rs.delete(id);
                RoomTableView.getItems().remove(myIndex);
                ObservableList.remove(selectedEle);
                final int newIndex = Math.max(0, myIndex - 1);
                RoomTableView.getSelectionModel().select(newIndex);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Event deletes successfully");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Handle the SQL exception here
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("An error occurred while deleting the event: " + e.getMessage());
            alert.showAndWait();
        }
    }

//    public void SetRowHoverEffect(){
//        RoomTableView.setRowFactory(tv -> {
//            TableRow<Room> row = new TableRow<>();
//            row.setOnMouseEntered(e -> {
//                if (!row.isEmpty()) {
//                    row.setStyle("-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.8), 10, 0, 0, 1);");
//                }
//            });
//            row.setOnMouseExited(e -> {
//                if (!row.isEmpty()) {
//                    row.setStyle(null);
//                }
//            });
//            return row;
//        });
//    }
    @FXML
    public void UpdateRoom(ActionEvent actionEvent) {
        final int selectedIdx = RoomTableView.getSelectionModel().getSelectedIndex();
        if (selectedIdx != -1) {
            Room SelectedItem = RoomTableView.getSelectionModel().getSelectedItem();
            final int Room_id = SelectedItem.getRoom_id();
            try {
                // Create a new stage for the "Update Room" window
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/forum/UpdateRoom.fxml"));
                Stage updateStage = new Stage();
                Parent root = fxmlLoader.load();
                UpdateRoomController controller = fxmlLoader.getController();
                controller.setSqlIdx(Room_id);
                controller.initData(SelectedItem);
                updateStage.setScene(new Scene(root));
                updateStage.setTitle("Update Room");
                updateStage.show();
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
    @FXML
    void ViewPosts(ActionEvent event){
        final int selectedIdx = RoomTableView.getSelectionModel().getSelectedIndex();
        if (selectedIdx != -1) {
            Room SelectedItem = RoomTableView.getSelectionModel().getSelectedItem();
            final int Room_id = SelectedItem.getRoom_id();
            try {
                // Create a new stage for the "Update Room" window
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/forum/AddPost.fxml"));
                Stage PostStage = new Stage();
                PostStage.initModality(Modality.APPLICATION_MODAL);
                Parent root = fxmlLoader.load();
                AddPostController controller = fxmlLoader.getController();
                controller.initialize(Room_id);
                PostStage.setScene(new Scene(root));
                PostStage.setTitle("Add Post");
                PostStage.show();
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void refresh(ActionEvent event) {
        initialize();
    }

}
