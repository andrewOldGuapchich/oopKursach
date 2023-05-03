package com.example.oopkursach;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.Connection;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EmployeeShowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label auditoriumLabel;

    @FXML
    private Hyperlink exitLink;

    @FXML
    private Hyperlink addGroup;

    @FXML
    private Hyperlink addStudent;

    @FXML
    private Hyperlink addTeacher;

    @FXML
    private Label groupLabel11;

    @FXML
    private Label groupLabel111;

    @FXML
    private ImageView imageView;

    @FXML
    private Hyperlink linkGroups;

    @FXML
    private Hyperlink linkSchedule;

    @FXML
    private Label nameLabel;

    @FXML
    private Label postLabel;

    @FXML
    private Hyperlink teachersLink;

    private final Connection connection = new Connection();

    private double xOffset;
    private double yOffset;

    @FXML
    void initialize() {
        nameLabel.setText(connection.getEmployee(readTempFile()).getName());

        addStudent.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("add_page.fxml"));

            try {
                AnchorPane pane = loader.load();
                anchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        addGroup.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("add_schedule.fxml"));

            try {
                AnchorPane pane = loader.load();
                anchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        linkGroups.setOnAction(actionEvent -> {
            writeTempFile("group");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("groups-page.fxml"));

            try {
                AnchorPane pane = loader.load();
                anchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        linkSchedule.setOnAction(actionEvent -> {
            writeTempFile("schedule");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("groups-page.fxml"));

            try {
                AnchorPane pane = loader.load();
                anchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        exitLink.setOnAction(actionEvent -> {
            exitLink.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("start.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() + xOffset);
                    stage.setY(event.getScreenY() + yOffset);
                }
            });

            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    xOffset = stage.getX() - mouseEvent.getScreenX();
                    yOffset = stage.getY() - mouseEvent.getScreenY();
                }
            });
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.show();
        });

    }

    private String readTempFile(){
        String line = null;
        try{
            BufferedReader reader = new BufferedReader(
                    new FileReader("C://IdeaProjects//oopKursach//src//main//resources//datadirectory//temp_file_author.txt"));
            line = reader.readLine();
        } catch (IOException ignored){

        }
        return line;
    }

    private void writeTempFile(String param){
        FileWriter writer = null;
        try{
            File file =
                    new File("C://IdeaProjects//oopKursach//src//main//resources//datadirectory//schedule_or_list.txt");
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
