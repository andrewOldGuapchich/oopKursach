package com.example.oopkursach.dao;

import com.example.oopkursach.model.StudentGroup;
import com.example.oopkursach.model.Lesson;
import com.example.oopkursach.model.Student;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupParser {

    private final List<StudentGroup> groupsList = new ArrayList<>();
    public void parse(){
        try{
            String path = "C://IdeaProjects//oopKursach//src//main//resources//datadirectory//groups.json";
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray groups = (JSONArray) jsonObject.get("groups");

            for(Object o : groups){
                JSONObject currentGroup = (JSONObject) o;
                StudentGroup group = new StudentGroup();
                List<String> list = new ArrayList<>();
                JSONArray students = (JSONArray) currentGroup.get("list_students");
                for (Object student : students)
                    list.add(String.valueOf(student));
                group.setNumber(Integer.valueOf(String.valueOf(currentGroup.get("number"))));
                group.setStudentList(list);
                groupsList.add(group);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public GroupParser() {
        parse();
    }

    public List<StudentGroup> getGroupsList() {
        return groupsList;
    }

    public Lesson getLesson(String name){
        List<Lesson> list = new ArrayList<>();
        String path = "src/main/resources/datadirectory/groups.json";
        try{
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray lessons = (JSONArray) jsonObject.get("groups");

            for(Object o : lessons){
                JSONObject inner = (JSONObject) o;
                Student student = new Student();
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
