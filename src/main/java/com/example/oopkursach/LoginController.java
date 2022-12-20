package com.example.oopkursach;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.Connection;
import com.example.oopkursach.dao.StudentParser;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
    private final StudentParser mapper = new StudentParser();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button enterButton;

    @FXML
    private TextField loginField = new TextField();
    private String login;

    @FXML
    private PasswordField passwordField;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Hyperlink backLink;

    private double xOffset;
    private double yOffset;

    @FXML
    private Label titleLabel = new Label();
    private final Connection connection = new Connection();

    @FXML
    void initialize() {
        switch (getParam()) {
            case "student" ->  titleLabel.setText("Страница входа для студента");
            case "teacher" ->  titleLabel.setText("Страница входа для преподавателя");
            case "employee" ->  titleLabel.setText("Страница входа для сотрудника");
        }

        enterButton.setOnAction(actionEvent -> {
            if(connection.isTruePassword(loginField.getText(), passwordField.getText())){
                login = loginField.getText();
                writeTempFile();
                FXMLLoader loader = new FXMLLoader();
                switch (getParam()) {
                    case "student" ->
                        loader.setLocation(getClass().getResource("show_student.fxml"));

                    case "teacher" ->
                        loader.setLocation(getClass().getResource("show_teacher.fxml"));

                    case "employee" ->
                            loader.setLocation(getClass().getResource("show_employee.fxml"));
                }
                try {
                    AnchorPane pane = loader.load();
                    anchorPane.getChildren().setAll(pane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        backLink.setOnAction(actionEvent -> {
            backLink.getScene().getWindow().hide();

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

    private void writeTempFile(){
        FileWriter writer = null;
        try{
            File file =
                    new File("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//temp_file_author.txt");
            writer = new FileWriter(file, false);
            writer.write(login);
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

    private String getParam(){
        String line = null;
        try{
            BufferedReader reader = new BufferedReader(
                    new FileReader("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//init_author.txt"));
            line = reader.readLine();
        } catch (IOException ignored){

        }
        return line;
    }
}
