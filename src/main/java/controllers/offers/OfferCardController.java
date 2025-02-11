package controllers.offers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.offers.Offer;

public class OfferCardController {

    @FXML
    private Label offer_name, offer_description, offer_status;
    @FXML
    private Button inspectOfferButton, acceptOfferButton, editOfferButton, denyOfferButton,PayUp;
    @FXML
    private HBox buttonsContainer;

    private Offer offer;


    public void setData(Offer offer) {
        this.offer = offer;
        this.offer_name.setText(offer.getTitle());
        this.offer_description.setText(offer.getDescription());
        setOfferStatus(offer.getStatus());
        setupButtons(offer.getUser_id());
    }

    private void setOfferStatus(int status) {
        switch (status) {
            case 0:
                this.offer_status.setText("admin didn't approve yet");
                this.offer_status.setStyle("-fx-text-fill: yellow");
                break;
            case 1:
                this.offer_status.setText("denied by admin");
                this.offer_status.setStyle("-fx-text-fill: red");
                break;
            case 2:
                this.offer_status.setText("accepted by admin wait for response");
                this.offer_status.setStyle("-fx-text-fill: rgba(1,22,255,0.7)");
                break;
            case 3:
                this.offer_status.setText("project owner approved");
                this.offer_status.setStyle("-fx-text-fill: green");
                break;
            case 4:
                this.offer_status.setText("denied by project owner");
                this.offer_status.setStyle("-fx-text-fill: red");
                break;
            default:
                this.offer_status.setText("Unknown");
                this.offer_status.setStyle("-fx-text-fill: red");
                break;
        }
    }

    private void setupButtons(int userId) {
        buttonsContainer.getChildren().clear();
        buttonsContainer.getChildren().add(inspectOfferButton);
        buttonsContainer.getChildren().add(editOfferButton);

        if (userId != 1) {
            buttonsContainer.getChildren().addAll(acceptOfferButton, denyOfferButton);
        }
    }

    @FXML
    private void denyOfferButtonClicked(ActionEvent actionEvent) {
        // Handle deny offer logic here
    }

    @FXML
    private void acceptOfferButtonClicked(ActionEvent actionEvent) {
        // Handle accept offer logic here
    }

    @FXML
    private void editOfferButtonClicked(ActionEvent actionEvent) {
        // Handle edit offer logic here
    }

    @FXML
    private void inspectOfferButtonClicked(ActionEvent actionEvent) {
        // Handle inspect offer logic here
    }

    public Button getInspectOfferButton() {
        return inspectOfferButton;
    }

    public Button getAcceptOfferButton() {
        return acceptOfferButton;
    }

    public Button getEditOfferButton() {
        return editOfferButton;
    }

    public Button getPayUp() {
        return PayUp;
    }

    public Button getDenyOfferButton() {
        return denyOfferButton;
    }

    public void PayUpButtonClicked(ActionEvent actionEvent) {
    }
}
