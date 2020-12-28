package labels_from_stock_maintenance;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ConfigurationController implements Initializable {

    private final static Logger LOGGER = Logger.getLogger(ConfigurationController.class.getName());

    private String CONFIG_FILENAME = "configuration.properties";

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

            String username = txtUser.getText();
            String password = txtPassword.getText();
            String dbUrl = txtDBUrl.getText();

            Properties configProps = new Properties();
            configProps.setProperty("username", username);
            configProps.setProperty("password", password);
            configProps.setProperty("dbUrl", dbUrl);

            try {
                FileOutputStream configOutputStream = new FileOutputStream(CONFIG_FILENAME);
                configProps.store(configOutputStream, "");

                configOutputStream.close();
                LOGGER.info(CONFIG_FILENAME + " saved");

            } catch (IOException ioe) {
                LOGGER.warning(ioe.getMessage());
            }

        }

        private void  loadConfig() {
            try {
                Boolean isConfigFile = Files.exists(Path.of(CONFIG_FILENAME));

                if(isConfigFile) {
                    FileInputStream configInputStream = new FileInputStream(CONFIG_FILENAME);
                    Properties configProps = new Properties();
                    configProps.load(configInputStream);

                    setTextFields(configProps);

                    configInputStream.close();
                    LOGGER.info(CONFIG_FILENAME = " loaded");

                } else {
                    Properties emptyConfigProps = new Properties();
                    emptyConfigProps.setProperty("username", "");
                    emptyConfigProps.setProperty("password", "");
                    emptyConfigProps.setProperty("dbUrl", "");

                    setTextFields(emptyConfigProps);
                    LOGGER.info(CONFIG_FILENAME + " not found");

                }
            } catch (IOException ioe) {
                LOGGER.warning(ioe.getMessage());
            }
        }

        private void setTextFields(Properties props){
            txtUser.setText(props.getProperty("username"));
            txtPassword.setText(props.getProperty("password"));
            txtDBUrl.setText(props.getProperty("dbUrl"));
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadConfig();
    }
}

