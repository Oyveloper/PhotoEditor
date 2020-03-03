module com.oyvindmonsen {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;
    requires java.desktop;
    requires com.google.gson;

    opens com.oyvindmonsen to javafx.fxm, com.google.gsonl;
    opens com.oyvindmonsen.model to com.google.gson;
    exports com.oyvindmonsen;
    opens com.oyvindmonsen.controller to javafx.fxml;
    exports com.oyvindmonsen.controller;

}