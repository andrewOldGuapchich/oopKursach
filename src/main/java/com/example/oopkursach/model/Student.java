package com.example.oopkursach.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
    @JsonProperty("login")
    private String login;
    private String password;
    @JsonProperty("name")
    private String name;
    @JsonProperty("ticket")
    private Integer ticket;
    @JsonProperty("book")
    private Integer book;
    @JsonProperty("group")
    private Integer group;

    @JsonIgnore
    private Grade grade;

    private static volatile Student instance;
    public static Student getInstance() {
        Student localInstance = instance;
        if (localInstance == null) {
            synchronized (Singleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Student();
                }
            }
        }
        return localInstance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    public Integer getBook() {
        return book;
    }

    public void setBook(Integer book) {
        this.book = book;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }


}
