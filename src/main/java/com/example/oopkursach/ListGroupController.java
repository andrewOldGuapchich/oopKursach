package com.example.oopkursach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.StudentParser;
import com.example.oopkursach.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class ListGroupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button button;

    @FXML
    private Label group;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Button saveButton;

    @FXML
    void initialize() {
        group.setText(readTempFile());
        for(int i = 0; i < linksList().size(); i++)
            anchorPane.getChildren().add(linksList().get(i));
        button.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("groups-page.fxml"));
            try {
                AnchorPane pane = loader.load();
                mainAnchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //список группы
    private static List<Student> groupList(Integer number){
        StudentParser studentMapper = new StudentParser();
        List<Student> studentList = new ArrayList<>();

        for(Student x : studentMapper.getStudentList()){
            if(x.getGroup().intValue() == number.intValue()){
                studentList.add(x);
            }
        }
        return studentList;
    }

    private List<Hyperlink> linksList(){
        List<Hyperlink> list = new ArrayList<>();
        int step = 0;
        for (Student x : groupList(Integer.parseInt(readTempFile()))){
            Font font = new Font("Constantia", 18);
            Hyperlink tempStudent = new Hyperlink();
            tempStudent.setFocusTraversable(false);
            tempStudent.setFont(font);
            tempStudent.setStyle("-fx-text-fill: #2196f3");

            tempStudent.setLayoutX(5);
            tempStudent.setLayoutY(35 + step * 25);
            tempStudent.setPrefWidth(600);
            tempStudent.setPrefHeight(25);
            tempStudent.setText(x.getName());

            list.add(tempStudent);
            step++;
        }
        return list;
    }


    private static String readTempFile(){
        String line = null;
        try{
            BufferedReader reader = new BufferedReader(
                    new FileReader("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//temp_file_group.txt"));
            line = reader.readLine();
        } catch (IOException ignored){

        }
        return line;
    }
}
