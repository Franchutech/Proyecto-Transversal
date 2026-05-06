package com.example.dentify;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DentifyController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}