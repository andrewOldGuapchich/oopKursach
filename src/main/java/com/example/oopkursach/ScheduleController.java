package com.example.oopkursach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.Connection;
import com.example.oopkursach.dao.ScheduleParser;
import com.example.oopkursach.model.Grade;
import com.example.oopkursach.model.Schedule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ScheduleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;

    @FXML
    private TextArea scheduleTextArea;

    private final ScheduleParser scheduleParser = new ScheduleParser();

    @FXML
    void initialize() throws IOException {
        createScheduleTextArea();
        button.setOnAction(actionEvent -> {
            button.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("show_student.fxml"));
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
    }

    public void createScheduleTextArea() throws IOException {
        Schedule schedule = scheduleParser.readSchedule();
        String allSchedule = "Понедельник\n" + schedule.getDays()[0]+
                "\n\nВторник\n" + schedule.getDays()[1] +
                "\n\nСреда\n" + schedule.getDays()[2] +
                "\n\nЧетверг\n" + schedule.getDays()[3] +
                "\n\nПятница\n" + schedule.getDays()[4];
        scheduleTextArea.setText(allSchedule);
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