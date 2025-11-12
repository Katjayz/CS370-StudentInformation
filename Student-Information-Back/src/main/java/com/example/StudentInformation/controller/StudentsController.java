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
public class StudentsController {

    private final UserDAO userDAO;
    private final AuditLogService auditLogService;

    public StudentsController(UserDAO userDAO, AuditLogService auditLogService) {
        this.userDAO = userDAO;
        this.auditLogService = auditLogService;
    }

    @GetMapping("/api/getStudent")
    public Student getStudent( @RequestParam String id) {
        int numberId = Integer.parseInt(id);
        return userDAO.getStudent(numberId);
    } 

    @GetMapping("/api/getStudentList")
    public List<Student> getStudentList() {
        return userDAO.getStudentList();
    } 

    @PostMapping("/api/updateStudent")
    public ResponseEntity<?> updateStudent(@RequestBody Student student) {
        try {
            // Returns early if validation fails
            if (!student.verifyStudent()) {
                auditLogService.record(
                    student.getEmail(),
                    "Student",
                    "UPDATE_PROFILE",
                    "FAILURE",
                    "Student update failed: invalid student data"
                );
                return ResponseEntity.badRequest().body("Invalid student data");
            }

            userDAO.updateStudent(student);

            auditLogService.record(
                student.getEmail(),
                "Student",
                "UPDATE_PROFILE",
                "SUCCESS",
                "Student updated successfully"
            );
            return ResponseEntity.ok("Student updated successfully");

        } catch (Exception e) {
            auditLogService.record(
                student.getEmail(),
                "Student",
                "UPDATE_PROFILE",
                "FAILURE",
                "Student update failed: " + e.getMessage()
            );
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping("/api/facultyUpdateStudent")
    public ResponseEntity<?> facultyUpdateStudent(
        @RequestBody Student student,
        @RequestParam String facultyEmail) {

        try {
            if (!student.verifyStudent()) {
                auditLogService.record(
                    facultyEmail,
                    "Faculty",
                    "UPDATE_STUDENT_RECORD",
                    "FAILURE",
                    "Faculty attempted to update invalid data for student: " + student.getEmail()
                );
                return ResponseEntity.badRequest().body("Invalid student data");
            }

            userDAO.updateStudent(student);

            auditLogService.record(
                facultyEmail,
                "Faculty",
                "UPDATE_STUDENT_RECORD",
                "SUCCESS",
                "Faculty updated student: " + student.getEmail()
            );

            return ResponseEntity.ok("Student updated successfully by faculty");

        } catch (Exception e) {
            // ✅ Log and return server error
            auditLogService.record(
                facultyEmail,
                "Faculty",
                "UPDATE_STUDENT_RECORD",
                "FAILURE",
                "Faculty update failed for student: " + student.getEmail() + " (" + e.getMessage() + ")"
            );
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }
}