module com.example.dentify {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dentify to javafx.fxml;
    exports com.example.dentify;
}