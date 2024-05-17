package controllers.events;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.users.User;
import services.users.UserService;
import models.events.Event;
import models.events.Ticket;
import services.events.EventService;
import services.events.ticketService;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.SessionManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

import static com.twilio.example.ValidationExample.ACCOUNT_SID;
import static com.twilio.example.ValidationExample.AUTH_TOKEN;
import static controllers.users.Profil.user;

public class getticket {
    @FXML
    private Button scanButton;
    @FXML
    private Pane myanchor;
    private final EventService pss = new EventService();

    @FXML
    private TableColumn<?, ?> imagec;

    @FXML
    private TableColumn<?, ?> namec;

    @FXML
    private TextField searchField;

    @FXML
    private Text titleeee;

    @FXML
    private Label datee;

    @FXML
    private TableColumn<?, ?> Idcatc;

    @FXML
    private TableColumn<?, ?> datec;

    @FXML
    private TableColumn<?, ?> locationc;
    @FXML
    private ImageView image;
    @FXML
    private Label description;
    @FXML
    private TableColumn<?, ?> descriptionc;

    @FXML
    private Label eventIdLabel;
    @FXML
    private TextField Idevent;

    @FXML
    private TableView<?> table;

    @FXML
    private TextField Iduser;
    @FXML
    private Label dateec;

    @FXML
    private Label locationcc;
    /*
        @FXML
        void Add(ActionEvent event) {
            try {
                // Get the text content of eventIdLabel and description
                String eventIdStr = eventIdLabel.getText();
                int eventId = Integer.parseInt(eventIdStr);
                String descriptionText = description.getText();
                String date=dateec.getText();
                String locations=locationcc.getText();

                // Ensure description is not empty
                if (descriptionText.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Description cannot be empty");
                    return;
                }

                // Generate a unique barcode path for each ticket
                String barcodePath = "C:\\Users\\Nourhene\\Desktop\\web data\\" + UUID.randomUUID().toString() + ".jpg";

                // Generate Code 128 barcode image
                String barcodeText = "TicketID: " + UUID.randomUUID().toString(); // Unique barcode text
                Code128Writer writer = new Code128Writer();
                BitMatrix matrix = writer.encode(barcodeText, BarcodeFormat.CODE_128, 500, 300);
                MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(barcodePath));
                System.out.println("Barcode created...");

                // Create an instance of ticketService
                ticketService ts = new ticketService(); // Ensure correct capitalization

                // Create a new Ticket object and pass it to the create method
                Ticket newTicket = new Ticket(eventId, 22, barcodePath);

                // Pass the required parameters to the create method
                ts.create(newTicket);

                // Generate PDF with the content using the instance, including the barcode
                ts.generatePDF(eventId, descriptionText, date, locations, barcodePath, barcodeText);

                // Display success alert
                showAlert(Alert.AlertType.INFORMATION, "Success", "PDF successfully generated!");
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid event ID format");
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate barcode: " + e.getMessage());
            } catch (Exception e) {
                // Handle other exceptions
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
            }
        }  */
// Remplacez ces valeurs par vos propres informations Twilio
    private static final String ACCOUNT_SID = "AC5a9bddf04d67b67e8965a8ffcdca0817";
    private static final String AUTH_TOKEN = "47da3d6838b63c14f79d7c2a1f3738f9";
    private static final String TWILIO_PHONE_NUMBER = "+14123247866";


    // Remplacez cette valeur par votre propre numéro de téléphone
    private static final String VOTRE_NUMERO = "+21621494353";

    @FXML
    private TextField messageTextField;

    @FXML
    private Button envoyerButton;
    private UserService us = new UserService();

    //   User user = us.getUserConnected();
    @FXML
    void Add(ActionEvent event) {
        try {
            // Get the text content of eventIdLabel and description
            String eventIdStr = eventIdLabel.getText();
            int eventId = Integer.parseInt(eventIdStr);
            String descriptionText = description.getText();
            String date = dateec.getText();
            String locations = locationcc.getText();

            // Ensure description is not empty
            if (descriptionText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Description cannot be empty");
                return;
            }

            // Generate a unique barcode filename
            String barcodeFileName = UUID.randomUUID().toString() + ".jpg";

            // Set the barcode path inside the resources directory
            String barcodePath = "C:/xampp/htdocs/barcode/" + barcodeFileName;

            // Generate Code 128 barcode image
            String barcodeText = "TicketID: " + UUID.randomUUID().toString(); // Unique barcode text
            Code128Writer writer = new Code128Writer();
            BitMatrix matrix = writer.encode(barcodeText, BarcodeFormat.CODE_128, 500, 300);
            Path path = Paths.get(barcodePath);

            // Ensure parent directories exist
            Files.createDirectories(path.getParent());

            // Write barcode image to file
            MatrixToImageWriter.writeToPath(matrix, "jpg", path);

            // Display success alert
            showAlert(Alert.AlertType.INFORMATION, "Success", "Barcode created: " + barcodeFileName);

            // Create an instance of TicketService
            ticketService ts = new ticketService();
            user = us.getUserById(SessionManager.getInstance().getUser_id());

            // Create a new Ticket object and pass it to the create method
            Ticket newTicket = new Ticket(eventId, user.getUser_id(), barcodePath);

            // Pass the required parameters to the create method
            ts.create(newTicket);

            // Generate PDF with the content using the instance, including the barcode
            ts.generatePDF(eventId, descriptionText, date, locations, barcodePath, barcodeText);

            // Display success alert
            showAlert(Alert.AlertType.INFORMATION, "Success", "PDF successfully generated!");

            // Send an SMS for ticket generation with event information
            sendSMSTicketGenerated(eventId, locations, descriptionText, date);

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid event ID format");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate barcode: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }



    private void sendSMSTicketGenerated(int eventId, String eventName, String eventDescription, String eventImage) {
        try {
            // Initialise Twilio with your credentials
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            // Construct the SMS message with event information
            String smsMessage = "Hello, this is Flayes and Flayes. Your ticket was successfully generated. Here are the details of the event:\n"
                    + "Location: " + eventName + "\n"
                    + "Description: " + eventDescription + "\n"
                    + "Date: " + eventImage;

            // Apply CSS styling to the message text
            String styledSmsMessage =
                    "**********\n" + // Top border
                            smsMessage + // Message content
                            "\n**********\n" + // Bottom border
                            "Flayes & Flayes APP founded 2024 Desktop app"; // Signature with custom styling

            // Send the SMS for ticket generation
            Message twilioMessage = Message.creator(
                            new PhoneNumber(VOTRE_NUMERO), // Your phone number
                            new PhoneNumber(TWILIO_PHONE_NUMBER), // Your Twilio number
                            styledSmsMessage) // Send the styled SMS message
                    .create();

            // Process the response of sending the SMS if necessary
            System.out.println("SID du message Twilio pour la génération du ticket : " + twilioMessage.getSid());

            // Send reminder SMS for the event a day before
            sendEventReminderSMS(eventDescription);

        } catch (Exception e) {
            // Handle exceptions
            System.err.println("Error sending SMS for ticket generation: " + e.getMessage());
        }
    }
    private void sendEventReminderSMS(String eventName) {
        try {
            // Retrieve the event date text
            String eventDateText = dateec.getText();

            // Parse the event date text into a LocalDate object using a DateTimeFormatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Adjusted pattern
            LocalDate eventDate = LocalDate.parse(eventDateText, formatter); // Parse the date text into a LocalDate object

            // Calculate the reminder date (e.g., a day before the event)
            LocalDate reminderDate = eventDate.minusDays(1);

            // Get today's date
            LocalDate today = LocalDate.now();

            // Check if today is the day before the event
            if (today.equals(reminderDate)) {
                // Initialize Twilio with your credentials
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

                // Send the reminder SMS for the event
                Message twilioMessage = Message.creator(
                                new PhoneNumber(VOTRE_NUMERO), // Your phone number
                                new PhoneNumber(TWILIO_PHONE_NUMBER), // Your Twilio number
                                "Reminder: Event '" + eventName + "' is happening tomorrow.")
                        .create();

                // Process the response of sending the SMS if necessary
                System.out.println("Twilio message SID for the event reminder: " + twilioMessage.getSid());
            }
        } catch (Exception e) {
            // Handle exceptions
            System.err.println("Error sending event reminder SMS: " + e.getMessage());
        }
    }


    public void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void deleteevent(ActionEvent event) {
        // Implement delete logic here
    }

    @FXML
    void update(ActionEvent event) {
        // Implement update logic here
    }

    @FXML
    public void initialize(int eventId) {
        try {
            Event event = pss.search(eventId);

            if (event != null) {
                String imagePath = "file:///C:\\Users\\user\\Desktop\\Nouveau dossier (4)\\Flayes-Flayes\\public\\img\\" + event.getImage();
                image.setImage(new Image(imagePath));

                eventIdLabel.setText(String.valueOf(event.getIdevent()));
                locationcc.setText(event.getLocation());
                dateec.setText(event.getDate());

                description.setText(event.getDescription());
            } else {
                System.out.println("Event not found for ID: " + eventId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle the SQL exception appropriately, e.g., show error message
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to initialize: " + ex.getMessage());
        }
    }

    @FXML
    private void scanBarcode() {
        // Open file chooser to select image containing barcode
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        Stage stage = (Stage) scanButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                // Read image file
                BufferedImage bufferedImage = ImageIO.read(selectedFile);

                // Decode barcode using ZXing
                BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                        new BufferedImageLuminanceSource(bufferedImage)));
                Result result = new MultiFormatReader().decode(binaryBitmap);

                // Display barcode result
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Barcode Scanned");
                alert.setHeaderText(null);
                alert.setContentText("Barcode Text: " + result.getText());
                alert.showAndWait();
            } catch (NotFoundException | IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error scanning barcode: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
