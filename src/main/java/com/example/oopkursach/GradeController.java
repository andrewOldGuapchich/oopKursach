package com.example.oopkursach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.Connection;

import com.example.oopkursach.model.Grade;
import com.example.oopkursach.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

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
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane gradeAnchorPane;
    @FXML
    private Label nameLabel;

    @FXML
    void initialize() {
        createGradeScrollPane();
        Student student = connection.getStudent(readTempFile());
        nameLabel.setText(student.getName() + " " + student.getGroup());
        button.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("show_student.fxml"));
            try {
                AnchorPane pane = loader.load();
                anchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void createGradeScrollPane() {
        Grade grade = connection.getStudent(readTempFile()).getGrade();
        int step = 1;
        for (Map.Entry<String, String> entry : grade.getGradeMap().entrySet()) {
            TextField nameTextField = new TextField();
            TextField markTextField = new TextField();

            nameTextField.setLayoutY(step * 35);
            nameTextField.setLayoutX(14);
            nameTextField.setPrefWidth(486);
            nameTextField.setPrefHeight(30);

            markTextField.setLayoutY(step * 35);
            markTextField.setLayoutX(500);
            markTextField.setPrefWidth(160);
            markTextField.setPrefHeight(30);


            Font font = new Font("Constantia", 18);

            nameTextField.setEditable(false);
            nameTextField.setFont(font);
            nameTextField.setStyle("-fx-text-fill: #49a8d1");

            markTextField.setEditable(false);
            markTextField.setFont(font);
            markTextField.setStyle("-fx-text-fill: #49a8d1");

            nameTextField.setText(entry.getKey());
            markTextField.setText(entry.getValue());
            step++;
            gradeAnchorPane.getChildren().add(nameTextField);
            gradeAnchorPane.getChildren().add(markTextField);

            /*if(entry.getValue() == null){
                allGrade += entry.getKey() + "\n";
            }
            else
                allGrade += entry.getKey() +
                        " " +
                        entry.getValue() + "\n";
        gradeTextArea.setStyle(("-fx-text-fill:  #49a8d1"));
        gradeTextArea.setText(allGrade);*/
        }
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
