package com.example.oopkursach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.Connection;
import com.example.oopkursach.dao.StudentMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StudentShowController {
    private final Connection connection = new Connection();

    //private
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label nameLabel;

    @FXML
    private Label groupLabel;

    @FXML
    private Label ticketLabel;

    @FXML
    private Hyperlink linkGrade;

    @FXML
    private Hyperlink linkLesson;

    @FXML
    private Hyperlink linkSchedule;


    @FXML
    void initialize(){
        linkGrade.setOnAction(actionEvent -> {
            linkGrade.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("grade.fxml"));
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

        linkSchedule.setOnAction(actionEvent -> {
            linkSchedule.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("schedule-page.fxml"));
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

        linkLesson.setOnAction(actionEvent -> {
            linkLesson.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("lessons.fxml"));
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
        String name = connection.getStudent(readTempFile()).getName();
        nameLabel.setText(name);
        groupLabel.setText(String.valueOf(connection.getStudent(readTempFile()).getGroup()));
        ticketLabel.setText(String.valueOf(connection.getStudent(readTempFile()).getTicket()));
    }

    private List<Hyperlink> createList(Integer group){
        String path = "C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//lessons.json";
        List<Hyperlink> hyperlinks = new ArrayList<>();
        try{
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray discArray = (JSONArray) jsonObject.get("disciplines");
            JSONArray lessonsArr = null;
            for(Object o : discArray){
                JSONObject innerObj = (JSONObject) o;
                if(innerObj.get("group").equals(group)) {
                    lessonsArr = (JSONArray) innerObj.get("lessons");
                    break;
                }
            }

            assert lessonsArr != null;
            for (Object o : lessonsArr) {
                Hyperlink tempLink = new Hyperlink();
                tempLink.setText(String.valueOf(o));
                hyperlinks.add(tempLink);
            }

        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

        return hyperlinks;
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
