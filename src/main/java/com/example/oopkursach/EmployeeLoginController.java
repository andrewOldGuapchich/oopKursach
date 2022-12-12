package com.example.oopkursach;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.ConnectionDB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class EmployeeLoginController {
    private final ConnectionDB connectionDB;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button enterButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    public EmployeeLoginController(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    @FXML
    void initialize() {
        enterButton.setOnAction(actionEvent -> {

        });
    }

}
