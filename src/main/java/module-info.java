module com.example.trackingfinances {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.example.trackingfinances to javafx.fxml;
    exports com.example.trackingfinances;
}