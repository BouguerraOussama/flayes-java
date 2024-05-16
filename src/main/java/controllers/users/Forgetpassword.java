package controllers.users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import services.users.UserService;



public class Forgetpassword {

    @FXML
    private TextField emailfield;
    @FXML
    private Button sendbtn;
    @FXML
    private Button login;
    private UserService userService = new UserService(); // Instance of UserService



    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        String email = emailfield.getText();
        if (userService.emailExists(email)) {
            int randomNum = generateRandomNumber();
            userService.addToken(email, String.valueOf(randomNum));
            System.out.println("Random number: " + randomNum);
            userService.mail(email,String.valueOf(randomNum));
            // Store the email in a variable
            String user_email = email;

            // Load the FXML file for the second scene

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/users/passwordreset.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Reset Password");
                stage.show();
                Stage currentStage = (Stage) emailfield.getScene().getWindow();
                currentStage.close();

                // Get the controller associated with the second scene
                ResetPasswordController controller2 = loader.getController();

                // Pass the email to Controller2
                controller2.setUserEmail(user_email);

                // Show the second scene


                // Close the current scene

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            emailfield.getStyleClass().add("text-field-error");
            showAlert("Email Not Found", "Error", "The email does not exist ");
        }
    }




    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static int generateRandomNumber() {
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            digits.add(i);
        }

        // Shuffle the list to randomize the order of digits
        Collections.shuffle(digits);

        StringBuilder randomNumber = new StringBuilder();

        // Add 6 digits to the random number
        for (int i = 0; i < 6; i++) {
            // Ensure that the first 4 digits are unique
            if (i < 4) {
                randomNumber.append(digits.get(i));
            } else {
                // Add two more random digits (not present in the first 4)
                int digit = randomDigit(digits);
                randomNumber.append(digit);
            }
        }

        // Convert the string to an integer and return
        return Integer.parseInt(randomNumber.toString());

    }

    private static int randomDigit(List<Integer> digits) {
        Random random = new Random();
        int digit = random.nextInt(10);
        while (digits.subList(0, 4).contains(digit)) {
            digit = random.nextInt(10);
        }
        return digit;
    }

    public static void main(String[] args) {
        int randomNumber = generateRandomNumber();
        System.out.println(randomNumber);
    }

    @FXML
    void loginon() throws IOException {

        // Load the sign-up window FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/users/register.fxml"));
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


    public void exit() {
        System.exit(0);
    }
}
