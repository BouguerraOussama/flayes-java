package controllers.forum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.forum.Room;
import services.forum.RoomService;

import java.sql.SQLException;
import java.util.Arrays;

public class UpdateRoomController {

    private final RoomService rs = new RoomService();
    private int sqlIdx;
    private Room room;

    @FXML
    private Label cat;

    @FXML
    private Label subcat;

    @FXML
    private Label desc;

    @FXML
    private TextField newSub_categoryTF;

    @FXML
    private TextField newDescriptionTF;

    @FXML
    private ChoiceBox<String> newCategoryTF;

    ObservableList<String> options = FXCollections.observableArrayList(
            "Finance",
            "Agriculture",
            "Industry",
            "Art",
            "Business",
            "Manufacturing",
            "Education"
    );

    public void setSqlIdx(int sqlIdx) {
        this.sqlIdx = sqlIdx;
    }
    public void setRoomModel(Room room) {
        this.room = room;
    }

    void initData(Room SelectedItem){
        newCategoryTF.setValue(SelectedItem.getCategory());
        newSub_categoryTF.setText(SelectedItem.getSub_category());
        newDescriptionTF.setText(SelectedItem.getSub_category());
    }
    @FXML
    public void update(ActionEvent event) {
        String selectedCategory = newCategoryTF.getValue();
        try{
            newCategoryTF.setItems(options);

            if ( (!newSub_categoryTF.getText().matches("^[a-zA-Z]*$")) || (newSub_categoryTF.getText().isBlank()) || (newDescriptionTF.getText().isBlank()))
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Not Valid Input !");
                alert.showAndWait();
            }else{
                rs.update(new Room(sqlIdx,selectedCategory,newSub_categoryTF.getText(),newDescriptionTF.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Room updated");
                alert.showAndWait();
                newCategoryTF.setValue("Finance");
                for (TextField textField : Arrays.asList(newSub_categoryTF, newDescriptionTF)) {
                    textField.setText("");
                }
            }
        }catch(SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}