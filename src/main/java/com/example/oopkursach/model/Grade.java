package com.example.oopkursach.model;

import java.util.HashMap;
import java.util.List;

public class Grade {
    private Integer numberTicket;
    private HashMap<String, Integer> gradeMap;

    public Integer getNumberTicket() {
        return numberTicket;
    }

    public void setNumberTicket(Integer numberTicket) {
        this.numberTicket = numberTicket;
    }

    public HashMap<String, Integer> getGradeMap() {
        return gradeMap;
    }

    public void setGradeMap(HashMap<String, Integer> gradeMap) {
        this.gradeMap = gradeMap;
    }
}
