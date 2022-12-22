package com.example.oopkursach;

import java.io.*;
import java.net.URL;
import java.util.*;

import com.example.oopkursach.dao.Connection;
import com.example.oopkursach.model.StudentGroup;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EmptyController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane anchorPane1;

    @FXML
    private Button button;


    @FXML
    private List<Hyperlink> hyperlinks = new ArrayList<>();

    @FXML
    void initialize() {
        Connection connection = new Connection();
        button.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            switch (readInitFile()){
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
        });

        switch (readInitFile()){
            case "teacher" ->
                    hyperlinks = createList(connection.getTeacher(readTempFile()).getName());
            case "employee" ->
                    hyperlinks = getHyperlinks(readTempFile());
        }

        int step = 1;
        for(Hyperlink h : hyperlinks) {
            h.setLayoutX(14);
            h.setLayoutY(2 + step * 25);
            h.setMaxWidth(500);
            h.setMaxHeight(23);
            Font font = new Font("Constantia", 16);
            h.setFont(font);
            h.setFocusTraversable(false);
            anchorPane1.getChildren().add(h);
            step++;
        }

        for(Hyperlink hyperlink : hyperlinks){
            hyperlink.setOnAction(actionEvent -> {
                writeTempFile(hyperlink.getText());
                FXMLLoader loader = new FXMLLoader();
                switch (readInitFile()){
                    case "teacher" ->
                            loader.setLocation(getClass().getResource("table-group.fxml"));
                    case "employee" -> {
                        switch (readStatusFile()){
                            case "group" -> loader.setLocation(getClass().getResource("list_group.fxml"));
                            case "schedule" -> loader.setLocation(getClass().getResource("schedule-page.fxml"));
                        }
                    }
                }
                try {
                    AnchorPane pane = loader.load();
                    anchorPane.getChildren().setAll(pane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }

    private List<Hyperlink> getHyperlinks(String key){
        List<StudentGroup> groups = new ArrayList<>();
        String path = "C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//groups.json";
        int numberInst = Integer.parseInt(key.replace("admin", ""));
        List<Hyperlink> hyperlinks = new ArrayList<>();

        try{
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray groupsArray = (JSONArray) jsonObject.get("groups");

            for(Object o : groupsArray){
                JSONObject innerObj = (JSONObject) o;
                List<String> tempList = new ArrayList<>();
                JSONArray students = (JSONArray) innerObj.get("list_students");
                for(Object o1 : students)
                    tempList.add(String.valueOf(o1));

                StudentGroup group = new StudentGroup();
                group.setNumber(Integer.parseInt(String.valueOf(innerObj.get("number"))));
                Collections.sort(tempList);
                group.setStudentList(tempList);

                if(group.getNumber() / 1000 == numberInst)
                    groups.add(group);
            }

            for (StudentGroup group : groups) {
                Hyperlink tempLink = new Hyperlink();
                tempLink.setText(String.valueOf(group.getNumber()));
                hyperlinks.add(tempLink);
            }

        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

        return hyperlinks;
    }

    private List<Hyperlink> createList(String key){
        String path = "C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//teachers.json";
        HashMap<String, JSONArray> map = new HashMap<>();
        List<Hyperlink> hyperlinks = new ArrayList<>();

        try{
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray groupsArray = (JSONArray) jsonObject.get("teachers");

            for(Object o : groupsArray){
                JSONObject innerObj = (JSONObject) o;
                map.put(String.valueOf(innerObj.get("name")), (JSONArray) innerObj.get("groups"));
            }


            for (Object o : map.get(key)) {
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
                    new File("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//temp_file_group.txt");
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

    private String readInitFile(){
        String line = null;
        try{
            BufferedReader reader = new BufferedReader(
                    new FileReader("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//init_author.txt"));
            line = reader.readLine();
        } catch (IOException ignored){

        }
        return line;
    }

    private String readStatusFile(){
        String line = null;
        try{
            BufferedReader reader = new BufferedReader(
                    new FileReader("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//schedule_or_list.txt"));
            line = reader.readLine();
        } catch (IOException ignored){

        }
        return line;
    }

}
