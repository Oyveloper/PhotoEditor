module com.oyvindmonsen {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;
    requires java.desktop;

    opens com.oyvindmonsen to javafx.fxml;
    exports com.oyvindmonsen;
    opens com.oyvindmonsen.controller to javafx.fxml;
    exports com.oyvindmonsen.controller;
}