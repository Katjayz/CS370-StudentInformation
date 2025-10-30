package com.example.StudentInformation.controller;
import com.example.StudentInformation.modals.*;
import com.example.StudentInformation.service.*;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {
    private final RoleService roleService;

    public RoleController( RoleService roleService) {
        this.roleService = roleService;
    }
    @PostMapping("/api/facultyLogin")
    public ResponseEntity<?> facultyLogin( @RequestParam String email, @RequestParam String password) {
        String token = roleService.generateToken(email, Role.FACULTY);
        return ResponseEntity.ok(Map.of(
            "token", token,
            "role", Role.FACULTY
        ));
    } 

    @PostMapping("/api/studentLogin")
    public ResponseEntity<?> studentLogin( @RequestParam String email, @RequestParam String password) {
        String token = roleService.generateToken(email, Role.STUDENT);
        return ResponseEntity.ok(Map.of(
            "token", token,
            "role", Role.STUDENT
        ));
    } 
}
