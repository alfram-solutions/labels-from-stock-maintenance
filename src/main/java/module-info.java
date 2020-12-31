module hellolabels {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires com.jfoenix;
    requires java.sql;
    requires commons.csv;

    opens labels_from_stock_maintenance to javafx.fxml;
    exports labels_from_stock_maintenance;
}