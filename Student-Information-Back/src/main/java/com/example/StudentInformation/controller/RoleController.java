package com.example.StudentInformation.controller;
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
public class RoleController {
    private final RoleService roleService;

    private final UserDAO userDAO;

    public RoleController( RoleService roleService, UserDAO userDAO) {
        this.roleService = roleService;
        this.userDAO = userDAO;
    }
    @PostMapping("/api/facultyLogin")
    public ResponseEntity<?> facultyLogin( @RequestParam String email, @RequestParam String password) {

        List<Faculty> user = this.userDAO.getFacultyLogin(email, password);
        if (user.size() < 1) {
            return null;
        } else {
            String token = roleService.generateToken(email, Role.FACULTY);
            return ResponseEntity.ok(Map.of(
            "token", token,
            "role", Role.FACULTY
        ));
        }
    } 

    @PostMapping("/api/studentLogin")
    public ResponseEntity<?> studentLogin( @RequestParam String email, @RequestParam String password) {
        List<Student> user = this.userDAO.getStudentLogin(email, password);
        if (user.size() < 1) {
            return null;
        } else {
            String token = roleService.generateToken(email, Role.STUDENT);
            return ResponseEntity.ok(Map.of(
                "token", token,
                "role", Role.STUDENT
            ));
        }
       
    } 
}
