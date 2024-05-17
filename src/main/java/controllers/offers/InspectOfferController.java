package controllers.offers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.offers.Category;
import models.offers.Offer;
import services.offers.CategoryService;
import services.offers.OfferService;

import java.sql.SQLException;

public class InspectOfferController {
    public Button delete;
    public Button quitInspect;
    public Label InspectTitle;
    public Label InspectDescription;
    public Label InspectDate;
    @FXML
    public Label InspectOfferType;
    @FXML
    public Label InspectFund;
    private int idOffer;
    private int idCategory;

    public Button getQuitInspect() {
        return quitInspect;
    }

    public void setData(Offer offer , Category category){
        idOffer=offer.getId();
        idCategory=category.getId();
        System.out.println(category);
        this.InspectTitle.setText(offer.getTitle());
        this.InspectDescription.setText(offer.getDescription());
        this.InspectDate.setText(String.valueOf(offer.getDate_created()));
        this.InspectOfferType.setText(category.getType());
        this.InspectFund.setText(String.valueOf(category.getAttribute1()) + "$");
    }

    public Button getDelete() {
        return delete;
    }

    public void deleteClicked(ActionEvent event) {
        OfferService os = new OfferService();
        CategoryService cs = new CategoryService();
        try {
            os.delete(this.idOffer);

            cs.delete(this.idCategory);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
