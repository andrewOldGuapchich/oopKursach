package com.example.oopkursach.dao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Authorization {

    private final HashMap<String, String> authMap = new HashMap<>();



    public HashMap<String, String> getAuthMap() {
        String path = null;
        switch (getParam()) {
            case "student" -> path =
                    "C://IdeaProjects//oopKursach//src//main//resources//datadirectory//students_login_password.json";
            case "teacher" -> path =
                    "C://IdeaProjects//oopKursach//src//main//resources//datadirectory//teachers_login_password.json";
            case "employee" -> path =
                    "C://IdeaProjects//oopKursach//src//main//resources//datadirectory//employee_login_password.json";
        }

        try {
            assert path != null;
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray authArray = (JSONArray) jsonObject.get("users");
            for(Object o : authArray){
                JSONObject innerObject = (JSONObject) o;
                authMap.put(String.valueOf(innerObject.get("login")), String.valueOf(innerObject.get("password")));
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return authMap;
    }

    private String getParam(){
        String line = null;
        try{
            BufferedReader reader = new BufferedReader(
                    new FileReader("C://IdeaProjects//oopKursach//src//main//resources//datadirectory//init_author.txt"));
            line = reader.readLine();
        } catch (IOException ignored){

        }
        return line;
    }
}
