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
import jakarta.servlet.http.HttpSession;

@RestController
public class RoleController {
    private final RoleService roleService;
    private final UserDAO userDAO;
    private final AuditLogService auditLogService;

    public RoleController( RoleService roleService, UserDAO userDAO, AuditLogService auditLogService) {
        this.roleService = roleService;
        this.userDAO = userDAO;
        this.auditLogService = auditLogService;
    }
    @PostMapping("/api/facultyLogin")
    public ResponseEntity<?> facultyLogin( @RequestParam String email, @RequestParam String password, HttpSession session) {

        List<Faculty> user = this.userDAO.getFacultyLogin(email, password);
        if (user.size() < 1) {

            // Logs failed faculty login
            auditLogService.record(email, "Faculty", "LOGIN", "FAILURE", "Faculty login failed: invalid credentials");
            return ResponseEntity
                .status(401)
                .body("Invalid email or password");
        } else {
            int id = user.get(0).getId();
            String token = roleService.generateToken(String.valueOf(id), Role.FACULTY);
            session.setAttribute("userEmail", email);

            // Logs successful faculty login
            auditLogService.record(email, "Faculty", "LOGIN", "SUCCESS", "Faculty login successful");
            return ResponseEntity.ok(Map.of(
            "token", token,
            "role", Role.FACULTY
        ));
        }
    } 

    @PostMapping("/api/studentLogin")
    public ResponseEntity<?> studentLogin( @RequestParam String email, @RequestParam String password, HttpSession session) {
        List<Student> user = this.userDAO.getStudentLogin(email, password);
        if (user.size() < 1) {

            // Logs failed student login
            auditLogService.record(email, "Student", "LOGIN", "FAILURE", "Student login failed: invalid credentials");
            return ResponseEntity
                .status(401)
                .body("Invalid email or password");
        } else {
            int id = user.get(0).getId();
            String token = roleService.generateToken(String.valueOf(id), Role.STUDENT);
            session.setAttribute("userEmail", email);

            // Logs successful student login
            auditLogService.record(email, "Student", "LOGIN", "SUCCESS", "Student login successful");
            return ResponseEntity.ok(Map.of(
                "token", token,
                "role", Role.STUDENT
            ));
        }
       
    } 
}