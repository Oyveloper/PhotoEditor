module com.oyvindmonsen {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;

    opens com.oyvindmonsen to javafx.fxml;
    exports com.oyvindmonsen;
}