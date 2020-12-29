package labels_from_stock_maintenance;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class DBController {

    Properties configProps;
    Optional<Connection> connection;

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

}
