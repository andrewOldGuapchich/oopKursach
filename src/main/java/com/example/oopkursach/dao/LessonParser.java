package com.example.oopkursach.dao;

import com.example.oopkursach.model.Lesson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LessonParser {
    private final Connection connection = new Connection();

    public Lesson getLesson(String name){
        List<Lesson> list = new ArrayList<>();
        String path = "src/main/resources/datadirectory/inform_lesson.json";
        try{
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray lessons = (JSONArray) jsonObject.get("inform");

            for(Object o : lessons){
                JSONObject inner = (JSONObject) o;
                Lesson lesson = new Lesson();
                lesson.setTitle(String.valueOf(inner.get("title")));
                lesson.setDepartment(String.valueOf(inner.get("department")));
                lesson.setYear(String.valueOf(inner.get("year")));
                lesson.setControl(String.valueOf(inner.get("control")));
                lesson.setHour(Integer.parseInt(String.valueOf(inner.get("hour"))));
                lesson.setTeacher(String.valueOf(inner.get("teacher")));
                list.add(lesson);
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

        for(Lesson l : list){
            if(l.getTitle().equals(name))
                return l;
        }
        return null;
    }
}
