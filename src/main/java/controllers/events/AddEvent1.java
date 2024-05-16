package controllers.events;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.events.Event;
import services.events.EventService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AddEvent1 implements Initializable {

    @FXML
    private TableView<Event> table;

    @FXML
    private TableColumn<Event, String> colonneName;

    @FXML
    private TableColumn<Event, String> colonneDate;

    @FXML
    private TableColumn<Event, String> colonneDescription;

    @FXML
    private TableColumn<Event, String> colonneLocation;

    @FXML
    private TableColumn<Event, Integer> colonneIdcat;

    @FXML
    private TableColumn<Event, String> colonneImage;

    @FXML
    private TableColumn<Event, String> colonneQrcode;

    private final EventService eventService;

    public AddEvent1() {
        this.eventService = new EventService();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<Event> eventList = eventService.read();
            ObservableList<Event> observableList = FXCollections.observableArrayList(eventList);

            table.setItems(observableList);

            // Setup table columns
            colonneName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colonneDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colonneDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colonneLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            colonneIdcat.setCellValueFactory(new PropertyValueFactory<>("idcat"));
            colonneImage.setCellValueFactory(new PropertyValueFactory<>("image"));
            colonneQrcode.setCellValueFactory(new PropertyValueFactory<>("qrcode"));
        } catch (SQLException e) {
            showAlert("Error", "Error Loading Events", e.getMessage());
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
