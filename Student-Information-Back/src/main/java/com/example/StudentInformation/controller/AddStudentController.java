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
    private final AuditLogService auditLogService;

    public AddStudentController(UserDAO userDAO, AuditLogService auditLogService) {
        this.userDAO = userDAO;
        this.auditLogService = auditLogService;
    }

    @PostMapping("/api/addStudent")
    public boolean addStudent(@RequestBody Student student) {
        boolean success = false;
        try {
            if (!student.verifyStudent()) {
                // Logs when the input data fails verifyStudent(), e.g. input data doesn't follow expected format or is NULL
                auditLogService.record("faculty@uwec.edu", "Faculty", "CREATE_STUDENT", "FAILURE",
                    "Faculty failed to create student: invalid data");
                return false;
            }

            if (student.getId() > 9999 || student.getId() < 999) {
                // Logs failure in creating a student because of an invalid ID
                auditLogService.record("faculty@uwec.edu", "Faculty", "CREATE_STUDENT", "FAILURE",
                    "Faculty failed to create student: invalid ID");
                return false;
            }

            userDAO.addStudent(student);
            Student testStudent = userDAO.checkStudent(student.getId());

            if (student.getId() == testStudent.getId()) {
                success = true;

                // Logs success in creating a student
                auditLogService.record("faculty@uwec.edu", "Faculty", "CREATE_STUDENT", "SUCCESS",
                    "Faculty created new student " + student.getFirstName() + " " + student.getLastName() + " (ID: " + student.getId() + ")");
            } else {
                // Logs mismatch after creating a student and ID doesn't match, e.g. database full/corrupted or another bug
                auditLogService.record("faculty@uwec.edu", "Faculty", "CREATE_STUDENT", "FAILURE",
                    "Faculty attempted to create student but verification failed");
            }
        } catch (Exception e) {
            // Logs any unexpected errors
            auditLogService.record("faculty@uwec.edu", "Faculty", "CREATE_STUDENT", "FAILURE",
                "Error creating student: " + e.getMessage());
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
        try {
            if (!student.verifyStudent()) {
                auditLogService.record("faculty@uwec.edu", "Faculty", "UPDATE_STUDENT", "FAILURE",
                    "Faculty failed to update student: invalid data");
                return ResponseEntity.status(404).body("An input field failed");
            }

            boolean updated = userDAO.updateStudent(id, student);
            if (updated) {
                // Logs success in updating a student's information
                auditLogService.record("faculty@uwec.edu", "Faculty", "UPDATE_STUDENT", "SUCCESS",
                    "Faculty updated student " + id + " successfully");
                return ResponseEntity.ok("Student updated successfully.");
            } else {
                // Logs failure in updating a student's information
                auditLogService.record("faculty@uwec.edu", "Faculty", "UPDATE_STUDENT", "FAILURE",
                    "Faculty tried to update student " + id + " but student not found");
                return ResponseEntity.status(404).body("Student not found.");
            }
        } catch (Exception e) {
            // Logs unexpected errors
            auditLogService.record("faculty@uwec.edu", "Faculty", "UPDATE_STUDENT", "FAILURE",
                "Error updating student " + id + ": " + e.getMessage());
            return ResponseEntity.status(500).body("Server error during update.");
        }
    }
}