package labels_from_stock_maintenance;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TimeController implements Initializable {

    @FXML
    public Spinner<Integer> spinHours;

    @FXML
    public Spinner<Integer> spinMins;

    @FXML
    public Button btnCancel;

    @FXML
    public Button btnOK;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        final int initialValue = 0;

        SpinnerValueFactory hoursSpinnerValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, initialValue);
        spinHours.setValueFactory(hoursSpinnerValueFactory);

        SpinnerValueFactory minsSpinnerValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, initialValue);
        spinMins.setValueFactory(minsSpinnerValueFactory);
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
