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
public class StudentsController {

    private final UserDAO userDAO;

    public StudentsController(UserDAO userDAO) {
        this.userDAO = userDAO;
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
    public void updateStudent( @RequestBody Student student) {
        userDAO.updateStudent(student);
    } 
}
