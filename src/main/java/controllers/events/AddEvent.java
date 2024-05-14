package controllers.events;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.events.Category;
import services.events.CatService;
import services.events.EventService;
import models.events.Event;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import javafx.scene.control.Alert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class AddEvent {

    private final EventService ps = new EventService();
    private ObservableList<Event> observableList;
    private ObservableList<Event> catlist;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField date;

    @FXML
    private Label namee;

    @FXML
    private TextField loc;

    @FXML
    private TableColumn<?, ?> namec;

    @FXML
    private TextField searchField;

    @FXML
    private Tab Catevent;

    @FXML
    private TextField Idcat;

    @FXML
    private Label namee1;

    @FXML
    private TextField description;

    @FXML
    private TableColumn<?, ?> Idcatc;

    @FXML
    private TextField type;

    @FXML
    private TableView<Category> tablec;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private Label Idcatt;

    @FXML
    private Label datee;

    @FXML
    private TableColumn<?, ?> datec;

    @FXML
    private TableColumn<?, ?> qrcodec;

    @FXML
    private TableColumn<?, ?> descriptionc;

    @FXML
    private TableView<Event> table;

    @FXML
    private TextField image;

    @FXML
    private TextField searchField1;

    @FXML
    private TableColumn<?, ?> locationc;

    @FXML
    private Label datee1;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TextField target;

    @FXML
    private TableColumn<?, ?> imagec;

    @FXML
    private Tab Eventtab;

    @FXML
    private TableColumn<?, ?> targetColumn;

    @FXML
    private TextField name;

    @FXML
    private TextField namecat;
    @FXML
    void Add(ActionEvent event) {
        try {
            String inputDate = date.getText();
            // Check if the date format is correct (dd/mm/yy)
            if (!isValidDateFormat(inputDate)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter the date in the format dd/mm/yy");
                alert.showAndWait();
                return; // Exit the method if the date format is incorrect
            }
            CatService cat=new CatService();
            // Fetch category name based on category ID
            String categoryName = cat.getName(Integer.parseInt(Idcat.getText()));

            // Generate a unique QR code filename
            String qrCodeFilename = generateUniqueQRCodeFilename();

            // Generate QR code data
            String qrCodeData = generateQRCodeData(
                    name.getText(),
                    inputDate,
                    description.getText(),
                    loc.getText(),
                    image.getText(),
                    categoryName
            );

            // Generate QR code image
            generateQRCodeImage(qrCodeData, qrCodeFilename);

            // Save event with QR code filename
            ps.create(new Event(
                    name.getText(),
                    inputDate,
                    description.getText(),
                    loc.getText(),
                    image.getText(),
                    Integer.parseInt(Idcat.getText()),
                    qrCodeFilename
            ));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Event added successfully");
            observableList.add(new Event(
                    name.getText(),
                    inputDate,
                    description.getText(),
                    loc.getText(),
                    image.getText(),
                    Integer.parseInt(Idcat.getText()),
                    qrCodeFilename
            ));
            alert.showAndWait();
        } catch (SQLException | IOException | WriterException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }




    private String generateQRCodeData(String name, String date, String description, String location, String image, String categoryName) {
        // Construct the data string containing all attributes, including category name
        return "Event Name: " + name + "\n" +
                "Date: " + date + "\n" +
                "Description: " + description + "\n" +
                "Location: " + location + "\n" +
                "Image: " + image + "\n" +
                "Category: " + categoryName;
    }



    private String generateUniqueQRCodeFilename() {
        // Generate a unique filename based on current timestamp
        return "qrcode_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".png";
    }

    private void generateQRCodeImage(String qrCodeData, String filename) throws IOException, WriterException {
        // Generating QR code image using ZXing library
        int width = 300;
        int height = 300;
        String fileType = "png";

        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCodeData, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, fileType, outputStream);
        byte[] qrCodeBytes = outputStream.toByteArray();

        // Save QR code image to file
        saveToResources(qrCodeBytes, filename);
        saveToServer(qrCodeBytes, filename);
    }

    private void saveToResources(byte[] qrCodeBytes, String filename) throws IOException {
        // Save QR code image to resources directory
        String resourcesPath = getResourcesPath();
        String qrCodeDirectory = resourcesPath + File.separator + "qr_code";
        Files.createDirectories(Paths.get(qrCodeDirectory));
        Files.write(Paths.get(qrCodeDirectory + File.separator + filename), qrCodeBytes);
    }

    private void saveToServer(byte[] qrCodeBytes, String filename) throws IOException {
        // Save QR code image to server directory (htdocs/qr)
        String serverPath = getServerPath();
        String qrDirectory = serverPath + File.separator + "qr";
        Files.createDirectories(Paths.get(qrDirectory));
        Files.write(Paths.get(qrDirectory + File.separator + filename), qrCodeBytes);
    }

    private String getResourcesPath() {
        return new File("src/main/resources").getAbsolutePath();
    }

    private String getServerPath() {
        String serverPath = "";
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            serverPath = "C:/xampp/htdocs";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            serverPath = "/opt/lampp/htdocs";
        }
        return serverPath;
    }

    private boolean isValidDateFormat(String date) {
        // Regular expression to match "dd/mm/yyyy" format
        String regex = "\\d{2}/\\d{2}/\\d{4}";
        return date.matches(regex);
    }



    @FXML
    void deleteevent(ActionEvent event) {
        try {
            int myIndex = table.getSelectionModel().getSelectedIndex();
            if (myIndex != -1) {
                Event selected = ((Event) table.getItems().get(myIndex));
                int id =  selected.getIdevent();
                EventService eventService=new EventService();
                eventService.delete(id);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Event deleted successfully");
                alert.showAndWait();

                observableList.remove(myIndex);
            }
        } catch (SQLException e) {
            showAlert("Error", "Error Deleting Event", e.getMessage());
        }
    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void update(ActionEvent event) {
        int myIndex = table.getSelectionModel().getSelectedIndex();
        if (myIndex != -1) { // Check if an item is selected
            try {
                // Retrieve selected event from the table
                Event selectedEvent = (Event) table.getItems().get(myIndex);

                // Check if the date format is correct (dd/mm/yyyy)
                if (!isValidDateFormat(date.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Date Format");
                    alert.setContentText("Please enter the date in the format dd/mm/yyyy");
                    alert.showAndWait();
                    return; // Exit the method if the date format is incorrect
                }

                // Update the event with data from the form fields
                selectedEvent.setName(name.getText());
                selectedEvent.setDate(date.getText());
                selectedEvent.setDescription(description.getText());
                selectedEvent.setImage(image.getText());
                selectedEvent.setLocation(loc.getText());

                // Generate a new QR code path based on the updated event information
                String newQRCodePath = generateQRCode(selectedEvent);

                // Update the event object with the new QR code path
                selectedEvent.setQrcode(newQRCodePath);

                // Parse the category ID if it's provided
                if (!Idcat.getText().isEmpty()) {
                    try {
                        selectedEvent.setIdcat(Integer.parseInt(Idcat.getText()));
                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Invalid Category ID");
                        alert.setContentText("Category ID must be a valid integer");
                        alert.showAndWait();
                        return; // Exit the method if the category ID is not a valid integer
                    }
                }

                // Update the event in the database
                ps.update(selectedEvent);

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Event Updated");
                alert.setContentText("Event updated successfully!");
                alert.showAndWait();

                // Refresh the table
                initialize();
            } catch (Exception ex) {
                // Handle exceptions
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Update Failed");
                alert.setContentText("An error occurred while updating the event: " + ex.getMessage());
                alert.showAndWait();
            }
        } else {
            // Show an error message if no item is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Event Selected");
            alert.setContentText("Please select an event to update.");
            alert.showAndWait();
        }
    }

    // Method to generate QR code and return the file path
    private String generateQRCode(Event event) throws IOException, WriterException {
        String qrCodeData = generateQRCodeData(event.getName(), event.getDate(), event.getDescription(), event.getLocation(), event.getImage(), "oops");
        String qrCodeFilename = generateUniqueQRCodeFilename();
        generateQRCodeImage(qrCodeData, qrCodeFilename);
        return qrCodeFilename;
    }

    @FXML
    void initialize() {
        initializeCategoryTab();
        initializeEventTab();

        // Add listener for selection changes in the category table
        tablec.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Fill the category form fields with the selected category's information
                namecat.setText(newValue.getName());
                type.setText(newValue.getType());
                target.setText(newValue.getTarget());
            } else {
                // Clear the category form fields when no item is selected
                namecat.clear();
                type.clear();
                target.clear();
            }
        });

        // Add listener for selection changes in the event table
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Fill the event form fields with the selected event's information
                name.setText(newValue.getName());
                date.setText(newValue.getDate());
                description.setText(newValue.getDescription());
                loc.setText(newValue.getLocation());
                image.setText(newValue.getImage());
                Idcat.setText(String.valueOf(newValue.getIdcat()));
            } else {
                // Clear the event form fields when no item is selected
                name.clear();
                date.clear();
                description.clear();
                loc.clear();
                image.clear();
                Idcat.clear();
            }
        });

        // Add listener for filtering events table
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterEventTable(newValue);
        });

        // Add listener for filtering categories table
        searchField1.textProperty().addListener((observable, oldValue, newValue) -> {
            filterCategoryTable(newValue);
        });
    }

    private void filterEventTable(String keyword) {
        if (observableList != null) {
            if (keyword.isEmpty()) {
                table.setItems(observableList); // Show all data if search field is empty
            } else {
                // Filter data based on keyword
                ObservableList<Event> filteredList = observableList.filtered(event ->
                        event.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                                event.getDescription().toLowerCase().contains(keyword.toLowerCase()) ||
                                event.getLocation().toLowerCase().contains(keyword.toLowerCase()) ||
                                String.valueOf(event.getIdcat()).equals(keyword) ||
                                event.getQrcode().toLowerCase().contains(keyword.toLowerCase()) ||
                                event.getImage().toLowerCase().contains(keyword.toLowerCase())
                );
                table.setItems(filteredList);
            }
        }
    }

    private void filterCategoryTable(String keyword) {
        if (categoryObservableList != null) {
            if (keyword.isEmpty()) {
                tablec.setItems(categoryObservableList); // Show all data if search field is empty
            } else {
                // Filter data based on keyword
                ObservableList<Category> filteredList = categoryObservableList.filtered(category ->
                        category.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                                category.getType().toLowerCase().contains(keyword.toLowerCase()) ||
                                category.getTarget().toLowerCase().contains(keyword.toLowerCase())
                );
                tablec.setItems(filteredList);
            }
        }
    }

    private void initializeCategoryTab() {
        try {
            CatService catService = new CatService();
            List<Category> categoryList = catService.read();

            // Ensure that the class-level observable list is initialized
            if (categoryObservableList == null) {
                categoryObservableList = FXCollections.observableArrayList();
            }

            // Clear the existing items and add the new category list
            categoryObservableList.clear();
            categoryObservableList.addAll(categoryList);

            // Set the items for the table
            tablec.setItems(categoryObservableList);

            // Set up cell value factories for each column
            TableColumn<Category, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Category, String> typeColumn = new TableColumn<>("Type");
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

            TableColumn<Category, String> targetColumn = new TableColumn<>("Target");
            targetColumn.setCellValueFactory(new PropertyValueFactory<>("target"));

            // Add the columns to the table
            tablec.getColumns().setAll(nameColumn, typeColumn, targetColumn);
        } catch (SQLException e) {
            showAlertt("Error", "Error Loading Categories", e.getMessage());
        }
    }

    private void showAlertt(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void initializeEventTab() {
        try {
            List<Event> eventList = ps.read();
            observableList = FXCollections.observableArrayList(eventList); // Initialize observableList here
            table.setItems(observableList);
            namec.setCellValueFactory(new PropertyValueFactory<>("name"));
            datec.setCellValueFactory(new PropertyValueFactory<>("date"));
            descriptionc.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationc.setCellValueFactory(new PropertyValueFactory<>("location"));
            Idcatc.setCellValueFactory(new PropertyValueFactory<>("idcat"));
            imagec.setCellValueFactory(new PropertyValueFactory<>("image"));
            qrcodec.setCellValueFactory(new PropertyValueFactory<>("qrcode"));
        } catch (SQLException e) {
            showAlert("Error", "Error Loading Events", e.getMessage());
        }
    }

    private void filterTable(String keyword) {
        if (keyword.isEmpty()) {
            table.setItems(observableList); // Show all data if search field is empty
        } else {
            // Filter data based on keyword
            ObservableList<Event> filteredList = observableList.filtered(event -> {
                String idcatString = String.valueOf(event.getIdcat());
                System.out.println("Keyword: " + keyword + ", idcat: " + idcatString);
                return event.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        event.getDescription().toLowerCase().contains(keyword.toLowerCase()) ||
                        event.getLocation().toLowerCase().contains(keyword.toLowerCase()) ||
                        idcatString.equals(keyword) ||  event.getQrcode().toLowerCase().contains(keyword.toLowerCase())||
                        event.getImage().toLowerCase().contains(keyword.toLowerCase());
            });
            table.setItems(filteredList);
        }
    }

    @FXML
    void catp(ActionEvent event) {
        try {
            //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowUser.fxml"));
            //  Parent root= loader.load();

            Parent root = FXMLLoader.load(getClass().getResource("/Crudcat.fxml"));
            loc.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println("error"+e.getMessage());
        }

    }


    @FXML
    void evntp(ActionEvent event) {
        try {
            //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowUser.fxml"));
            //  Parent root= loader.load();

            Parent root = FXMLLoader.load(getClass().getResource("/Crudevent.fxml"));
            loc.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println("error"+e.getMessage());
        }
    }

    @FXML
    void betp(ActionEvent event) {

    }

    @FXML
    void resultp(ActionEvent event) {

    }

    @FXML
    void ticketp(ActionEvent event) {

    }

    @FXML
    void updateCategory(ActionEvent event) {
        int myIndex = tablec.getSelectionModel().getSelectedIndex(); // Ensure you're using the correct table
        if (myIndex != -1) { // Check if an item is selected
            try {
                // Retrieve selected category from the table
                Category selectedCategory = tablec.getItems().get(myIndex); // Ensure you're using the correct table

                // Validate the category type
                String updatedType = type.getText().toUpperCase(); // Convert to uppercase for case-insensitive comparison
                if (!isValidCategoryType(updatedType)) {
                    showAlertt(String.valueOf(Alert.AlertType.ERROR), "Error", "No Category Selected");
                    return; // Exit the method if the category type is invalid
                }

                // Update the category with data from the form fields
                selectedCategory.setName(namecat.getText()); // Use namecat instead of name
                selectedCategory.setType(updatedType);
                selectedCategory.setTarget(target.getText());

                // Update the category in the database
                CatService catService = new CatService(); // Initialize CatService if not already done
                catService.update(selectedCategory);

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Category Updated");
                alert.setContentText("Category updated successfully!");
                alert.showAndWait();

                // Refresh the table
                initializeCategoryTab(); // Refresh category table instead of the entire view
            } catch (Exception ex) {
                // Handle exceptions
                showInformationAlert("Error", "No Category Selected");
            }
        } else {
            // Show an error message if no item is selected
            showInformationAlert("Error", "No Category Selected");
        }
    }


    @FXML
    void deleteCategory(ActionEvent event) {

    }

    @FXML
    void f5efef(ActionEvent event) {

    }

    @FXML
    void eeeeee(ActionEvent event) {

    }
    private boolean isValidCategoryType(String type) {
        return type.equals("BET") || type.equals("TICKET");
    }
    private ObservableList<Category> categoryObservableList;

    @FXML
    void addCategory() {
        try {
            String categoryName = namecat.getText().trim();
            String categoryType = type.getText().toUpperCase().trim(); // Convert to uppercase for case-insensitive comparison
            String categoryTarget = target.getText().trim();

            // Validate the category type
            if (!isValidCategoryType(categoryType)) {
                showAlertt("Error", "Category type must be either 'BET' or 'TICKET'", "");
                return; // Exit the method if the category type is invalid
            }

            Category newCategory = new Category(categoryName, categoryType, categoryTarget);
            CatService catService = new CatService();
            catService.create(newCategory);

            // Ensure categoryObservableList is initialized before adding a new category
            if (categoryObservableList == null) {
                categoryObservableList = FXCollections.observableArrayList();
            }

            // Add the new category to the observable list
            categoryObservableList.add(newCategory);

            // Show success alert
            showInformationAlert("Success", "Category added successfully");
        } catch (SQLException e) {
            showAlertt("Error", e.getMessage(), "");
        }
    }


    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}