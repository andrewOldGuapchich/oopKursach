package com.example.oopkursach.model;

import java.util.List;

public class StudentGroup {
    private Integer number;
    private List<String> studentList;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<String> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<String> studentList) {
        this.studentList = studentList;
    }
}
