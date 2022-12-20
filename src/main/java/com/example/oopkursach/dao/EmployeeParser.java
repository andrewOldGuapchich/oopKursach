package com.example.oopkursach.dao;

import com.example.oopkursach.model.Employee;
import com.example.oopkursach.model.Grade;
import com.example.oopkursach.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeParser {
    private final List<Employee> employeeArrayList = new ArrayList<>();

    public void parse(){
        try{
            String path = "C://Users//Andrew//IdeaProjects//oopKursach//src//main//resources//datadirectory//employee.json";
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray employees = (JSONArray) jsonObject.get("employees");

            for(Object o : employees){
                JSONObject currentStudent = (JSONObject) o;
                Employee employee = new Employee();
                employee.setLogin(String.valueOf(currentStudent.get("login")));
                employee.setName(String.valueOf(currentStudent.get("name")));

                employeeArrayList.add(employee);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public EmployeeParser() {
        parse();
    }

    public List<Employee> getEmployeeArrayList() {
        return employeeArrayList;
    }
}
