package labels_from_stock_maintenance;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

public class LabelsController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(LabelsController.class.getName());

    @FXML
    public DatePicker startDatePicker;

    @FXML
    public DatePicker endDatePicker;

    @FXML
    public TextField txtStartTime;

    @FXML
    public TextField txtEndTime;

    @FXML
    private ListView<String> purchasesIn;

    @FXML
    private ListView<Product> lvPurchases;

    @FXML
    private CheckBox chkPickAll;

    private int startHours;
    private int startMins;
    private int endHours;
    private int endMins;
    private LocalDate startDate;
    private LocalDate endDate;

    private DBController dbController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDateTime();
        try {
            dbController = new DBController();
        } catch (IOException | SQLException ioe) {
            ioe.printStackTrace();
        }

        purchasesIn.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, old_val, new_val) -> {
                    try {
                        var prodsList = dbController.runQueryProducts(Timestamp.valueOf(new_val));
                        buildProductList(prodsList);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );

    }


    private void initDateTime() {
        startHours = 0;
        startMins = 0;
        endHours = 0;
        endMins = 0;
        startDate = LocalDate.now();
        endDate = startDate.plusDays(1);

        txtStartTime.setText(startHours + ":" + startMins);
        txtEndTime.setText(endHours + ":" + endMins);
        startDatePicker.setValue(startDate);
        endDatePicker.setValue(endDate);
    }

    private void buildProductList(ArrayList<Product> prods) {
        ObservableList<Product> prodsObs = FXCollections.observableList(prods);
        lvPurchases.setItems(prodsObs);
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
        var timeController = fxmlLoader.<TimeController>getController();

        Scene scene = new Scene(parent, 300, 100);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        startHours = timeController.spinHours.getValue();
        startMins = timeController.spinMins.getValue();

        txtStartTime.setText(startHours + ":" +  startMins);

    }

    @FXML
    public void openEndTimePicker(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("time.fxml"));
        Parent parent = fxmlLoader.load();
        var timeController = fxmlLoader.<TimeController>getController();

        Scene scene = new Scene(parent, 300, 100);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        endHours = timeController.spinHours.getValue();
        endMins = timeController.spinMins.getValue();
        txtEndTime.setText(endHours + ":" + endMins);

    }


    @FXML
    public void getStartDate(ActionEvent actionEvent) {
        startDate = startDatePicker.getValue();


    }

    @FXML void getEndDate(ActionEvent actionEvent) {
        endDate = endDatePicker.getValue();

    }

    @FXML
    public void searchDatePurchase(ActionEvent actionEvent) {
       var startTimestamp = (convertDatesToTimestamp(startDate, startHours, startMins)).get();
       var endTimestamp = (convertDatesToTimestamp(endDate, endHours, endMins)).get();

       if (startTimestamp.after(endTimestamp)) {
           var wrongDateAlert = new Alert(Alert.AlertType.WARNING);
           wrongDateAlert.setContentText("Invalid dates");
           wrongDateAlert.showAndWait();
       } else {
           try {
               var purchaseDateList = dbController.runQueryDatePurchase(startTimestamp, endTimestamp);
               ObservableList<String> purchaseDates = FXCollections.observableList(purchaseDateList);
               purchasesIn.setItems(purchaseDates);
           } catch (SQLException se) {
               se.printStackTrace();
           }
       }

    }

    private Optional<Timestamp> convertDatesToTimestamp(LocalDate pDate, int pHours, int pMins) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
        var searchDateString = pDate.toString() + " " + pHours + ":" + pMins + ":" + 0;
        try {
            Date parsedDateStr = sdf.parse(searchDateString);
            Optional<Timestamp> searchTimestamp = Optional.ofNullable(new Timestamp(parsedDateStr.getTime()));
            return searchTimestamp;
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return Optional.empty();
    }

    @FXML
    public void isPickAll(ActionEvent actionEvent) {
        if (chkPickAll.isSelected()) {
            LOGGER.info("All selected");
        } else {
            LOGGER.info("None selected");
        }
    }
}
