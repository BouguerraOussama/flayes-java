package controllers.mainPage;

import javafx.scene.layout.Pane;
import test.Main;
import javafx.fxml.FXMLLoader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class FxmlLoader {
    private Pane view;
    public Pane getPage(String fileName) {
        try {
            URL fileURL = Main.class.getResource("/fxml/offers/"+fileName+".fxml");
            if (fileURL == null)
                throw new java.io.FileNotFoundException("File not found");
            view = new FXMLLoader().load(fileURL);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return view;
    }
}
