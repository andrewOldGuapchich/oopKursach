package com.example.oopkursach.dao;

import com.example.oopkursach.model.Schedule;
import com.example.oopkursach.model.Student;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScheduleParser {
    private final Connection connection = new Connection();

    public Schedule readSchedule() throws IOException {
        Student student = connection.getStudent(readTempFile());
        List<Schedule> list = new ArrayList<>();
        try {
            FileReader reader = new FileReader("src/main/resources/datadirectory/schedule.json");
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray groups = (JSONArray) jsonObject.get("schedule");

            for(Object o : groups) {
                Schedule schedule = new Schedule();
                String[] str = new String[5];
                JSONObject innerObject = (JSONObject) o;
                schedule.setGroup(Integer.parseInt(String.valueOf(innerObject.get("group"))));
                str[0] = String.valueOf(innerObject.get("monday"));
                str[1] = String.valueOf(innerObject.get("tuesday"));
                str[2] = String.valueOf(innerObject.get("wednesday"));
                str[3] = String.valueOf(innerObject.get("thursday"));
                str[4] = String.valueOf(innerObject.get("friday"));
                schedule.setDays(str);
                list.add(schedule);
            }

        } catch (ParseException ignored){

        }

        Schedule schedule = new Schedule();
        for (Schedule s : list) {
            if (s.getGroup().intValue() == student.getGroup().intValue()) {
                schedule = s;
            }
        }
        return schedule;
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
