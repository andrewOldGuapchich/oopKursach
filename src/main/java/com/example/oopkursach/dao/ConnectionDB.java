package com.example.oopkursach.dao;
import com.example.oopkursach.model.Student;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class ConnectionDB {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public ConnectionDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Student getStudent(String login){
        return jdbcTemplate.query("select * from student\n" +
                "inner join student_login sl on student.id = sl.id_student\n" +
                "where login='?'",new Object[]{login}, new BeanPropertyRowMapper<>(Student.class)).stream().findAny().orElse(null);
    }

    public boolean isTruePassword(Integer login, String password){
        String userPassword = String.valueOf(jdbcTemplate.query("SELECT password FROM student_login WHERE login =?",
                new Object[]{login}, new BeanPropertyRowMapper<>(Student.class)).stream().findAny().orElse(null));
        return password.equals(userPassword);
    }
}
