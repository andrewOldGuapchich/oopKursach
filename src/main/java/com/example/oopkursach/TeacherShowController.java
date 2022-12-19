package com.example.oopkursach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.Connection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TeacherShowController {
    private final Connection connection = new Connection();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label groupLabel11;

    @FXML
    private ImageView imageView;

    @FXML
    private Hyperlink linkGroups;

    @FXML
    private Hyperlink linkLesson;

    @FXML
    private Hyperlink linkSchedule;

    @FXML
    private Label nameLabel;

    @FXML
    private Label postLabel;

    @FXML
    private Label auditoriumLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    void initialize() {
        linkSchedule.setOnAction(actionEvent -> {
        });

        linkGroups.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("groups-page.fxml"));

            try {
                AnchorPane pane = loader.load();
                anchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        nameLabel.setText(connection.getTeacher(readTempFile()).getName());
        postLabel.setText(connection.getTeacher(readTempFile()).getPost());
        auditoriumLabel.setText(connection.getTeacher(readTempFile()).getAuditorium());
    }

    private String readTempFile(){
        String line = null;
        try{
            BufferedReader reader = new BufferedReader(
                    new FileReader("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//temp_file_author.txt"));
            line = reader.readLine();
        } catch (IOException ignored){

        }
        return line;
    }
}