package controllers.events;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import models.events.Event;
import services.events.EventService;

import java.sql.SQLException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.events.Event;
import services.events.EventService;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import models.events.Event;
import services.events.EventService;

import java.sql.SQLException;
import java.util.List;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
           // translateContent();
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

    public VBox createEventBox(Event event) {
        // Create labels for event information
        Label nameLabel = new Label("Name: " + event.getName());
        Label descriptionLabel = new Label("Description: " + event.getDescription());
        Label dateLabel = new Label("Date: " + event.getDate());
        Label locationLabel = new Label("Location: " + event.getLocation());

        // Style labels
        nameLabel.getStyleClass().add("event-info-label");
        descriptionLabel.getStyleClass().add("event-info-label");
        dateLabel.getStyleClass().add("event-info-label");
        locationLabel.getStyleClass().add("event-info-label");

        // Create ImageView
        ImageView imageView = new ImageView();

        // Load image from file
        try {
            String imagePath = "file:///C:\\Users\\user\\Desktop\\Nouveau dossier (4)\\Flayes-Flayes\\public\\img\\"  + event.getImage();
            Image image = new Image(imagePath);
            imageView.setImage(image);
            // Set dimensions of image view
            imageView.setFitWidth(150); // Set width of image
            imageView.setFitHeight(100); // Set height of image
        } catch (IllegalArgumentException e) {
            e.printStackTrace(); // Log the exception
            // Set a default image or handle the exception as needed
        }

        // Create labels box and set alignment and spacing
        VBox labelsBox = new VBox(nameLabel, descriptionLabel, dateLabel, locationLabel);
        labelsBox.setAlignment(Pos.TOP_LEFT);
        labelsBox.setSpacing(5);

        // Create button for viewing details
        Button detailsButton = new Button("View Details");
        detailsButton.setOnAction(e -> openNextPage(event.getIdevent()));

        // Create border pane for content layout
        BorderPane contentPane = new BorderPane();
        contentPane.setLeft(imageView);
        contentPane.setCenter(labelsBox);
        contentPane.setBottom(detailsButton);

        // Apply style to the content pane
        contentPane.setStyle("-fx-border-color: blue; -fx-border-width: 2px; -fx-border-radius: 5px;");

        // Create event box and set alignment and spacing
        VBox eventBox = new VBox(contentPane);
        eventBox.getStyleClass().add("eventBox");
        eventBox.setAlignment(Pos.CENTER);
        eventBox.setSpacing(10);



        return eventBox;
    }

}