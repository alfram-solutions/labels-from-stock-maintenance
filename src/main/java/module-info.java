module hellolabels {
    requires javafx.controls;
    requires javafx.fxml;

    opens labels_from_stock_maintenance to javafx.fxml;
    exports labels_from_stock_maintenance;
}