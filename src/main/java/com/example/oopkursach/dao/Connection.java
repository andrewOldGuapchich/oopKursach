package com.example.oopkursach.dao;


import com.example.oopkursach.model.Employee;
import com.example.oopkursach.model.Student;
import com.example.oopkursach.model.Teacher;

public class Connection {
    private final StudentParser studentParser = new StudentParser();
    private final TeacherParser teacherParser = new TeacherParser();

    private final EmployeeParser employeeParser = new EmployeeParser();
    private final Authorization authorization = new Authorization();

    public boolean isTruePassword(String login, String password){
        String userPassword = authorization.getAuthMap().get(login);
        return password.equals(userPassword);
    }

    public Student getStudent(String login){
        Student student = new Student();
        for(Student x : studentParser.getStudentList()){
            if(login.equals(x.getLogin())) {
                student = x;
                break;
            }
        }
        return student;
    }

    public Teacher getTeacher(String login) {
        Teacher teacher = new Teacher();
        for (Teacher x : teacherParser.getList()) {
            if (login.equals(x.getLogin())) {
                teacher = x;
                break;
            }
        }
        return teacher;
    }

    public Employee getEmployee(String login){
        Employee employee = new Employee();
        for(Employee x : employeeParser.getEmployeeArrayList()){
            if (login.equals(x.getLogin())) {
                employee = x;
                break;
            }
        }
        return employee;
    }
}
