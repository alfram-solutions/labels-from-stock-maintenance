package labels_from_stock_maintenance;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConfigurationController implements Initializable {

    private String CONFIG_FILENAME = "configuration";

        @FXML
        private TextField txtUser;

        @FXML
        private TextField txtPassword;

        @FXML
        private TextField txtDBUrl;

        @FXML
        void cancelConfig(ActionEvent event) {

        }

        @FXML
        void saveConfig(ActionEvent event) {

        }

        public void  loadConfig() {
            try {
                Boolean isConfigFile = Files.exists(Path.of(CONFIG_FILENAME));

                if(isConfigFile) {
                    FileInputStream configInputStream = new FileInputStream(CONFIG_FILENAME);
                    Properties configProps = new Properties();
                    configProps.load(configInputStream);

                    setTextFields(configProps);
                } else {
                    Properties emptyConfigProps = new Properties();
                    emptyConfigProps.setProperty("username", "");
                    emptyConfigProps.setProperty("password", "");
                    emptyConfigProps.setProperty("dbUrl", "");

                    setTextFields(emptyConfigProps);

                }
            } catch (IOException ioe) {

            }
        }

        private void setTextFields(Properties props){
            txtUser.setText(props.getProperty("username"));
            txtPassword.setText(props.getProperty("password"));
            txtDBUrl.setText(props.getProperty("dbUrl"));
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

