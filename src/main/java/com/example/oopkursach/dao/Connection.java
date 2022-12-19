package com.example.oopkursach.dao;


import com.example.oopkursach.model.Student;
import com.example.oopkursach.model.Teacher;

public class Connection {
    private final StudentParser mapper = new StudentParser();
    private final TeacherParser parser = new TeacherParser();
    private final Authorization authorization = new Authorization();

    public boolean isTruePassword(String login, String password){
        String userPassword = authorization.getAuthMap().get(login);
        return password.equals(userPassword);
    }

    public Student getStudent(String login){
        Student student = new Student();
        for(Student x : mapper.getStudentList()){
            if(login.equals(x.getLogin())) {
                student = x;
                break;
            }
        }
        return student;
    }

    public Teacher getTeacher(String login) {
        Teacher teacher = new Teacher();
        for (Teacher x : parser.getList()) {
            if (login.equals(x.getLogin())) {
                teacher = x;
                break;
            }
        }
        return teacher;
    }
}
