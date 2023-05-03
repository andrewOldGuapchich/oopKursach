package com.example.oopkursach.dao;

import com.example.oopkursach.model.Grade;
import com.example.oopkursach.model.Student;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentParser {
    private final List<Student> studentList = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();
    public void parser(){
        try{
            File file = new File("C://IdeaProjects//oopKursach//src//main//resources//datadirectory//students.json");

            Student student = objectMapper.readValue(file, Student.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void parse(){
        try{
            String path = "C://IdeaProjects//oopKursach//src//main//resources//datadirectory//students.json";
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray students = (JSONArray) jsonObject.get("students");

            for(Object o : students){
                JSONObject currentStudent = (JSONObject) o;
                Student student = new Student();
                student.setLogin(String.valueOf(currentStudent.get("login")));
                student.setName(String.valueOf(currentStudent.get("name")));
                student.setTicket(Integer.parseInt(String.valueOf(currentStudent.get("ticket"))));
                student.setBook(Integer.parseInt(String.valueOf(currentStudent.get("book"))));
                student.setGroup(Integer.parseInt(String.valueOf(currentStudent.get("group"))));

                Grade grade = new Grade();
                HashMap<String, String> mapGrade = new HashMap<>();
                JSONArray lessons = (JSONArray) currentStudent.get("lessons");
                JSONArray gradeArray = (JSONArray) currentStudent.get("grade");

                for(int i = 0; i < lessons.size(); i++){
                    mapGrade.put(String.valueOf(lessons.get(i)), String.valueOf(gradeArray.get(i)));
                }
                grade.setNumberTicket(Integer.parseInt(String.valueOf(currentStudent.get("ticket"))));
                grade.setGradeMap(mapGrade);

                student.setGrade(grade);
                studentList.add(student);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public StudentParser() {
        parse();
    }

    public List<Student> getStudentList() {
        return studentList;
    }

}