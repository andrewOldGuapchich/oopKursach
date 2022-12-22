package com.example.oopkursach;

import java.io.*;
import java.net.URL;
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

public class RemoveController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button button;

    @FXML
    private AnchorPane gradeAnchorPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Hyperlink linkRemove;

    @FXML
    void initialize() {
        Student student = createStudent();
        nameLabel.setText(student.getName() + " " + student.getGroup());
        createGradeScrollPane();

        linkRemove.setOnAction(actionEvent -> {
            removeLoinAndPassword();
            removeOnStudentsJson();
            System.out.println(removeStudentOnList().size());
        });

        button.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("list_group.fxml"));
            try {
                AnchorPane pane = loader.load();
                anchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void createGradeScrollPane() {
        Connection connection = new Connection();
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
        }
    }

    private String readTempFile() {
        String line = null;
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//temp_file_student.txt"));
            line = reader.readLine();
        } catch (IOException ignored) {

        }
        return line;
    }

    private static String readTempGroupFile() {
        String line = null;
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//temp_file_group.txt"));
            line = reader.readLine();
        } catch (IOException ignored) {

        }
        return line;
    }

    private static List<Student> groupList(Integer number) {
        StudentParser studentMapper = new StudentParser();
        List<Student> studentList = new ArrayList<>();

        for (Student x : studentMapper.getStudentList()) {
            if (x.getGroup().intValue() == number.intValue()) {
                studentList.add(x);
            }
        }
        return studentList;
    }

    private Student createStudent() {
        List<Student> studentList = groupList(Integer.parseInt(readTempGroupFile()));
        Student student = new Student();
        String login = readTempFile();
        for (Student x : studentList) {
            if(x.getLogin().equals(login)){
                student = x;
                break;
            }
        }
        return student;
    }

    private List<Student> removeStudentOnList(){
        StudentParser parserStudent = new StudentParser();
        List<Student> studentList = parserStudent.getStudentList(); //список всех студентов

        studentList.removeIf(x -> x.getLogin().equals(createStudent().getLogin()));

        return studentList;
    }

    private void removeLoinAndPassword(){
        HashMap<String, String> mapPassword = new HashMap<>();
        try {
            FileReader reader = new FileReader("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//students_login_password.json");
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray authArray = (JSONArray) jsonObject.get("users");
            for(Object o : authArray){
                JSONObject innerObject = (JSONObject) o;
                mapPassword.put(String.valueOf(innerObject.get("login")), String.valueOf(innerObject.get("password")));
            }
            System.out.println(mapPassword.size());
            mapPassword.remove(createStudent().getLogin());
            System.out.println(mapPassword.size());
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            File file = new File("src/main/resources/datadirectory/students_login_password.json");
            FileReader reader = new FileReader(file);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            jsonObject.clear();
            JSONArray userArray = new JSONArray();

            for(Map.Entry<String, String> entry : mapPassword.entrySet()){
                JSONObject userObject = new JSONObject();
                userObject.put("login", entry.getKey());
                userObject.put("password", entry.getValue());

                userArray.add(userObject);
            }

            JSONObject object = new JSONObject();
            object.put("users", (Object) userArray);

            try (FileWriter writer = new FileWriter(file, false)){
                writer.write(object.toJSONString());
                writer.flush();
            } catch (IOException ignored){

            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeOnStudentsJson(){
        try {

            List<Student> studentList = removeStudentOnList(); //список всех студентов
            File file = new File("src/main/resources/datadirectory/students.json");
            FileReader reader = new FileReader(file);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            jsonObject.clear();
            JSONArray studentsArray = new JSONArray();
            for(Student x : studentList){
                JSONObject studentObject = new JSONObject();
                JSONArray lessons = new JSONArray();
                JSONArray grade = new JSONArray();

                for(Map.Entry<String, String> entry : x.getGrade().getGradeMap().entrySet()){
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

}