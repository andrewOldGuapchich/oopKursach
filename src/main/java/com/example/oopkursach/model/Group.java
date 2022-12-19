package com.example.oopkursach.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private Integer number;
    private List<Student> studentList;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
