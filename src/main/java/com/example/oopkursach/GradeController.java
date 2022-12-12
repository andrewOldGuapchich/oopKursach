package com.example.oopkursach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.Connection;

import com.example.oopkursach.model.Grade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class GradeController {
    private final Connection connection = new Connection();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;
    @FXML
    private TextArea gradeTextArea;

    @FXML
    void initialize() {
        createGradeTextArea();
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
    }

    public void createGradeTextArea(){
        Grade grade = connection.getStudent(readTempFile()).getGrade();
        String allGrade = "";
        for(Map.Entry<String, Integer> entry : grade.getGradeMap().entrySet()){
            allGrade += entry.getKey() +
                    " " +
                    entry.getValue() + "\n";
        }
        gradeTextArea.setText(allGrade);
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
