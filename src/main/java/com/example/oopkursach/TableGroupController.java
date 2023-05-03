package com.example.oopkursach;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.example.oopkursach.dao.Connection;
import com.example.oopkursach.dao.StudentParser;
import com.example.oopkursach.model.Grade;
import com.example.oopkursach.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TableGroupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label group;


    @FXML
    private Button button;

    @FXML
    private Button saveButton;

    private final List<TextField> markList = new ArrayList<>();

    @FXML
    void initialize() {
        group.setText(readTempFile());
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

        // TODO: 13.12.2022 дописать вставку в файл
        saveButton.setOnAction(actionEvent -> {
            StudentParser parserStudent = new StudentParser();
            List<Student> studentList = parserStudent.getStudentList(); //список всех студентов

            for(int i = 0; i < nameList().size(); i++){
                Student tempStudent = groupList(Integer.parseInt(readTempFile())).get(i);


                if(!isInteger(markList().get(i).getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка ввода");
                    alert.setHeaderText(null);
                    alert.setContentText("Введено неверный значение!");
                    alert.showAndWait();
                    break;
                }
                else if(!isGoodValue(Integer.parseInt(markList().get(i).getText()))){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка ввода");
                    alert.setHeaderText(null);
                    alert.setContentText("Введено неверный значение!");
                    alert.showAndWait();
                    break;
                }
                else {
                    studentList.removeIf(x -> x.getTicket() == tempStudent.getTicket().intValue());
                    Grade grade = tempStudent.getGrade();
                    grade.getGradeMap().remove(getNameLesson());
                    grade.getGradeMap().put(getNameLesson(), markList().get(i).getText());
                    tempStudent.setGrade(grade);
                    studentList.add(tempStudent);
                }
            }
            writeOnStudentJson(studentList);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("table-group.fxml"));
            try {
                AnchorPane pane = loader.load();
                mainAnchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });



        for(int i = 0; i < nameList().size(); i++){
            anchorPane.getChildren().add(nameList().get(i));
            anchorPane.getChildren().add(markList().get(i));
        }
    }

    private void writeOnStudentJson(List<Student> list){
        try {
            File file = new File("src/main/resources/datadirectory/students.json");
            FileReader reader = new FileReader(file);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            jsonObject.clear();
            JSONArray studentsArray = new JSONArray();
            for (Student x : list) {
                JSONObject studentObject = new JSONObject();
                JSONArray lessons = new JSONArray();
                JSONArray grade = new JSONArray();

                for (Map.Entry<String, String> entry : x.getGrade().getGradeMap().entrySet()) {
                    lessons.add(entry.getKey());
                    grade.add(entry.getValue());
                }
                studentObject.put("login", x.getLogin());
                studentObject.put("name", x.getName());
                studentObject.put("ticket", x.getTicket());
                studentObject.put("book", x.getBook());
                studentObject.put("group", x.getGroup());
                studentObject.put("lessons", lessons);
                studentObject.put("grade", grade);

                studentsArray.add(studentObject);
            }

            JSONObject object = new JSONObject();
            object.put("students", (Object) studentsArray);

            try (FileWriter writer = new FileWriter(file, false)){
                writer.write(object.toJSONString());
                writer.flush();
            } catch (IOException ignored){

            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
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

    private static String readTempFile(){
        String line = null;
        try{
            BufferedReader reader = new BufferedReader(
                    new FileReader("C://IdeaProjects//oopKursach//src//main//resources//datadirectory//temp_file_group.txt"));
            line = reader.readLine();
        } catch (IOException ignored){

        }
        return line;
    }

    private static String getNameLesson(){
        String name;
        String temp = null;
        try{
            BufferedReader reader = new BufferedReader(
                    new FileReader("C://IdeaProjects//oopKursach//src//main//resources//datadirectory//temp_file_author.txt"));
            temp = reader.readLine();
        } catch (IOException ignored){

        }

        Connection connection = new Connection();
        name = connection.getTeacher(temp).getSubjName();
        return name;
    }

    private static List<TextField> nameList(){
        List<TextField> list = new ArrayList<>();
        int step = 0;
        for (Student x : groupList(Integer.parseInt(readTempFile()))){
            Font font = new Font("Constantia", 18);

            TextField name = new TextField();
            name.setEditable(false);
            name.setFont(font);
            name.setStyle("-fx-text-fill: #2196f3");

            name.setLayoutX(5);
            name.setLayoutY(35 + step * 35);
            name.setMinWidth(540);
            name.setMaxHeight(35);
            name.setText(x.getName());

            list.add(name);
            step++;
        }
        return list;
    }

    private List<TextField> markList(){
        int step = 0;
        for (Student x : groupList(Integer.parseInt(readTempFile()))){
            Font font = new Font("Constantia", 18);
            TextField mark = new TextField();
            mark.setFont(font);
            mark.setStyle("-fx-text-fill: #2196f3");

            mark.setLayoutX(550);
            mark.setLayoutY(35 + step * 35);
            mark.setPrefWidth(125);
            mark.setMaxHeight(35);

            if(String.valueOf(x.getGrade().getGradeMap().get(getNameLesson())) == null)
                mark.setText("");
            else
                mark.setText(String.valueOf(x.getGrade().getGradeMap().get(getNameLesson())));
            markList.add(mark);
            step++;
        }
        return markList;
    }

    public boolean isInteger(String value){
        try{
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException ignored) {
        }
        return false;
    }

    private boolean isGoodValue(int a){
        return a >= 2 && a <= 5;
    }
}
