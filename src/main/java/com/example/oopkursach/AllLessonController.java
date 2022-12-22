package com.example.oopkursach;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.Connection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AllLessonController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private final List<Hyperlink> hyperlinks = new ArrayList<>();

    @FXML
    private Button button = new Button();

    @FXML
    private AnchorPane anchorPane;


    @FXML
    void initialize() {
        Connection connection = new Connection();
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
        int step = 1;
        for(Hyperlink h : createList(connection.getStudent(readTempFile()).getGroup())) {
            h.setLayoutX(14);
            h.setLayoutY(2 + step * 25);
            h.setMaxWidth(500);
            h.setMaxHeight(23);
            Font font = new Font("Constantia", 16);
            h.setFont(font);
            h.setFocusTraversable(false);
            anchorPane.getChildren().add(h);
            step++;
        }

        for(Hyperlink hyperlink : hyperlinks){
            hyperlink.setOnAction(actionEvent -> {
                writeTempFile(hyperlink.getText());
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("lesson-page.fxml"));
                try {
                    AnchorPane pane = loader.load();
                    anchorPane.getChildren().setAll(pane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }


    private List<Hyperlink> createList(Integer group){
        String path = "C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//lessons.json";
        HashMap<Integer, JSONArray> map = new HashMap<>();
        try{
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray discArray = (JSONArray) jsonObject.get("disciplines");

            for(Object o : discArray){
                JSONObject innerObj = (JSONObject) o;
                map.put(Integer.parseInt(String.valueOf(innerObj.get("group"))), (JSONArray) innerObj.get("lesson"));
            }

            for (Object o : map.get(group)) {
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

    private void writeTempFile(String title){
        FileWriter writer = null;
        try{
            File file =
                    new File("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//temp_file_lesson.txt");
            writer = new FileWriter(file, false);
            writer.write(title);
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
