package com.example.oopkursach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.ScheduleParser;
import com.example.oopkursach.dao.TeacherParser;
import com.example.oopkursach.model.Schedule;
import com.example.oopkursach.model.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class ScheduleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;

    @FXML
    private TextArea scheduleTextArea;

    @FXML
    private AnchorPane anchorPane;

    private final ScheduleParser scheduleParser = new ScheduleParser();

    @FXML
    void initialize() throws IOException {
        switch (getParam()){
            case "teacher" -> createScheduleTextAreaTeachers();
            case "student", "employee" -> createScheduleTextAreaStudents();
        }

        button.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            switch (getParam()){
                case "teacher" -> loader.setLocation(getClass().getResource("show_teacher.fxml"));
                case "student" -> loader.setLocation(getClass().getResource("show_student.fxml"));
                case "employee" -> loader.setLocation(getClass().getResource("groups-page.fxml"));
            }

            try {
                AnchorPane pane = loader.load();
                anchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }



    private void createScheduleTextAreaStudents() throws IOException {
        Schedule schedule = scheduleParser.readSchedule();
        String allSchedule = "Понедельник\n" + schedule.getDays()[0]+
                "\n\nВторник\n" + schedule.getDays()[1] +
                "\n\nСреда\n" + schedule.getDays()[2] +
                "\n\nЧетверг\n" + schedule.getDays()[3] +
                "\n\nПятница\n" + schedule.getDays()[4];
        scheduleTextArea.setStyle(("-fx-text-fill:  #49a8d1"));
        scheduleTextArea.setText(allSchedule);
    }

    private void createScheduleTextAreaTeachers() {
        TeacherParser parser = new TeacherParser();
        Font font = new Font("Constantia", 18);
        String schedule = "";

        for(String x : parser.getSchedule(readTempFile())){
            schedule += x;
            schedule += "\n";
        }
        scheduleTextArea.setFont(font);
        scheduleTextArea.setStyle(("-fx-text-fill:  #49a8d1"));
        scheduleTextArea.setText(schedule);
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

    private String getParam(){
        String line = null;
        try{
            BufferedReader reader = new BufferedReader(
                    new FileReader("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//init_author.txt"));
            line = reader.readLine();
        } catch (IOException ignored){

        }
        return line;
    }

}