package controllers.offers;

import controllers.projects.ProjectCardController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfferController {


    /*Error messages*/
    public HBox titleError;
    public HBox descriptionError;
    public HBox typeError;

    /*offer form*/
    public TextArea offer_description_ta;
    public TextField offer_title_tf;
    public Button Equity_Button;
    public Button Debt_Button;
    public Button Revenue_Button;
    public Pane blur;
    public Pane form;
    public VBox OffersContent;
    public VBox offerCardsVbox;
    @FXML
    public Pane inspectBg;
    public Button Update_button;
    private int projectId;

    ComboBox<String> Dropdown = new ComboBox<>();


    /*category form*/
    public VBox category_form_vbox;

    public Button activeButton ;
    public Button decoyButton= new Button() ;
    public TextField categoryAttribute1 =new TextField();
    public TextField categoryAttribute2=new TextField();
    public TextField categoryAttribute3=new TextField();
    public TextField categoryAttribute4=new TextField();
    public TextField categoryAttribute5=new TextField();
    public Button Submit_button;
    /*content*/
    public VBox projects_list;
    /**SERVICES**/
    ProjectService projectService = new ProjectService();
    CategoryService cs = new CategoryService();
    OfferService os = new OfferService();

    List<Project> projects = new ArrayList<>();


    @FXML
    public void initialize(){
        category_form_vbox.getChildren().clear();
        blur.setVisible(false);
        form.setVisible(false);
        setCategoryAttributesDimensions();
        // Clear any existing error messages
        titleError.setVisible(false);
        descriptionError.setVisible(false);
        typeError.setVisible(false);
        try{
            //loadingProjects();
            loadingOffers();
        }catch (IOException  | SQLException e) {
            throw new RuntimeException(e);
        }
        inspectBg.setVisible(false);

    }

    private void setActive(Button button) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("btn_active");
        }
        button.getStyleClass().add("btn_active");
        activeButton = button;
    }
    public void setCategoryAttributesDimensions() {
        TextField[] textFields = {categoryAttribute1, categoryAttribute2, categoryAttribute3, categoryAttribute4, categoryAttribute5};

        for (int i = 0; i < textFields.length; i++) {
            // Set width and height for each TextField
            textFields[i].setMaxWidth(350); // Set preferred width
            textFields[i].setMaxHeight(45); // Set preferred height
        }
    }

    public void Equity_Button_Clicked(ActionEvent actionEvent) {

            setActive(Equity_Button);
            categoryAttribute1.setPromptText("Investment amount");
            categoryAttribute2.setPromptText("Expected Return on Investment");
            categoryAttribute3.setPromptText("Investment Horizon (in months) ");
            ///controle de saisie
            EventHandler<KeyEvent> numericValidationHandler = event -> {
                TextField textField = (TextField) event.getSource();
                String text = textField.getText();
                if (!text.matches("\\d*(\\.\\d*)?")) {
                    textField.setText(text.replaceAll("[^\\d.]", ""));
                    event.consume();
                }
            };

            // Attach the event handler to each text field
            categoryAttribute1.addEventHandler(KeyEvent.KEY_TYPED, numericValidationHandler);
            categoryAttribute2.addEventHandler(KeyEvent.KEY_TYPED, numericValidationHandler);
            categoryAttribute3.addEventHandler(KeyEvent.KEY_TYPED, numericValidationHandler);


                Dropdown.getItems().addAll("Low", "Medium", "High");
                Dropdown.setPromptText("Select Risk Appetite");
                Dropdown.setOnAction(e -> {
                String selectedRiskAppetite = Dropdown.getValue();
            });

            category_form_vbox.getChildren().clear();
            category_form_vbox.getChildren().addAll(categoryAttribute1, categoryAttribute2, categoryAttribute3, Dropdown);


    }



    public void Dept_Button_Clicked(ActionEvent actionEvent) {
        setActive(Debt_Button);
        categoryAttribute1.setPromptText("Investment amount");
        categoryAttribute2.setPromptText("Interest Rate");
        categoryAttribute3.setPromptText("The duration of the investment, in years");

        ///controle de saisie
        EventHandler<KeyEvent> numericValidationHandler = event -> {
            TextField textField = (TextField) event.getSource();
            String text = textField.getText();
            if (!text.matches("\\d*(\\.\\d*)?")) {
                textField.setText(text.replaceAll("[^\\d.]", ""));
                event.consume();
            }
        };

        // Attach the event handler to each text field
        categoryAttribute1.addEventHandler(KeyEvent.KEY_TYPED, numericValidationHandler);
        categoryAttribute2.addEventHandler(KeyEvent.KEY_TYPED, numericValidationHandler);
        categoryAttribute3.addEventHandler(KeyEvent.KEY_TYPED, numericValidationHandler);

        // Create a dropdown with credit rating options
        Dropdown.getItems().clear();
        Dropdown.getItems().addAll(
                "AAA", "AA+", "AA", "A+", "A", "BBB+", "BBB", "BB+", "BB"
        );
        Dropdown.setPromptText("Select Credit Rating");

        // Event listener for when an option is selected
        Dropdown.setOnAction(e -> {
            String selectedCreditRating = Dropdown.getValue();

        });

        category_form_vbox.getChildren().clear();
        category_form_vbox.getChildren().addAll(categoryAttribute1, categoryAttribute2, categoryAttribute3, Dropdown);
    }

    public void Revenue_Button_Clicked(ActionEvent actionEvent) {
        setActive(Revenue_Button);
        categoryAttribute1.setPromptText("Investment amount");
        categoryAttribute2.setPromptText("Percentage of Profits");
        categoryAttribute3.setPromptText("Profit Margin (%) on product sold");

        ///controle de saisie
        EventHandler<KeyEvent> numericValidationHandler = event -> {
            TextField textField = (TextField) event.getSource();
            String text = textField.getText();
            if (!text.matches("\\d*(\\.\\d*)?")) {
                textField.setText(text.replaceAll("[^\\d.]", ""));
                event.consume();
            }
        };
        Dropdown.getItems().clear();
        Dropdown.getItems().addAll(
                "On sails", "On revenue"
        );
        Dropdown.setPromptText("Select Revenue Category");

        // Attach the event handler to each text field
        categoryAttribute1.addEventHandler(KeyEvent.KEY_TYPED, numericValidationHandler);
        categoryAttribute2.addEventHandler(KeyEvent.KEY_TYPED, numericValidationHandler);
        categoryAttribute3.addEventHandler(KeyEvent.KEY_TYPED, numericValidationHandler);

        category_form_vbox.getChildren().clear();
        category_form_vbox.getChildren().addAll(categoryAttribute1, categoryAttribute2, categoryAttribute3,Dropdown);

    }

    public void SubmitButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {
        titleError.setVisible(offer_title_tf.getText().isEmpty());
        descriptionError.setVisible(offer_description_ta.getText().isEmpty());
        typeError.setVisible(activeButton == null);
        if(typeError.isVisible() || descriptionError.isVisible() || titleError.isVisible()) {return;}

            switch (activeButton.getId()){
                case "Equity_Button":
                    if(categoryAttribute1.getText().isEmpty() || categoryAttribute2.getText().isEmpty() || categoryAttribute3.getText().isEmpty() || Dropdown.getValue()==null){
                        return;
                    }
                    else {

                        Category category = new Category("Equity", Float.parseFloat(categoryAttribute1.getText()),Float.parseFloat(categoryAttribute2.getText()),Float.parseFloat(categoryAttribute3.getText()),Dropdown.getValue());
                        pushToDb(category);
                    }
                    break;
                case "Debt_Button":
                    if(categoryAttribute1.getText().isEmpty() || categoryAttribute2.getText().isEmpty() || categoryAttribute3.getText().isEmpty() || Dropdown.getValue()==null){
                        return;
                    }
                    else {

                        Category category = new Category("Dept", Float.parseFloat(categoryAttribute1.getText()),Float.parseFloat(categoryAttribute2.getText()),Float.parseFloat(categoryAttribute3.getText()),Dropdown.getValue());
                        pushToDb(category);
                    }
                    break;
                case "Revenue_Button":
                    if(categoryAttribute1.getText().isEmpty() || categoryAttribute2.getText().isEmpty() || categoryAttribute3.getText().isEmpty() || Dropdown.getValue()==null){
                        return;
                    }
                    else {
                        Category category = new Category("Revenue", Float.parseFloat(categoryAttribute1.getText()),Float.parseFloat(categoryAttribute2.getText()),Float.parseFloat(categoryAttribute3.getText()),Dropdown.getValue());

                        pushToDb(category);
                    }
                    break;
            }
        reset();
    }

    private void pushToDb(Category category ) {

        try {
            int fk =cs.create(category);
            if (fk!=-1){
                os.create(new Offer(fk,offer_title_tf.getText() , offer_description_ta.getText() , projectId));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }
    private void updateToDb(ActionEvent actionEvent,Offer offer, Category category ) {

        try {
            int fk =cs.create(category);
            if (fk!=-1){
                os.create(new Offer(fk,offer_title_tf.getText() , offer_description_ta.getText() , projectId));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }

    public void openform(ActionEvent actionEvent) {
        blur.setVisible(true);
        blur.toFront();

        form.setVisible(true);
        form.toFront();

    }
    public void openformForEdit(ActionEvent actionEvent, Offer offer , Category category) {
        System.out.println(category);
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
                setActive(Equity_Button);
                break;
            case "Dept":
                setActive(Debt_Button);
                break;
            case "Revenue":
                setActive(Revenue_Button);
                break;

        }
        categoryAttribute1.setText(String.valueOf(category.getAttribute1()));
        categoryAttribute2.setText(String.valueOf(category.getAttribute2()));
        categoryAttribute3.setText(String.valueOf(category.getAttribute3()));
        Update_button.setOnAction(event ->{
//            updateToDb();
        });

    }

    public void close_form_button(ActionEvent actionEvent) {
        blur.setVisible(false);
        blur.toBack();

        form.setVisible(false);
        form.toBack();

        inspectBg.setVisible(false);
        inspectBg.getChildren().clear();
        inspectBg.toBack();
        System.out.println("huh");
    }
    private void loadingProjects() throws SQLException, IOException {
        /*
        projects = projectService.read();
        projects_list.getChildren().clear();
        for (Project project : projects) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/projects/ProjectCard.fxml"));

            try {
                Pane view = loader.load();
                ProjectCardController projectCardController = loader.getController();
                projectCardController.setData(project);
                projectCardController.getProjectCardButton().setOnAction(event -> {
                    this.openform(event);
                    Update_button.setVisible(false);
                });

                // Set action handler on the ProjectCardButton
                this.projectId=projectCardController.projectCardButtonClicked();
                System.out.println(this.projectId);

                projects_list.getChildren().add(view);
            } catch (IOException e) {
                // Handle IOException
                e.printStackTrace();
            }
        }

         */
    }
    private void loadingOffers() throws SQLException, IOException {

        List<Offer> offers = new ArrayList<>();
        OfferService OfferService = new OfferService();
        CategoryService categoryService = new CategoryService();
        offers = OfferService.read();
        offerCardsVbox.getChildren().clear();
        Category category = new Category();
        for (Offer offer:offers)
        {
            category=categoryService.getSingleCategory(offer.getFunding_id());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/offers/OfferCard.fxml"));
            Pane view = loader.load();
            OfferCardController offerCardController = loader.getController();
            offerCardController.setData(offer);
            offerCardsVbox.getChildren().add(view);
            offerCardController.getInspectOfferButton().setOnAction(event -> {openInspectOffer(event,offer);});
            Category finalCategory = category;
            offerCardController.getEditOfferButton().setOnAction(event -> {openformForEdit(event,offer, finalCategory);});
        }
    }

    private void openInspectOffer(ActionEvent event,Offer offer) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/offers/InspectOffer.fxml"));
        try {
            CategoryService cs = new CategoryService();
            Category category= new Category();
            category=cs.getSingleCategory(offer.getFunding_id());
            Pane view = loader.load();
            blur.setVisible(true);
            blur.toFront();
            inspectBg.setVisible(true);
            inspectBg.toFront();
            inspectBg.getChildren().add(view);
            InspectOfferController inspectOfferController = loader.getController();
            inspectOfferController.setData(offer,category);
            inspectOfferController.getQuitInspect().setOnAction(this::closeInspect);
            inspectOfferController.getDelete().setOnAction(e->{
                inspectOfferController.deleteClicked(e);
                closeInspect(e);
            });



        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeInspect(ActionEvent event) {
        inspectBg.setVisible(false);
        inspectBg.getChildren().clear();
        inspectBg.toBack();
        blur.setVisible(false);
        blur.toBack();
        try {
            loadingOffers();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateButtonClicked(ActionEvent event , Offer offer , Category category) throws SQLException, IOException {
        updateToDb(event,offer, category);
        reset();
    }

    private void reset() throws SQLException, IOException {
        offer_title_tf.clear();
        offer_description_ta.clear();
        categoryAttribute1.clear();
        categoryAttribute2.clear();
        categoryAttribute3.clear();
        Dropdown.getSelectionModel().clearSelection();
        setActive(decoyButton);
        category_form_vbox.getChildren().clear();
        blur.setVisible(false);
        form.setVisible(false);
        blur.toBack();
        form.toBack();
        loadingOffers();
    }

    public void UpdateButtonClicked(ActionEvent event) {
       // this.f
    }
}
