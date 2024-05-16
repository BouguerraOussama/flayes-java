//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package controllers.users;

import controllers.users.animations.Animations;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.users.User;
import services.users.Notifications;
import services.users.TrayNotification;
import services.users.UserService;
import utils.MyDataBase;
import utils.SessionManager;
import org.mindrot.jbcrypt.BCrypt;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
public class SignIn implements Initializable {
    @FXML
    private Label TravelMe;
    @FXML
    private TextField email_signin;
    @FXML
    private PasswordField password_signin;
    @FXML
    private Button login_btn;
    @FXML
    private Hyperlink create_acc;
    @FXML
    private Label TravelMe2;
    @FXML
    private Label your_email;

    @FXML
    private TextField username;   //textfielusername
    @FXML
    private Button signup_btn;
    @FXML
    private Hyperlink login_acc;
    @FXML
    private AnchorPane signup_form;
    @FXML
    private AnchorPane login_form;
    @FXML
    private PasswordField confirm_password;
    @FXML
    private TextField numero;   //textfieldtel
    @FXML
    private PasswordField password_signup;   //jpassword

    @FXML
    private TextField email_signup;    //textfieldemail
    @FXML
    private ImageView image;
    @FXML
    private Button btn_login;

    @FXML
    private Label myFile;

    @FXML
    private Label nom_fichier;

    @FXML
    private Hyperlink mdp_oub;
    @FXML
    private HBox emailBox;
    private Connection cnx;
    private double xOffset = 0;
    private double yOffset = 0;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    private UserService userService = new UserService();
    private static SessionManager instance;
    public SignIn() {
    }



    public void exit() {
        System.exit(0);
    }

    public void textfieldDesign() {
        if (this.email_signin.isFocused()) {
            this.email_signin.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
            this.password_signin.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
        } else if (this.password_signin.isFocused()) {
            this.email_signin.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.password_signin.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
        }

    }

    public void textfieldDesign1() {
        if (this.email_signup.isFocused()) {

            this.email_signup.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
            this.password_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.username.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.numero.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.confirm_password.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
        } else if (this.password_signup.isFocused()) {
            this.email_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.password_signup.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
            this.username.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.numero.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.confirm_password.setStyle("-fx-background-color:transparent;-fx-border-width:1px");

        } else if (this.username.isFocused()) {
            this.email_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.password_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.username.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
            this.numero.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.confirm_password.setStyle("-fx-background-color:transparent;-fx-border-width:1px");

        } else if (this.numero.isFocused()) {
            this.email_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.password_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.username.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.numero.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
            this.confirm_password.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
        } else if (this.confirm_password.isFocused()) {
            this.email_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.password_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.username.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.numero.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.confirm_password.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
        }

    }

    public void dropShadowEffect() {
        DropShadow original = new DropShadow(20.0, Color.valueOf("#ae44a5"));
        original.setRadius(30.0);
        this.TravelMe.setEffect(original);
        this.TravelMe2.setEffect(original);
        this.TravelMe.setOnMouseEntered((event) -> {
            DropShadow shadow = new DropShadow(60.0, Color.valueOf("#ae44a5"));
            this.TravelMe.setCursor(Cursor.HAND);
            this.TravelMe.setStyle("-fx-text-fill:#517ab5");
            this.TravelMe.setEffect(shadow);
        });
        this.TravelMe.setOnMouseExited((event) -> {
            DropShadow shadow = new DropShadow(20.0, Color.valueOf("#ae44a5"));
            shadow.setRadius(30.0);
            this.TravelMe.setStyle("-fx-text-fill:#000");
            this.TravelMe.setEffect(shadow);
        });
        this.TravelMe2.setOnMouseEntered((event) -> {
            DropShadow shadow = new DropShadow(60.0, Color.valueOf("#ae44a5"));
            this.TravelMe2.setCursor(Cursor.HAND);
            this.TravelMe2.setStyle("-fx-text-fill:#517ab5");
            this.TravelMe2.setEffect(shadow);
        });
        this.TravelMe2.setOnMouseExited((event) -> {
            DropShadow shadow = new DropShadow(20.0, Color.valueOf("#ae44a5"));
            shadow.setRadius(30.0);
            this.TravelMe2.setStyle("-fx-text-fill:#000");
            this.TravelMe2.setEffect(shadow);
        });
    }

    public void changeForm(ActionEvent event) {
        if (event.getSource() == this.create_acc) {
            this.signup_form.setVisible(true);
            this.login_form.setVisible(false);
        } else if (event.getSource() == this.login_acc) {
            this.login_form.setVisible(true);
            this.signup_form.setVisible(false);
        }

    }

    public boolean ValidationEmail() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
        Matcher match = pattern.matcher(this.email_signup.getText());
        if (match.find() && match.group().equals(this.email_signup.getText())) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore message");
            alert.setHeaderText((String) null);
            alert.setContentText("Invalid Email");
            alert.showAndWait();
            return false;
        }
    }



    private void showAlert(AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void login() {
        try {
            //User user = userService.login(email_signin.getText(), password_signin.getText());
            User user =SessionManager.getInstance().auth(email_signin.getText(),password_signin.getText());
            System.out.println("user session connect is " + SessionManager.getInstance().getUser_id());

            if (user != null) {
                System.out.println(user);

                String fxmlPath = null;
                switch (user.getRole()) {
                    case "ROLE_ADMIN":
                        fxmlPath = "/Back.fxml";
                        break;
                    case "ROLE_USER":
                        fxmlPath = "/Front.fxml";
                        break;
                    default:
                        // Handle other roles or errors
                        return;
                }

                // Load FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                // Create a new stage
                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);  // Set the style to TRANSPARENT

                // Set the scene
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);  // Ensure the scene is transparent if using TRANSPARENT stage style
                stage.setScene(scene);

                // Enable dragging for the new stage
                root.setOnMousePressed(event -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });

                root.setOnMouseDragged(event -> {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                });

                // Close the current stage
                Stage currentStage = (Stage) email_signin.getScene().getWindow();
                currentStage.close();

                // Show the new stage
                stage.show();
            } else {
                // Handle authentication failure
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password");
                alert.showAndWait();

                TrayNotification trayNotification = new TrayNotification();
                trayNotification.setTitle("Login Failed");
                trayNotification.setMessage("Invalid username or password");
                trayNotification.setNotification(Notifications.ERROR);
                trayNotification.showAndWait();

            }
        } catch (SQLException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    @FXML
    public void signUp() {
        this.cnx = MyDataBase.getInstance().getConnection();
        String userEmail = email_signup.getText();
        String query = "insert into user (name,email,tel,password,roles,image_name,status) values (?,?,?,?,'ROLE_USER',?,?)";

        try {
            Alert alert;
            if (this.username.getText().isEmpty() | this.numero.getText().isEmpty() | this.email_signup.getText().isEmpty() | this.password_signup.getText().isEmpty() | this.confirm_password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Travel Me :: Error Message");
                alert.setHeaderText((String) null);
                alert.setContentText("Entrer all blank fields !!");
                alert.showAndWait();
            } else if (this.confirm_password.getText().length() < 8 | this.confirm_password.getText() == this.password_signup.getText()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Flayes&Flayes :: Error Message");
                alert.setHeaderText((String) null);
                alert.setContentText("password has to have 8 characters !!");
                alert.showAndWait();
            }else if (userService.emailExists(userEmail)) {
                email_signup.getStyleClass().add("text-field-error");
                showAlert(Alert.AlertType.ERROR, "Error", null, "A user with this email already exists.");

            } else if (this.ValidationEmail()) {
                // Hash the password before inserting
                String hashedPassword = BCrypt.hashpw(this.password_signup.getText(), BCrypt.gensalt());

                PreparedStatement smt = this.cnx.prepareStatement(query);
                smt.setString(1, this.username.getText());
                smt.setString(2, this.email_signup.getText());
                smt.setString(3, this.numero.getText());
                smt.setString(4, hashedPassword);
                smt.setString(5, myFile.getText());
                smt.setInt(6, 0);
                smt.executeUpdate();
                System.out.println("ajout avec succee");
                Alert alertt = new Alert(AlertType.INFORMATION);
                alertt.setTitle("Flayes&Flayes :: welcome");
                alertt.setHeaderText((String) null);
                alertt.setContentText("Vous Etes Inscrit !!");
                TrayNotification trayNotification = new TrayNotification();
                trayNotification.setTitle("BIENVENNUE");
                trayNotification.setMessage("BIENVENNUE");
                trayNotification.setNotification(Notifications.SUCCESS);
                trayNotification.showAndWait();
                alertt.showAndWait();
                this.login_form.setVisible(true);
                this.signup_form.setVisible(false);
            }
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }

    }

    void sendPassword() {
        this.cnx = MyDataBase.getInstance().getConnection();
        System.out.println("cxcccccccccccccccccc");
        String query2 = "select * from user where email=? ";
        String email1 = "empty";

        try {
            PreparedStatement smt = this.cnx.prepareStatement(query2);
            smt.setString(1, this.email_signin.getText());
            ResultSet rs = smt.executeQuery();
            if (rs.next()) {
                email1 = rs.getString("email");
                System.out.println(email1);
            }
        } catch (Exception var5) {
            System.out.println(var5.getMessage());
        }

        this.sendMail(email1);
    }


        public void sendMail(String recepient) {
            System.out.println("Preparing to send email");
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            final String myAccountEmail = "iben46655@gmail.com";
            final String passwordd = "buaj jssh dgok suge";
            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, passwordd);
                }
            });
            Message message = this.preparedMessage(session, myAccountEmail, recepient);

            try {
                Transport.send(message);
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("TravelMe :: Boite Mail");
                alert.setHeaderText((String)null);
                alert.setContentText("consulter votre boite mail !!");
                alert.showAndWait();
            } catch (Exception var8) {
                var8.printStackTrace();
            }

        }

        private Message preparedMessage(Session session, String myAccountEmail, String recepient) {
            this.cnx = MyDataBase.getInstance().getConnection();
            String query2 = "select * from user where email=?";
            String userEmail = "null";
            String pass = "empty";

            try {
                PreparedStatement smt = this.cnx.prepareStatement(query2);
                smt.setString(1, this.email_signin.getText());
                ResultSet rs = smt.executeQuery();
                System.out.println(rs);
                if (rs.next()) {
                    pass = rs.getString("password");
                    userEmail = rs.getString("email");
                }
            } catch (Exception var12) {
                System.out.println(var12.getMessage());
            }

            System.out.print("c est en cours");
            String text = "Votre mot de pass est :" + pass + "";
            String object = "Recupération de mot de passe";
            Message message = new MimeMessage(session);

            try {
                message.setFrom(new InternetAddress(myAccountEmail));
                message.setRecipient(RecipientType.TO, new InternetAddress(userEmail));
                message.setSubject(object);
                String htmlcode = "<h1> " + text + " </h1> <h2> <b> </b2> </h2> ";
                message.setContent(htmlcode, "text/html");
                System.out.println("Message envoyeer");
                System.out.println(pass);
                return message;
            } catch (MessagingException var11) {
                var11.printStackTrace();
                return null;
            }
        }

    @FXML
    void sendPaswword_btn(ActionEvent event) {
        this.sendPassword();
    }

    public void initialize(URL url, ResourceBundle rb) {
        this.dropShadowEffect();
    }


    @FXML
    void onChoisi(ActionEvent event) {
        try {
            String filename;
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            if (f != null) {
                filename = f.getAbsolutePath();
                nom_fichier.setText(filename);
                Image img = new Image(new FileInputStream(filename));
                image.setImage(img);
                image.setVisible(true);
                //Déplacer l'image
                String newPath = "C:\\Users\\user\\Desktop\\Flayes-Flayes-offers - Copie\\public\\uploads\\images\\";
                File sourceFile = null;
                File destinationFile = null;
                String nFile = f.getName();
                myFile.setText(nFile);
                sourceFile = new File(filename);
                destinationFile = new File(newPath + nFile);
                Path copy = Files.copy(sourceFile.toPath(), destinationFile.toPath());
            } else {
                System.out.println("Image error");
            }


            //Fin déplacer image
        } catch (IOException ex) {
            Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
@FXML
    private void click(ActionEvent event)
    {
        String title = "Sign In";
        String message = "is activated"; // You need to call the getText() method

        TrayNotification tray = new TrayNotification();
        tray.setAnimation(Animations.POPUP); // Corrected to use Animations instead of AnimationType
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotification(Notifications.SUCCESS); // Corrected to use Notifications instead of NotificationType
        tray.showAndDismiss(javafx.util.Duration.millis(3000));
    }

     */





    public void openForgetPasswordPage(ActionEvent event) {
        try {
            // Load the forgetpassword.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/users/forgetpassword.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();


            // Set the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Close the current stage
            Stage currentStage = (Stage) mdp_oub.getScene().getWindow();
            currentStage.close();

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            // Handle the exception if the fxml file cannot be loaded
            e.printStackTrace(); // You may want to handle this more gracefully
        }
    }

    public static class SceneTransition {
        public static void fadeTransition(Scene scene, Stage stage) {
            // Create a fade transition with a duration of 1.5 seconds
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0), scene.getRoot());

            // Set the from value (fully transparent)
            fadeTransition.setFromValue(0.0);

            // Set the to value (fully opaque)
            fadeTransition.setToValue(1.0);

            // Set action on finished transition
            fadeTransition.setOnFinished(event -> stage.show());

            // Play the fade transition
            fadeTransition.play();
        }
    }
}

