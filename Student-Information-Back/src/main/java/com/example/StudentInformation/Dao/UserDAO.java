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

    public void addStudent(Student student) {
        String studentSql = "INSERT INTO STUDENT (STUDENT_ID, STUDENT_FNAME, STUDENT_LNAME, STUDENT_EMAIL, STUDENT_PASSWORD, STUDENT_ADDRESS, STUDENT_PHONE) VALUES " +
        " (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(studentSql, student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(), student.getPassword(), student.getAddress(), student.getPhone());
        
        String administrationSql = "INSERT INTO ADMINISTRATION (STUDENT_ID, STUDENT_GPA, STUDENT_BALANCE, STUDENT_CREDITS) \r\n" +
                        "VALUES (?, ?, ?, ?)" ;
        jdbcTemplate.update(administrationSql, student.getId(), student.getGpa(), student.getBalance(), student.getCredits());
    }

    public List<Student> getStudentLogin(String email, String password) {
        String sql = "SELECT * FROM STUDENT WHERE STUDENT_EMAIL = ? AND STUDENT_PASSWORD = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Student(rs.getString("STUDENT_EMAIL"), rs.getInt("STUDENT_ID"), rs.getString("STUDENT_FNAME"), 
                    rs.getString("STUDENT_LNAME"), rs.getString("STUDENT_ADDRESS"), rs.getString("STUDENT_PHONE")    
            ),
            email, password
        );
    } 

     public Student checkStudent(int id) {
        String sql = "SELECT * FROM STUDENT WHERE STUDENT_ID = ? ";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new Student(rs.getString("STUDENT_EMAIL"), rs.getInt("STUDENT_ID"), rs.getString("STUDENT_FNAME"), 
                    rs.getString("STUDENT_LNAME"), rs.getString("STUDENT_ADDRESS"), rs.getString("STUDENT_PHONE")    
            ),
            id
        );
    } 
    
    public List<Faculty> getFacultyLogin(String email, String password) {
        String sql = "SELECT * FROM FACULTY WHERE FACULTY_EMAIL = ? AND FACULTY_PASSWORD = ? ";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Faculty(rs.getString("FACULTY_EMAIL"), rs.getInt("FACULTY_ID"), rs.getString("FACULTY_FNAME"), 
                    rs.getString("FACULTY_LNAME")  
            ),
            email, password
        );
    }

    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM STUDENT";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Student(
                rs.getString("STUDENT_EMAIL"),
                rs.getInt("STUDENT_ID"),
                rs.getString("STUDENT_FNAME"),
                rs.getString("STUDENT_LNAME"),
                rs.getString("STUDENT_ADDRESS"),
                rs.getString("STUDENT_PHONE")
            )
        );
    }

     public Student getStudent(int id) {
        String sql = "SELECT * FROM STUDENT join ADMINISTRATION USING (STUDENT_ID)  WHERE STUDENT_ID = " + id ;
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new Student(rs.getString("STUDENT_EMAIL"), rs.getInt("STUDENT_ID"), rs.getString("STUDENT_FNAME"), 
                    rs.getString("STUDENT_LNAME"), rs.getString("STUDENT_ADDRESS"), rs.getString("STUDENT_PHONE"), 
                    rs.getDouble("STUDENT_GPA"), rs.getInt("STUDENT_CREDITS"), rs.getDouble("STUDENT_BALANCE")    
            )
        );
    } 

     public List<Student> getStudentList() {
        String sql = "SELECT * FROM STUDENT join ADMINISTRATION USING (STUDENT_ID) ";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Student(rs.getString("STUDENT_EMAIL"), rs.getInt("STUDENT_ID"), rs.getString("STUDENT_FNAME"), 
                    rs.getString("STUDENT_LNAME"), rs.getString("STUDENT_ADDRESS"), rs.getString("STUDENT_PHONE"), 
                    rs.getDouble("STUDENT_GPA"), rs.getInt("STUDENT_CREDITS"), rs.getDouble("STUDENT_BALANCE")    
            )
        );
    } 

    public void updateStudent(Student student) {
        String studentSql = "UPDATE STUDENT SET STUDENT_FNAME = ?, STUDENT_LNAME = ?, STUDENT_EMAIL = ?, STUDENT_ADDRESS = ?, STUDENT_PHONE = ? WHERE STUDENT_ID = ?";
        jdbcTemplate.update(studentSql,  student.getFirstName(), student.getLastName(), student.getEmail(),  student.getAddress(), student.getPhone(), student.getId());
        
        String administrationSql = "UPDATE ADMINISTRATION SET  STUDENT_GPA = ?, STUDENT_BALANCE = ?, STUDENT_CREDITS=? WHERE STUDENT_ID = ? " ;
        jdbcTemplate.update(administrationSql, student.getGpa(), student.getBalance(), student.getCredits(), student.getId());
    }


    public boolean updateStudent(int id, Student student) {
        String sql = "UPDATE STUDENT SET STUDENT_FNAME = ?, STUDENT_LNAME = ?, STUDENT_EMAIL = ?, STUDENT_ADDRESS = ?, STUDENT_PHONE = ? WHERE STUDENT_ID = ?";
        int rows = jdbcTemplate.update(sql, student.getFirstName(), student.getLastName(), student.getEmail(),
                                       student.getAddress(), student.getPhone(), id);
        String administrationSql = "UPDATE ADMINISTRATION SET  STUDENT_GPA = ?, STUDENT_BALANCE = ?, STUDENT_CREDITS=? WHERE STUDENT_ID = ? " ;
        jdbcTemplate.update(administrationSql, student.getGpa(), student.getBalance(), student.getCredits(), id);
        return rows > 0;
    }


}
