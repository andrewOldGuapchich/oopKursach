package com.example.oopkursach;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.Connection;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private AnchorPane anchorPane;

    @FXML
    private ImageView imageView;

    @FXML
    private Hyperlink exitLink;

    private double xOffset;
    private double yOffset;


    @FXML
    void initialize() throws FileNotFoundException {
        String path = "C:\\Users\\Andrew\\IdeaProjects\\oopKursach\\src\\main\\resources\\image\\" + readTempFile() + ".png";
        FileInputStream fileInputStream = null;
        try {
            File file = new File(path);
            if(!file.exists())
                fileInputStream = new FileInputStream("C:\\Users\\Andrew\\IdeaProjects\\oopKursach\\src\\main\\resources\\image\\image.png");
            else
                fileInputStream = new FileInputStream(path);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image image  = new Image(fileInputStream);
        imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setX(12);
        imageView.setY(12);


        linkGrade.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("grade.fxml"));
            try {
                AnchorPane pane = loader.load();
                anchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        linkSchedule.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("schedule-page.fxml"));
            try {
                AnchorPane pane = loader.load();
                anchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        linkLesson.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("lessons.fxml"));
            try {
                AnchorPane pane = loader.load();
                anchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        exitLink.setOnAction(actionEvent -> {
            exitLink.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("start.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() + xOffset);
                    stage.setY(event.getScreenY() + yOffset);
                }
            });

            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    xOffset = stage.getX() - mouseEvent.getScreenX();
                    yOffset = stage.getY() - mouseEvent.getScreenY();
                }
            });
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.show();
        });


        String name = connection.getStudent(readTempFile()).getName();
        nameLabel.setText(name);
        groupLabel.setText(String.valueOf(connection.getStudent(readTempFile()).getGroup()));
        ticketLabel.setText(String.valueOf(connection.getStudent(readTempFile()).getTicket()));
        anchorPane.getChildren().add(imageView);
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
