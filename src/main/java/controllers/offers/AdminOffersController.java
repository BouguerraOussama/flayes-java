package controllers.offers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.offers.Category;
import models.offers.Offer;
import services.offers.CategoryService;
import services.offers.OfferService;
import utils.SessionManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminOffersController {

    public Pane inspectBg;
    public Pane blur;
    @FXML
    private VBox offersVbox;

    private final OfferService offerService = new OfferService();
    private final CategoryService categoryService = new CategoryService();

    @FXML
    public void initialize() {
        try {
            resetForm();
        } catch (IOException | SQLException e) {
            showErrorDialog(e);
        }
    }

    private void loadOffers() throws SQLException, IOException {
        List<Offer> offers = offerService.read();
        offersVbox.getChildren().clear();
        for (Offer offer : offers) {
            Category category = categoryService.getSingleCategory(offer.getFunding_id());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/offers/OfferCard.fxml"));
            try {
                Pane view = loader.load();
                OfferCardController offerCardController = loader.getController();
                offerCardController.setData(offer);
                offersVbox.getChildren().add(view);

                offerCardController.getInspectOfferButton().setOnAction(event -> openInspectOffer(event, offer));
                offerCardController.getAcceptOfferButton().setOnAction(event -> {
                    try {
                        acceptOffer(event, offer);
                        resetForm();
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }

                });
                offerCardController.getDenyOfferButton().setOnAction(event -> {
                    try {
                        denyOffer(event, offer);
                        resetForm();
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                hideButton(offerCardController.getPayUp());
                hideButton(offerCardController.getEditOfferButton());

            } catch (IOException e) {
                showErrorDialog(e);
            }

        }
    }


    private void denyOffer(ActionEvent event, Offer offer) throws SQLException {
        try {
            offer.setStatus(1);

            offerService.update(offer);
        } catch (SQLException e) {
            showErrorDialog(e);
        }
    }

    private void acceptOffer(ActionEvent event, Offer offer) throws SQLException {
        try {
            offer.setStatus(2);
            offerService.update(offer);
        } catch (SQLException e) {
            showErrorDialog(e);
        }
    }

    private void openInspectOffer(ActionEvent event, Offer offer) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/offers/InspectOffer.fxml"));
        try {
            Category category = categoryService.getSingleCategory(offer.getFunding_id());
            Pane view = loader.load();
            blur.setVisible(true);
            blur.toFront();
            inspectBg.setVisible(true);
            inspectBg.toFront();
            inspectBg.getChildren().add(view);
            InspectOfferController inspectOfferController = loader.getController();
            inspectOfferController.setData(offer, category);
            inspectOfferController.getQuitInspect().setOnAction(this::closeFormButton);
            inspectOfferController.getDelete().setOnAction(e -> {
                inspectOfferController.deleteClicked(e);
                closeFormButton(e);
            });
            resetForm();

        } catch (IOException | SQLException e) {
            showErrorDialog(e);
        }
    }

    private void hideButton(Button button) {
        button.setVisible(false);
        button.setManaged(false);
    }

    private void showErrorDialog(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    private void closeFormButton(ActionEvent actionEvent) {
        blur.setVisible(false);
        blur.toBack();
        inspectBg.setVisible(false);
        inspectBg.getChildren().clear();
        inspectBg.toBack();
    }
    private void resetForm() throws SQLException, IOException {
        blur.setVisible(false);
        blur.toBack();
        loadOffers();
    }
}
