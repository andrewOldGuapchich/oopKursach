package com.example.oopkursach;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button employeeButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button studentButton;

    @FXML
    private Button teacherButton;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    void initialize() {
        studentButton.setOnAction(actionEvent -> {
            writeTempFile("student");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login_page.fxml"));
            try {
                AnchorPane pane = loader.load();
                mainAnchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        employeeButton.setOnAction(actionEvent ->{
            writeTempFile("employee");
            employeeButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("empl_login_page.fxml"));

            loader.setLocation(getClass().getResource("empl_login_page.fxml"));
            try {
                AnchorPane pane = loader.load();
                mainAnchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        teacherButton.setOnAction(actionEvent -> {
            writeTempFile("teacher");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login_page.fxml"));
            try {
                AnchorPane pane = loader.load();
                mainAnchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void writeTempFile(String param){
        FileWriter writer = null;
        try{
            File file =
                    new File("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//init_author.txt");
            writer = new FileWriter(file, false);
            writer.write(param);
        } catch (IOException ignored){

        }
        finally {
            try {
                assert writer != null;
                writer.close();
            } catch (IOException var15) {
                System.err.println("File not found");
            }
        }
    }
}
