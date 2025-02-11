package controllers.offers;


import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import controllers.projects.ProjectCardController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.offers.Category;
import models.offers.Offer;
import models.projects.Project;
import services.offers.CategoryService;
import services.offers.OfferService;
import services.projects.ProjectService;
import utils.SessionManager;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfferController {


    /* Error messages */
    @FXML
    private HBox titleError, descriptionError, typeError;

    /* Offer form */
    @FXML
    private TextArea offer_description_ta;
    @FXML
    private TextField offer_title_tf;
    @FXML
    private Button Equity_Button, Debt_Button, Revenue_Button, Update_button, Submit_button;
    @FXML
    private Pane blur, form, inspectBg;
    @FXML
    private VBox offerCardsVbox, offerCardsVbox1, category_form_vbox, projects_list;

    private Button activeButton;
    private final Button decoyButton = new Button();
    private final TextField categoryAttribute1 = new TextField();
    private final TextField categoryAttribute2 = new TextField();
    private final TextField categoryAttribute3 = new TextField();
    private final TextField categoryAttribute4 = new TextField();
    private final TextField categoryAttribute5 = new TextField();
    private final ComboBox<String> dropdown = new ComboBox<>();
    private Project project;

    private final ProjectService projectService = new ProjectService();
    private final CategoryService categoryService = new CategoryService();
    private final OfferService offerService = new OfferService();

    private List<Project> projects = new ArrayList<>();

    @FXML
    public void initialize() {
        setupForm();
        setupCategoryAttributesDimensions();
        clearErrorMessages();
        try {
            loadProjects();
            loadOffersImade();
            loadOffersIgot();
        } catch (IOException | SQLException e) {
            showErrorDialog(e);
        }
    }

    private void setupForm() {
        category_form_vbox.getChildren().clear();
        blur.setVisible(false);
        form.setVisible(false);
        inspectBg.setVisible(false);
    }

    private void setupCategoryAttributesDimensions() {
        TextField[] textFields = {categoryAttribute1, categoryAttribute2, categoryAttribute3, categoryAttribute4, categoryAttribute5};
        for (TextField textField : textFields) {
            textField.setMaxWidth(350);
            textField.setMaxHeight(45);
        }
    }

    private void clearErrorMessages() {
        titleError.setVisible(false);
        descriptionError.setVisible(false);
        typeError.setVisible(false);
    }

    public void Equity_Button_Clicked(ActionEvent actionEvent) {
        setActiveButton(Equity_Button);
        setupCategoryAttributes("Investment amount", "Expected Return on Investment", "Investment Horizon (in months)");
        setupNumericValidation(categoryAttribute1, categoryAttribute2, categoryAttribute3);
        setupDropdown("Select Risk Appetite", "Low", "Medium", "High");
        updateCategoryForm(categoryAttribute1, categoryAttribute2, categoryAttribute3, dropdown);
    }

    public void Dept_Button_Clicked(ActionEvent actionEvent) {
        setActiveButton(Debt_Button);
        setupCategoryAttributes("Investment amount", "Interest Rate", "The duration of the investment, in years");
        setupNumericValidation(categoryAttribute1, categoryAttribute2, categoryAttribute3);
        setupDropdown("Select Credit Rating", "AAA", "AA+", "AA", "A+", "A", "BBB+", "BBB", "BB+", "BB");
        updateCategoryForm(categoryAttribute1, categoryAttribute2, categoryAttribute3, dropdown);
    }

    public void Revenue_Button_Clicked(ActionEvent actionEvent) {
        setActiveButton(Revenue_Button);
        setupCategoryAttributes("Investment amount", "Percentage of Profits", "Profit Margin (%) on product sold");
        setupNumericValidation(categoryAttribute1, categoryAttribute2, categoryAttribute3);
        setupDropdown("Select Revenue Category", "On sales", "On revenue");
        updateCategoryForm(categoryAttribute1, categoryAttribute2, categoryAttribute3, dropdown);
    }

    private void setActiveButton(Button button) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("btn_active");
        }
        button.getStyleClass().add("btn_active");
        activeButton = button;
    }

    private void setupCategoryAttributes(String prompt1, String prompt2, String prompt3) {
        categoryAttribute1.setPromptText(prompt1);
        categoryAttribute2.setPromptText(prompt2);
        categoryAttribute3.setPromptText(prompt3);
    }

    private void setupNumericValidation(TextField... textFields) {
        EventHandler<KeyEvent> numericValidationHandler = event -> {
            TextField textField = (TextField) event.getSource();
            String text = textField.getText();
            if (!text.matches("\\d*(\\.\\d*)?")) {
                textField.setText(text.replaceAll("[^\\d.]", ""));
                event.consume();
            }
        };
        for (TextField textField : textFields) {
            textField.addEventHandler(KeyEvent.KEY_TYPED, numericValidationHandler);
        }
    }

    private void setupDropdown(String promptText, String... options) {
        dropdown.getItems().clear();
        dropdown.getItems().addAll(options);
        dropdown.setPromptText(promptText);
    }

    private void updateCategoryForm(Node... nodes) {
        category_form_vbox.getChildren().clear();
        category_form_vbox.getChildren().addAll(nodes);
    }

    public void SubmitButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {
        if (validateForm()) {
            Category category = this.createCategory();
            if (category != null) {
                saveOffer(category);
            }
            resetForm();
        }
    }

    private boolean validateForm() {
        titleError.setVisible(offer_title_tf.getText().isEmpty());
        descriptionError.setVisible(offer_description_ta.getText().isEmpty());
        typeError.setVisible(activeButton == null);
        return !(titleError.isVisible() || descriptionError.isVisible() || typeError.isVisible());
    }

    private Category createCategory() {
        try {
            float attribute1 = Float.parseFloat(categoryAttribute1.getText());
            float attribute2 = Float.parseFloat(categoryAttribute2.getText());
            float attribute3 = Float.parseFloat(categoryAttribute3.getText());
            String dropdownValue = dropdown.getValue();
            switch (activeButton.getId()) {
                case "Equity_Button":
                    return new Category("Equity", attribute1, attribute2, attribute3, dropdownValue);
                case "Debt_Button":
                    return new Category("Debt", attribute1, attribute2, attribute3, dropdownValue);
                case "Revenue_Button":
                    return new Category("Revenue", attribute1, attribute2, attribute3, dropdownValue);
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            showErrorDialog(e);
            return null;
        }
    }


    private void saveOffer(Category category) {
        try {
            int categoryId = categoryService.create(category);
            if (categoryId != -1) {
                offerService.create(new Offer(categoryId, this.project.getId(), SessionManager.getInstance().getUser_id(), project.getUser_id(), offer_title_tf.getText(), offer_description_ta.getText()));
            }
        } catch (SQLException e) {
            showErrorDialog(e);
        }
    }

    private void resetForm() throws SQLException, IOException {
        offer_title_tf.clear();
        offer_description_ta.clear();
        categoryAttribute1.clear();
        categoryAttribute2.clear();
        categoryAttribute3.clear();
        dropdown.getSelectionModel().clearSelection();
        setActiveButton(decoyButton);
        category_form_vbox.getChildren().clear();
        blur.setVisible(false);
        form.setVisible(false);
        blur.toBack();
        form.toBack();
        loadOffersImade();
        loadOffersIgot();
    }

    public void openForm(ActionEvent actionEvent) {
        blur.setVisible(true);
        blur.toFront();
        form.setVisible(true);
        form.toFront();
        Update_button.setVisible(false);
        Submit_button.setVisible(true);
    }

    public void openFormForEdit(ActionEvent actionEvent, Offer offer, Category category) {
        blur.setVisible(true);
        blur.toFront();
        form.setVisible(true);
        form.toFront();
        Update_button.setVisible(true);
        Submit_button.setVisible(false);
        offer_title_tf.setText(offer.getTitle());
        offer_description_ta.setText(offer.getDescription());
        switch (category.getType()) {
            case "Equity":
                setActiveButton(Equity_Button);
                break;
            case "Debt":
                setActiveButton(Debt_Button);
                break;
            case "Revenue":
                setActiveButton(Revenue_Button);
                break;
        }
        categoryAttribute1.setText(String.valueOf(category.getAttribute1()));
        categoryAttribute2.setText(String.valueOf(category.getAttribute2()));
        categoryAttribute3.setText(String.valueOf(category.getAttribute3()));
        Update_button.setOnAction(event -> updateOffer(event, offer, category));
    }

    public void close_form_button(ActionEvent actionEvent) {
        blur.setVisible(false);
        blur.toBack();
        form.setVisible(false);
        form.toBack();
        inspectBg.setVisible(false);
        inspectBg.getChildren().clear();
        inspectBg.toBack();
    }

    private void loadProjects() throws SQLException, IOException {
        projects = projectService.readProjectsIdidntMake(SessionManager.getInstance().getUser_id());
        projects_list.getChildren().clear();
        for (Project project : projects) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/projects/ProjectCard.fxml"));
            try {
                Pane view = loader.load();
                ProjectCardController projectCardController = loader.getController();
                projectCardController.setData(project);
                projectCardController.getProjectCardButton().setOnAction(this::openForm);
                this.project = projectCardController.projectCardButtonClicked();
                projects_list.getChildren().add(view);
            } catch (IOException e) {
                showErrorDialog(e);
            }
        }
    }

    private void hideButton(Button button) {
        button.setVisible(false);
        button.setManaged(false);
    }

    private void loadOffersImade() throws SQLException, IOException {
        List<Offer> offers = offerService.readOffersImade(SessionManager.getInstance().getUser_id());
        offerCardsVbox.getChildren().clear();
        for (Offer offer : offers) {
            Category category = categoryService.getSingleCategory(offer.getFunding_id());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/offers/OfferCard.fxml"));
            try {
                Pane view = loader.load();
                OfferCardController offerCardController = loader.getController();
                offerCardController.setData(offer);
                offerCardsVbox.getChildren().add(view);
                offerCardController.getInspectOfferButton().setOnAction(event -> openInspectOffer(event, offer));
                offerCardController.getEditOfferButton().setOnAction(event -> openFormForEdit(event, offer, category));
                hideButton(offerCardController.getAcceptOfferButton());
                hideButton(offerCardController.getDenyOfferButton());
                if(offer.getStatus()==3){
                    hideButton(offerCardController.getEditOfferButton());
                    hideButton(offerCardController.getInspectOfferButton());

                }else {
                    hideButton(offerCardController.getPayUp());
                }
            } catch (IOException e) {
                showErrorDialog(e);
            }
        }
    }

    private void loadOffersIgot() throws SQLException, IOException {
        List<Offer> offers = offerService.readOffersIgot(SessionManager.getInstance().getUser_id());
        offerCardsVbox1.getChildren().clear();
        for (Offer offer : offers) {
            Category category = categoryService.getSingleCategory(offer.getFunding_id());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/offers/OfferCard.fxml"));
            try {
                Pane view = loader.load();
                OfferCardController offerCardController = loader.getController();
                offerCardController.setData(offer);
                offerCardsVbox1.getChildren().add(view);
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

    private void acceptOffer(ActionEvent event, Offer offer) throws SQLException {
        try {
            offer.setStatus(3);
            offerService.update(offer);
        } catch (SQLException e) {
            showErrorDialog(e);
        }
    }
    private void denyOffer(ActionEvent event, Offer offer) throws SQLException {
        try {
            offer.setStatus(4);
            offerService.update(offer);
        } catch (SQLException e) {
            showErrorDialog(e);
        }
    }

    private void pay(Offer offer) throws SQLException {
        try {
            // Configure Stripe API key
            Stripe.apiKey = "sk_test_51OqZ3YLdtRvtorIBI6PHfTH7iGGb64aLqBG7z3jIEGlSmui5sNxJ3mjr8GsKxg3dDFP6L0O9dx5L3PbOwFhrhXRi00LLRcqQUf";

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://127.0.0.1:8000/success-url/" + offer.getId())
                    .setCancelUrl("https://example.com/cancel")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount(2000L) // Montant en cents (ex. 20,00 USD)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Sample Product")
                                                                    .build())
                                                    .build())
                                    .build())
                    .build();

            // Créez la session de paiement
            Session session = Session.create(params);

            // Mettez à jour l'offre et sauvegardez dans la base de données
            offer.setStatus(3);
            offerService.update(offer);

            // Redirigez l'utilisateur vers l'URL de paiement
            openBrowser(session.getUrl());
        } catch (Exception e) {
            throw new SQLException("Erreur lors de la création de la session de paiement", e);
        }
    }

    private void openBrowser(String url) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop is not supported. Please open the following URL manually: " + url);
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
            inspectOfferController.getQuitInspect().setOnAction(this::close_form_button);
//            if it's an offer i got i can't delete it
            if (offer.getReciever_id() == SessionManager.getInstance().getUser_id()) {
                hideButton(inspectOfferController.getDelete());

            }
//            else don't
            else {
                inspectOfferController.getDelete().setOnAction(e -> {
                    inspectOfferController.deleteClicked(e);
                    close_form_button(e);
                });
                resetForm();
            }
        } catch (IOException | SQLException e) {
            showErrorDialog(e);
        }
    }

    private void updateOffer(ActionEvent event, Offer offer, Category category) {
        try {
            if (category != null && offer != null) {
                category.setAttribute1(Float.parseFloat(categoryAttribute1.getText()));
                category.setAttribute2(Float.parseFloat(categoryAttribute2.getText()));
                category.setAttribute3(Float.parseFloat(categoryAttribute3.getText()));
                category.setTextAttribute(dropdown.getValue());
                switch (activeButton.getId()) {
                    case "Equity_Button":
                        category.setType("Equity");
                        break;
                    case "Debt_Button":
                        category.setType("Dept");
                        break;
                    case "Revenue_Button":
                        category.setType("Revenue");
                        break;
                    default:
                        category.setType("notSelected");
                        break;
                }

                offer.setTitle(offer_title_tf.getText());
                offer.setDescription(offer_description_ta.getText());
                offer.setStatus(0);

                categoryService.update(category);
                offerService.update(offer);
                resetForm();
            }
        } catch (SQLException | IOException e) {
            showErrorDialog(e);
        }
    }

    private void showErrorDialog(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public void UpdateButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {
        if (validateForm()) {
            Category category = this.createCategory();
            if (category != null) {
                saveOffer(category);
            }
            resetForm();
        }
    }
}
