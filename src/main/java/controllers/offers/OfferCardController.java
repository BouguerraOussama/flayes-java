package controllers.offers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.offers.Offer;

import java.text.SimpleDateFormat;


public class OfferCardController {
    public Label offer_name;
    public Label offer_description;
    public Label offer_status;
    public Button inspectOfferButton;
    public Button acceptOfferButton;
    public Button editOfferButton;
    public Button denyOfferButton;
    public HBox buttonsContainer;
    private int OfferKey;

    public void setData(Offer offer) {
        OfferKey = offer.getId();
        this.offer_name.setText(offer.getTitle());
        this.offer_description.setText(offer.getDescription());
        switch (offer.getStatus()) {
            case 0:
                this.offer_status.setText("no match ");
                this.offer_status.setStyle("-fx-text-fill: yellow");
                break;
            case 1:
                this.offer_status.setText("accepted");
                this.offer_status.setStyle("-fx-text-fill: green");
                break;
        }
        if (offer.getUser_id()==1){
            buttonsContainer.getChildren().clear();
            buttonsContainer.getChildren().addAll(inspectOfferButton,editOfferButton);
        }else{
            buttonsContainer.getChildren().clear();
            buttonsContainer.getChildren().addAll(inspectOfferButton,editOfferButton,acceptOfferButton,denyOfferButton);
        }
    }


    public void denyOfferButtonClicked(ActionEvent actionEvent) {
    }

    public int editOfferButtonClicked(ActionEvent actionEvent) {
        return OfferKey;
    }

    public void acceptOfferButtonClicked(ActionEvent actionEvent) {
    }

    public int inspectOfferButtonClicked(ActionEvent actionEvent) {
        return OfferKey;
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

    public Button getDenyOfferButton() {
        return denyOfferButton;
    }

}
