package controllers.users;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import services.users.UserService;
import utils.MyDataBase;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetPasswordController implements Initializable {
    @FXML
    private AnchorPane anchor1;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private TextField codetext;
    @FXML
    private PasswordField password;
    @FXML
    private ProgressBar progressbar;
    private Connection cnx;

    @FXML
    private PasswordField confirm_password;
    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;
    @FXML
    private TextField showpassword;

    @FXML
    private TextField showconfirmpassword;

    @FXML
    private Button login;
    private boolean imageSwitch1 = true;
    private boolean imageSwitch2 = true;

    private UserService userService = new UserService(); // Instance of UserService
    private String userEmail;

    // Getter method for the email variable
    public String getUserEmail() {
        return userEmail;
    }

    // Setter method for the email variable
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void exit() {
        System.exit(0);
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Retrieve the current user from the session manager
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Allow only digits (0-9)
                return change;
            }
            return null; // Reject any change that is not a digit
        };

        // Create a TextFormatter with the filter
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);

        // Apply the TextFormatter to the TextField
        codetext.setTextFormatter(textFormatter);
        anchor2.setVisible(false);

        showpassword.setManaged(false);
        showpassword.setVisible(false);

        showconfirmpassword.setManaged(false);
        showconfirmpassword.setVisible(false);
        password.textProperty().bindBidirectional(showpassword.textProperty());
        confirm_password.textProperty().bindBidirectional(showconfirmpassword.textProperty());
    }

    @FXML
    protected void onSubmitClick() {
        String code = codetext.getText();
        String verify;
        verify = userService.findResetTokenByEmail(userEmail);
        System.out.println("this is user email " + userEmail);

        // Check if the code matches the verification code
        if (verify != null && verify.equals(code)) {
            anchor2.setVisible(true);
            anchor1.setVisible(false);
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Invalid Code", "Error", "The reset code is incorrect.");
        }
    }



    @FXML
    void onChange(ActionEvent event) {
        String userPassword = password.getText();
        String confirmPassword = confirm_password.getText();
        double strength = calculatePasswordStrength(userPassword);

        if (strength < 0.7) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Password must be at least 8 characters long, include at least one number, one special character, and one uppercase letter.");
            return;
        }

        // Validate password match
        if (!userPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Passwords do not match.");
            return;
        }

        // Hash the password
        String hashedPassword = hashPassword(userPassword);

        // Check if hashing was successful
        if (hashedPassword == null) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Failed to hash the password.");
            return;
        }

        // Reset password with the hashed password
        userService.resetPassword(userEmail, hashedPassword);
        try {
            // Load the sign-up window FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/users/register.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();

            Scene scene = new Scene(root, 1230, 760);
            stage.setScene(scene);

            // Get the reference to the current window
            Stage currentStage = (Stage) anchor1.getScene().getWindow();

            // Close the current window
            currentStage.close();

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            // Handle the exception if the FXML file cannot be loaded
            showAlert(Alert.AlertType.ERROR, "Error", null, "Failed to load sign-up window.");
        }
    }
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private double calculatePasswordStrength(String password) {
        // Check if the password meets the minimum length requirement
        if (password.isEmpty()) {
            return 0.0; // Return zero strength if the password is too short
        }
        if (password.length() < 3 ) {
            return 0.1; // Return zero strength if the password is too short
        }
        if (password.length() < 8) {
            return 0.2; // Return zero strength if the password is too short
        }

        // Initialize counters for different types of characters
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        // Define the set of special characters
        String specialChars = "!@#$%^&*()-_=+[{]};:'\"|,<.>/?";

        // Loop through each character in the password
        for (char ch : password.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (specialChars.contains(String.valueOf(ch))) {
                hasSpecialChar = true;
            }
        }

        // Calculate the strength based on the presence of different types of characters
        int strength = 0;
        if (hasLowercase) strength++;
        if (hasUppercase) strength++;
        if (hasDigit) strength++;
        if (hasSpecialChar) strength++;

        // Return the strength as a percentage of the maximum possible strength
        return (double) strength / 4.0;
    }
    @FXML
    void loginon() throws IOException {

        // Load the sign-up window FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/bloodify/login.fxml"));
        Parent root = loader.load();

        // Create a new stage
        Stage stage = new Stage();
        stage.setTitle("Log in");
        stage.setScene(new Scene(root));

        // Apply the transition animation
        SignIn.SceneTransition.fadeTransition(stage.getScene(), stage);

        // Show the new stage
        stage.show();

        // Get the reference to the current window and close it
        Stage currentStage = (Stage) login.getScene().getWindow();
        currentStage.close();



    }

}