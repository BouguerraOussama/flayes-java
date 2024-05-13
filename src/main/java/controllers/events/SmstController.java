package controllers.events;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SmstController {


    // Remplacez ces valeurs par vos propres informations Twilio
    private static final String ACCOUNT_SID = "AC171c0cb01dd8314558113088a7bfd9d0";
    private static final String AUTH_TOKEN = "73cd4f1900e6d819414b88ea941bad16";
    private static final String TWILIO_PHONE_NUMBER = "+18573424232";

    // Remplacez cette valeur par votre propre numéro de téléphone
    private static final String VOTRE_NUMERO = "+216 21494353";

    @FXML
    private TextField messageTextField;

    @FXML
    private Button envoyerButton;

    @FXML
    public void envoyerSMS(ActionEvent event) {
        // Obtenez le message à partir du champ de texte
        String message = messageTextField.getText();

        // Initialisez Twilio avec vos informations d'identification
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Envoyez le SMS
        Message twilioMessage = Message.creator(
                        new PhoneNumber(VOTRE_NUMERO), // Votre numéro de téléphone
                        new PhoneNumber(TWILIO_PHONE_NUMBER), // Votre numéro Twilio
                        message)
                .create();

        // Traitez la réponse de l'envoi du SMS si nécessaire
        System.out.println("SID du message Twilio : " + twilioMessage.getSid());
    }
}

