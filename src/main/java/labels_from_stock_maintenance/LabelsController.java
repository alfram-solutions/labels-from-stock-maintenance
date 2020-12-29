package labels_from_stock_maintenance;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class LabelsController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(LabelsController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void openConfig(ActionEvent actionEvent) throws IOException{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("configuration.fxml"));
            Parent parent = fxmlLoader.load();
            var configurationController = fxmlLoader.<ConfigurationController>getController();

            Scene scene = new Scene(parent, 600, 400);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
    }

    @FXML
    public void openStartTimePicker(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("time.fxml"));
        Parent parent = fxmlLoader.load();
        var configurationController = fxmlLoader.<TimeController>getController();

        Scene scene = new Scene(parent, 300, 100);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    public void openEndTimePicker(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("time.fxml"));
        Parent parent = fxmlLoader.load();
        var configurationController = fxmlLoader.<TimeController>getController();

        Scene scene = new Scene(parent, 300, 100);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
