package controllers.events;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.events.Event;
import services.events.EventService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Ticketclient {
    @FXML
    private VBox eventContainer;

    private final EventService eventService = new EventService();
    private List<Event> events;
    private int currentIndex = 0;
    @FXML
    void initialize() {
        try {
            events = eventService.read(); // Assign the fetched events to the class-level variable
            displayEvents();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void displayEvents() {
        for (Event event : events) {
            VBox eventBox = createEventBox(event);
            eventContainer.getChildren().add(eventBox);
        }
    }


    VBox createEventBox(Event event) {
        Label nameLabel = new Label("Name: " + event.getName());
        nameLabel.getStyleClass().add("event-info-label");

        Label descriptionLabel = new Label("Description: " + event.getDescription());
        descriptionLabel.getStyleClass().add("event-info-label");

        Label dateLabel = new Label("Date: " + event.getDate());
        dateLabel.getStyleClass().add("event-info-label");

        Label locationLabel = new Label("Location: " + event.getLocation());
        locationLabel.getStyleClass().add("event-info-label");

        ImageView imageView = new ImageView();
        imageView.getStyleClass().add("eventImage");

        // Load image from file
        try {
            Image image = new Image(event.getImage());
            imageView.setImage(image);
            // Set dimensions of image view
            imageView.setFitWidth(100); // Set width of image
            imageView.setFitHeight(100); // Set height of image
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        VBox labelsBox = new VBox(nameLabel, descriptionLabel, dateLabel, locationLabel);
        labelsBox.setAlignment(Pos.TOP_LEFT); // Align labels to top left
        labelsBox.setSpacing(5);

        Button detailsButton = new Button("View Details");
        detailsButton.setOnAction(e -> openNextPage(event.getIdevent()));

        BorderPane contentPane = new BorderPane();
        contentPane.setLeft(imageView);
        contentPane.setCenter(labelsBox);
        contentPane.setBottom(detailsButton);

        // Apply blue border to the content pane
        contentPane.setStyle("-fx-border-color: blue; -fx-border-width: 2px; -fx-border-radius: 5px;");

        VBox eventBox = new VBox(contentPane);
        eventBox.getStyleClass().add("eventBox");
        eventBox.setAlignment(Pos.CENTER);
        eventBox.setSpacing(10);

        return eventBox;
    }


    void openNextPage(int eventId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/events/getticket.fxml"));
            Parent root = loader.load();

            getticket controller = loader.getController();

            // Call the initialize method directly and pass the event ID
            controller.initialize(eventId);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
