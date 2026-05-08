module com.example.dentify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens com.example.dentify to javafx.fxml;

    exports com.example.dentify;
    exports com.example.dentify.Configuration;
}