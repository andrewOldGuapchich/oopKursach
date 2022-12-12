package com.example.oopkursach;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.oopkursach.dao.Connection;
import com.example.oopkursach.dao.StudentMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private final StudentMapper mapper = new StudentMapper();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button enterButton;

    @FXML
    private TextField loginField = new TextField();
    private String login;

    @FXML
    private PasswordField passwordField;
    private final Connection connection = new Connection();

    @FXML
    void initialize() {
        enterButton.setOnAction(actionEvent -> {
            enterButton.getScene().getWindow().hide();
            if(connection.isTruePassword(loginField.getText(), passwordField.getText())){
                login = loginField.getText();
                writeTempFile();
                FXMLLoader loader = new FXMLLoader();
                System.out.println(getParam());
                switch (getParam()) {
                    case "student" -> loader.setLocation(getClass().getResource("show_student.fxml"));
                    case "teacher" -> loader.setLocation(getClass().getResource("show_teacher.fxml"));
                }
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
        });
    }

    private void writeTempFile(){
        FileWriter writer = null;
        try{
            File file =
                    new File("C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//temp_file_author.txt");
            writer = new FileWriter(file, false);
            writer.write(login);
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
