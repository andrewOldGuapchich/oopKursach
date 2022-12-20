package com.example.oopkursach.dao;

import com.example.oopkursach.model.Teacher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherParser {
    private final List<Teacher> list = new ArrayList<>();

    public void parse(){
        try{
            String path = "C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//teachers.json";
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray teachers = (JSONArray) jsonObject.get("teachers");

            for(Object o : teachers){
                JSONObject innerObj = (JSONObject) o;
                Teacher teacher = new Teacher();
                teacher.setLogin(String.valueOf(innerObj.get("login")));
                teacher.setName(String.valueOf(innerObj.get("name")));
                teacher.setSubjName(String.valueOf(innerObj.get("subjName")));
                teacher.setDepartment(String.valueOf(innerObj.get("department")));
                teacher.setPost(String.valueOf(innerObj.get("post")));
                teacher.setAuditorium(String.valueOf(innerObj.get("auditorium")));

                JSONArray groups = (JSONArray) innerObj.get("groups");
                List<Integer> numbers = new ArrayList<>();

                for (Object group : groups)
                    numbers.add(Integer.parseInt(String.valueOf(group)));

                teacher.setListGroup(numbers);

                JSONArray schedule = (JSONArray) innerObj.get("schedule");
                List<String> scheduleList = new ArrayList<>();

                for (Object group : schedule)
                    scheduleList.add(String.valueOf(group));

                teacher.setSchedule(scheduleList);
                list.add(teacher);
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getSchedule(String login){
        for(Teacher x : list)
            if(x.getLogin().equals(login))
                return x.getSchedule();
        return null;
    }

    public TeacherParser(){
        parse();
    }

    public List<Teacher> getList() {
        return list;
    }
}
