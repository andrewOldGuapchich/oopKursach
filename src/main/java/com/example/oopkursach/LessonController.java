package com.example.oopkursach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.LessonParser;
import com.example.oopkursach.model.Lesson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LessonController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label controlLabel;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label hourLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Button button;

    @FXML
    private Hyperlink scheduleLink;

    @FXML
    private Hyperlink teacherLink;


    @FXML
    void initialize() {
        LessonParser parser = new LessonParser();
        Lesson lesson = parser.getLesson(readTempFile());

        nameLabel.setText(lesson.getTitle());
        departmentLabel.setText(lesson.getDepartment());
        controlLabel.setText(lesson.getControl());
        yearLabel.setText(lesson.getYear());
        hourLabel.setText(String.valueOf(lesson.getHour()));

        button.setOnAction(actionEvent -> {
            button.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("show_student.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        teacherLink.setText(lesson.getTeacher());
    }

    private String readTempFile(){
        String line = null;
        try{
            BufferedReader reader = new BufferedReader(
                    new FileReader("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//temp_file_lesson.txt"));
            line = reader.readLine();
        } catch (IOException ignored){

        }
        return line;
    }
}
