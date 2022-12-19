package com.example.oopkursach.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

public class Grade {
    private Integer numberTicket;
    private HashMap<String, String> gradeMap;

    public Integer getNumberTicket() {
        return numberTicket;
    }

    public void setNumberTicket(Integer numberTicket) {
        this.numberTicket = numberTicket;
    }

    public HashMap<String, String> getGradeMap() {
        return gradeMap;
    }

    public void setGradeMap(HashMap<String, String> gradeMap) {
        this.gradeMap = gradeMap;
    }
}
