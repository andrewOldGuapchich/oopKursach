package com.example.oopkursach;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.oopkursach.model.Schedule;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

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
            Schedule schedule = createSchedule();
            System.out.println(labelNumber.getText());
            for(int i = 0; i < 5; i++){
                System.out.println(schedule.getDays()[i]);
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
}
