package labels_from_stock_maintenance;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

public class DBController {

    private static final Logger LOGGER = Logger.getLogger(DBController.class.getName());

    Properties configProps;
    Optional<Connection> connection;

    private String queryDatePurchaseIn = "SELECT DISTINCT DATENEW FROM STOCKDIARY WHERE REASON = 1 AND DATENEW BETWEEN ? AND ?";
    private String queryProducts = "SELECT NAME, CODE, PRICESELL FROM STOCKDIARY, PRODUCTS WHERE STOCKDIARY.product = PRODUCTS.id AND REASON = 1 AND DATENEW = ?";


    public DBController() throws IOException, SQLException{
        initDbProps();
        initConnection();


    }

    private void initDbProps() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("configuration.fxml"));
        Parent parent = fxmlLoader.load();
        var configurationController = fxmlLoader.<ConfigurationController>getController();

        var isConfigProps = configurationController.isConfigFile();
        if(!isConfigProps) {
            Scene scene = new Scene(parent, 600, 400);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }

        var configFileName = configurationController.getConfigFilename();
        FileInputStream configPropsInputStream = new FileInputStream(configFileName);
        configProps = new Properties();
        configProps.load(configPropsInputStream);

    }

    private void initConnection() throws SQLException {
        var username = configProps.getProperty("username");
        var password = configProps.getProperty("password");
        var dBUrl = configProps.getProperty("dbUrl");

        Properties userLogin = new Properties();
        userLogin.put("user", username);
        userLogin.put("password", password);

        connection = Optional.ofNullable(DriverManager.getConnection(
                dBUrl, userLogin
        ));
    }

    public ArrayList<String> runQueryDatePurchase(Timestamp startDate, Timestamp endDate) throws SQLException {
        var datePurchasesInList = new ArrayList<String>();
        try (PreparedStatement datePurchasesIn = connection.get().prepareStatement(queryDatePurchaseIn)) {
            datePurchasesIn.setTimestamp(1, startDate);
            datePurchasesIn.setTimestamp(2, endDate);
            ResultSet rs = datePurchasesIn.executeQuery();


            while (rs.next()) {
                datePurchasesInList.add(rs.getTimestamp("DATENEW").toString());
            }
        }

        return datePurchasesInList;
    }

    public ArrayList<Product> runQueryProducts(Timestamp inDate) throws SQLException {
        var purchases = new ArrayList<Product>();
        try (PreparedStatement queryProductsStmt = connection.get().prepareStatement(queryProducts)) {
            queryProductsStmt.setTimestamp(1, inDate);
            ResultSet rs = queryProductsStmt.executeQuery();

            while (rs.next()) {
                Product tempProd = new Product();
                String name = rs.getString("NAME");
                String code = rs.getString("code");
                double pricesell = rs.getDouble("PRICESELL");

                tempProd.setName(name);
                tempProd.setCode(code);
                tempProd.setPricesell(pricesell);

                purchases.add(tempProd);
            }
        }

        return purchases;
    }

}
