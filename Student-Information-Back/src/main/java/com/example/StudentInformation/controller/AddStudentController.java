package com.example.StudentInformation.Controller;
import com.example.StudentInformation.Dao.UserDAO;
import com.example.StudentInformation.modals.*;
import com.example.StudentInformation.service.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AddStudentController {

    private final UserDAO userDAO;

    public AddStudentController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    @PostMapping("/api/addStudent")
    public boolean addStudent( @RequestBody Student student) {
        boolean success = false;
        if ( student.verifyStudent() == false ) {
            return false;
        }
        if ( student.getId() > 9999 || student.getId() < 999 ) {
            return false;
        }
        userDAO.addStudent(student);
        Student testStudent = userDAO.checkStudent(student.getId());
        if (student.getId() == testStudent.getId()) {
            success = true;
        }
        return success;
    }
    
    @GetMapping("/api/students")
    public List<Student> getAllStudents() {
        return userDAO.getAllStudents();
    }

    @GetMapping("/api/students/{id}")
    public Student getStudentById(@PathVariable int id) {
        return userDAO.checkStudent(id);
    }

    @PutMapping("/api/students/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable int id, @RequestBody Student student) {
        if ( student.verifyStudent() == false ) {
            return ResponseEntity.status(404).body("An input field failed");
        }
        boolean updated = userDAO.updateStudent(id, student);
        if (updated) {
            return ResponseEntity.ok("Student updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Student not found.");
        }
    }

}
