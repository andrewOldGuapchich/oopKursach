package com.example.oopkursach;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.example.oopkursach.dao.Authorization;
import com.example.oopkursach.dao.StudentParser;
import com.example.oopkursach.model.Grade;
import com.example.oopkursach.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AddController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button backButton;

    @FXML
    private TextField bookField;

    @FXML
    private Label group;

    @FXML
    private TextField groupField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField ticketField;

    @FXML
    void initialize() {
        saveButton.setOnAction(actionEvent -> {
            writeStudentOnFile(createStudent());
            writeLoginAndPassword();
            nameField.clear();
            loginField.clear();
            passwordField.clear();
            groupField.clear();
            ticketField.clear();
            bookField.clear();
        });

        backButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("show_employee.fxml"));

            try {
                AnchorPane pane = loader.load();
                anchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void writeLoginAndPassword(){
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

            mapPassword.put(loginField.getText(), passwordField.getText());
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

    private void writeStudentOnFile(Student student){
        try {
            StudentParser parserStudent = new StudentParser();
            List<Student> studentList = parserStudent.getStudentList(); //список всех студентов
            studentList.add(student);
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

    private Student createStudent(){
        Student student = new Student();
        student.setName(nameField.getText());
        student.setLogin(loginField.getText());
        student.setPassword(passwordField.getText());
        student.setGroup(Integer.parseInt(groupField.getText()));
        student.setTicket(Integer.parseInt(ticketField.getText()));
        student.setBook(Integer.parseInt(bookField.getText()));

        Grade grade = createGrade(Integer.parseInt(groupField.getText()));
        grade.setNumberTicket(Integer.parseInt(ticketField.getText()));

        student.setGrade(grade);
        return student;
    }

    private Grade createGrade(int group){
        Grade grade = new Grade();
        int course = -1;
        switch (group / 100 % 10) {
            case 9 -> course = 4;
            case 0 -> course = 3;
            case 1 -> course = 2;
            case 2 -> course = 1;
        }

        List<String> lessonList = new ArrayList<>();
        try {
            String path = "C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//lesson_for_instit.json";
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray institute = (JSONArray) jsonObject.get("institute");

            HashMap<Integer, HashMap<Integer, JSONArray>> map = new HashMap<>();
            for(Object o : institute) {
                JSONObject currentNumber = (JSONObject) o;
                int checkNumber = Integer.parseInt(String.valueOf(currentNumber.get("number")));

                HashMap<Integer, JSONArray> tempMap = new HashMap<>();
                JSONArray courseArray = (JSONArray) currentNumber.get("course");
                for (Object o1 : courseArray) {
                    JSONObject currentNumberCourse = (JSONObject) o1;
                    int checkNumberCourse = Integer.parseInt(String.valueOf(currentNumberCourse.get("number")));
                    JSONArray lessons = (JSONArray) currentNumberCourse.get("list_lessons");
                    tempMap.put(checkNumberCourse, lessons);
                }
                map.put(checkNumber, tempMap);
            }

            HashMap<Integer, JSONArray> temp = map.get(group / 1000);
            System.out.println(temp.size());
            JSONArray tempArr = temp.get(course);
            for (Object o : tempArr)
                lessonList.add(String.valueOf(o));


        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        HashMap<String, String> gradeMap = new HashMap<>();
        for (String s : lessonList) {
            gradeMap.put(s, " ");
        }

        grade.setGradeMap(gradeMap);
        return grade;
    }
}
