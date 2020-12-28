module hellolabels {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens labels_from_stock_maintenance to javafx.fxml;
    exports labels_from_stock_maintenance;
}