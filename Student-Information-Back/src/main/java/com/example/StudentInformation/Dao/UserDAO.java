package com.example.StudentInformation.Dao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.StudentInformation.modals.Faculty;
import com.example.StudentInformation.modals.Student;

import java.util.List;
import java.util.Map;


@Repository
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> getStudentLogin(String email, String password) {
        String sql = "SELECT * FROM STUDENT WHERE STUDENT_EMAIL = '" + email + "' AND STUDENT_PASSWORD = '" + password + "'";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Student(rs.getString("STUDENT_EMAIL"), rs.getInt("STUDENT_ID"), rs.getString("STUDENT_FNAME"), 
                    rs.getString("STUDENT_LNAME"), rs.getString("STUDENT_ADDRESS"), rs.getString("STUDENT_PHONE")    
            )
        );
    } 
    
    public List<Faculty> getFacultyLogin(String email, String password) {
        String sql = "SELECT * FROM FACULTY WHERE FACULTY_EMAIL = '" + email + "' AND FACULTY_PASSWORD = '" + password + "'";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Faculty(rs.getString("FACULTY_EMAIL"), rs.getInt("FACULTY_ID"), rs.getString("FACULTY_FNAME"), 
                    rs.getString("FACULTY_LNAME")  
            )
        );
    }  

}
