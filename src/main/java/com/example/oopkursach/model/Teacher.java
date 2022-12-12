package com.example.oopkursach.model;

import java.util.List;

public class Teacher {
    private String login;
    private String name;
    private String subjName;
    private String department;

    private String post;
    private String auditorium;
    private List<Integer> listGroup;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjName() {
        return subjName;
    }

    public void setSubjName(String subjName) {
        this.subjName = subjName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public List<Integer> getListGroup() {
        return listGroup;
    }

    public void setListGroup(List<Integer> listGroup) {
        this.listGroup = listGroup;
    }
}
