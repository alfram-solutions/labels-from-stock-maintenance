package labels_from_stock_maintenance;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TimeController implements Initializable {

    @FXML
    public Button btnCancel;

    @FXML
    public Button btnOK;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    @FXML
    public void cancelTime(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void getTime(ActionEvent actionEvent) {
        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
    }
}
