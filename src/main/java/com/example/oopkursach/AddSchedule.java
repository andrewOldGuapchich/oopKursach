package com.example.oopkursach;

import java.io.*;
import java.net.URL;
import java.util.*;

import com.dlsc.formsfx.model.structure.Group;
import com.example.oopkursach.dao.GroupParser;
import com.example.oopkursach.model.Schedule;
import com.example.oopkursach.model.Student;
import com.example.oopkursach.model.StudentGroup;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AddSchedule {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button button;

    @FXML
    private AnchorPane schedulePane;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Button saveButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField labelNumber;

    private final List<TextField> list = new ArrayList<>();

    @FXML
    void initialize() {
        createScheduleTable();
        saveButton.setOnAction(actionEvent -> {
            addSchedule(createSchedule());
            writeGroupFile(createSchedule());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("show_employee.fxml"));

            try {
                AnchorPane pane = loader.load();
                mainAnchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        button.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("show_employee.fxml"));

            try {
                AnchorPane pane = loader.load();
                mainAnchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void createScheduleTable(){
        Font font = new Font("Constantia", 18);
        Label[] days = new Label[]{new Label("Понедельник"), new Label("Вторник"), new Label("Среда"),
                new Label("Четверг"), new Label("Пятница")};
        for(int i = 0; i < days.length; i++){
            days[i].setFont(font);
            days[i].setLayoutX(10);
            days[i].setLayoutY(i * 215);
            days[i].setPrefWidth(300);
            days[i].setPrefHeight(30);
            days[i].setStyle("-fx-text-fill: #2196f3");
            anchorPane.getChildren().add(days[i]);
        }
        for(int i = 0; i < 30; i++){
            TextField temp = new TextField();
            temp.setPrefHeight(30);
            temp.setPrefWidth(550);
            temp.setLayoutX(40);
            temp.setLayoutY(35 * ((i / 6) + 1) + 30 * i);
            list.add(temp);
            anchorPane.getChildren().add(temp);
        }
    }

    private Schedule createSchedule(){
        Schedule schedule = new Schedule();
        String[] days = new String[5];
        String temp = "";
        for(int i = 0; i < list.size(); i++){
            if(!list.get(i).getText().equals("")){
                temp += ((i % 6) + 1) + " пара - " + list.get(i).getText() + "\n";
            }

            if(i == 5 || i == 11 || i == 17 || i == 23 || i == 29) {
                if(temp.equals(""))
                    days[i / 6] = "Выходной";
                else
                    days[i / 6] = temp;
                temp = "";
            }
        }
        schedule.setGroup(Integer.parseInt(labelNumber.getText()));
        schedule.setDays(days);

        return schedule;
    }

    private void writeGroupFile(Schedule schedule) {
        GroupParser groupParser = new GroupParser();
        List<StudentGroup> list1 = groupParser.getGroupsList();
        StudentGroup temp = new StudentGroup();
        temp.setNumber(schedule.getGroup());
        temp.setStudentList(new ArrayList<>());

        list1.add(temp);

        try {
            File file = new File("src/main/resources/datadirectory/groups.json");
            FileReader reader = new FileReader(file);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            jsonObject.clear();
            JSONArray userArray = new JSONArray();

            for (StudentGroup x : list1) {
                JSONObject groupObj = new JSONObject();
                groupObj.put("number", x.getNumber());
                JSONArray listSt = new JSONArray();
                listSt.addAll(x.getStudentList());
                groupObj.put("list_students", listSt);
                userArray.add(groupObj);
            }

            JSONObject object = new JSONObject();
            object.put("groups", (Object) userArray);

            try (FileWriter writer = new FileWriter(file, false)) {
                writer.write(object.toJSONString());
                writer.flush();
            } catch (IOException ignored) {

            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

        private void addSchedule(Schedule schedule){
        List<Schedule> schedules = new ArrayList<>();
        try {
            String path = "/src/main/resources/datadirectory/schedule.json";
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray scheduleArray = (JSONArray) jsonObject.get("schedule");

            for(Object o : scheduleArray) {
                Schedule tempSchedule = new Schedule();
                String[] temp = new String[5];
                temp[0] = String.valueOf(((JSONObject) o).get("monday"));
                temp[1] = String.valueOf(((JSONObject) o).get("tuesday"));
                temp[2] = String.valueOf(((JSONObject) o).get("wednesday"));
                temp[3] = String.valueOf(((JSONObject) o).get("thursday"));
                temp[4] = String.valueOf(((JSONObject) o).get("friday"));

                tempSchedule.setGroup(Integer.parseInt(String.valueOf(((JSONObject) o).get("group"))));
                tempSchedule.setDays(temp);

                schedules.add(tempSchedule);
            }

            for(Schedule x : schedules){
                if(x.getGroup().intValue() == schedule.getGroup().intValue()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка ввода");
                    alert.setHeaderText(null);
                    alert.setContentText("Расписание для это группы уже существует!");
                    alert.showAndWait();
                    return;
                }
            }

            schedules.add(schedule);

            File file = new File("src/main/resources/datadirectory/schedule.json");
            jsonObject.clear();
            JSONArray scheduleArr = new JSONArray();
            for(Schedule x : schedules){
                JSONObject scheduleObj = new JSONObject();
                scheduleObj.put("group", x.getGroup());
                scheduleObj.put("monday", x.getDays()[0]);
                scheduleObj.put("tuesday", x.getDays()[1]);
                scheduleObj.put("wednesday", x.getDays()[2]);
                scheduleObj.put("thursday", x.getDays()[3]);
                scheduleObj.put("friday", x.getDays()[4]);

                scheduleArr.add(scheduleObj);
            }

            JSONObject object = new JSONObject();
            object.put("schedule", (Object) scheduleArr);

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
