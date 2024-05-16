package controllers.users;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.users.User;
import services.users.UserService;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class Comptes implements Initializable {


    @FXML
    private AnchorPane listedesComptes;

    @FXML
    private ComboBox<String> combobox;

    @FXML
    private TableView<User> tableRead;


    @FXML
    private TableColumn<User,Integer> colonneUser_id;

    @FXML
    private TableColumn<User,Integer> colonneUsername;

    @FXML
    private TableColumn<User,Integer> colonneEmail;

    @FXML
    private TableColumn<User,Integer> colonneNumTel;

    @FXML
    private TableColumn<User, ImageView> colonneImage;

    @FXML
    private TableColumn<User,String> colonneRole;

    @FXML
    private TextField recherche;
    @FXML
    private Label nom_excel;

    @FXML
    private Label excelFile;
    @FXML
    private FlowPane comptesFlowPane;
    @FXML
    private TableColumn<User, Void> editCol;

    User user = null;
    public  ObservableList<User> list = FXCollections.observableArrayList();





    private UserService us;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnExcel;
    @FXML
    private Button btnDelete;

    UpdateUser uc= new UpdateUser();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.us = new UserService();
        List<User> liste=us.readWithImageView();
        RefreshTable(liste);
        tableRead.refresh();
        this.setupEditColumn();



    }
    @FXML
    void rechercher(KeyEvent event) {
        UserService us=new UserService();
        ObservableList<User> tab=FXCollections.observableArrayList();
        tab.addAll(us.SearchByName(recherche.getText()));

        colonneUsername.setCellValueFactory(new PropertyValueFactory("Username"));
        colonneEmail.setCellValueFactory(new PropertyValueFactory("Email"));
        colonneNumTel.setCellValueFactory(new PropertyValueFactory("tel"));
        colonneImage.setCellValueFactory(new PropertyValueFactory("img"));
        tableRead.setItems(tab);
        tableRead.refresh();
    }


    @FXML
    private void onCreate() throws IOException{
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/users/AddUser.fxml"));
        final Node node;
        node = fxmlLoader.load();
        AnchorPane pane=new AnchorPane(node);
        listedesComptes.getChildren().setAll(pane);
    }
    @FXML
    private void onUpdate(ActionEvent event) throws IOException {

        User u;
        u=tableRead.getSelectionModel().getSelectedItem();
        uc.setUserUpdate(u);
        System.out.println("aaa"+u.toString());
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/users/UpdateUser.fxml"));
        final Node node;
        node = fxmlLoader.load();
        AnchorPane pane=new AnchorPane(node);
        listedesComptes.getChildren().setAll(pane);

    }
    private void RefreshTable(List<User> liste){

        ObservableList<User> list=FXCollections.observableArrayList();
        list.addAll(liste);

        colonneUsername.setCellValueFactory(new PropertyValueFactory("Username"));
        colonneEmail.setCellValueFactory(new PropertyValueFactory("Email"));
        colonneNumTel.setCellValueFactory(new PropertyValueFactory("tel"));
        colonneImage.setCellValueFactory(new PropertyValueFactory("img"));
        tableRead.setItems(list);
        //tableRead.refresh();
    }


    @FXML
    private void onDelete(ActionEvent event) {
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppresssion");
        alert.setContentText("Voulez-vous vraiment supprimer ce compte ?");
        Optional<ButtonType> action=alert.showAndWait();
        if(action.get() == ButtonType.OK){
            UserService us= new UserService();
            us.delete(tableRead.getSelectionModel().getSelectedItem().getUser_id());
            RefreshTable(us.readWithImageView());
        }
    }
    @FXML
    private void onExcel(ActionEvent event) throws IOException {
        //text file, should be opening in default text editor
        File file = new File("");

        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) desktop.open(file);

        //let's try to open PDF file
        file = new File("C:\\Users\\user\\Desktop\\User\\src\\main\\resources\\Users.xlsx");
        if(file.exists()) desktop.open(file);
        us.excel();
    }
/*
    public void setUserUpdate(User u) {
        user.setUser_id(u.getUser_id());
        user.setUsername(u.getUsername());
        user.setEmail(u.getEmail());
        user.setPassword(u.getPassword());
        user.setRole(u.getRole());
        user.setImage_name(u.getImage_name());
        user.setImg(u.getImg());
        user.setStatus(u.getStatus());
        user.setTel(u.getTel());

        System.out.println(u.toString());    }
*/

    private void setupEditColumn() {
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<>() {
                    private final HBox actionsBox = new HBox();

                    {



                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        deleteIcon.setOnMouseClicked(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            handleDeleteUser(user);
                        });


                        deleteIcon.setStyle("-fx-cursor: hand ; -glyph-size: 28px ; -fx-fill: #ff1744;");

                        actionsBox.getChildren().addAll(deleteIcon);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(actionsBox);
                        }
                    }
                };
                return cell;
            }
        };

        editCol.setCellFactory(cellFactory);
    }


    public void handleEditUser(User user) {
        this.user = tableRead.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/users/UpdateUser.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        UpdateUser muc = loader.getController();
        muc.setTextFields(this.user);
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        this.us = new UserService();
        List<User> liste = us.readWithImageView();
        RefreshTable(liste);
    }
    private void refreshTable() {
        List<User> liste = us.readWithImageView();
        tableRead.getItems().setAll(liste);
    }
    private void handleDeleteUser(User user) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete User");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the user '" + user.getUsername() + "'?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

                us.delete(user.getUser_id());
                // Remove the user from the table view's data
                tableRead.getItems().remove(user);
                // Show success message
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("User Deleted");
                successAlert.setHeaderText(null);
                successAlert.setContentText("The user '" + user.getUsername() + "' has been deleted successfully.");
                successAlert.showAndWait();

        }
    }




}
