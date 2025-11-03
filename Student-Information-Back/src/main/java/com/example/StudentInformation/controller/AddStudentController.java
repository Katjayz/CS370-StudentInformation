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
public class AddStudentController {

    private final UserDAO userDAO;

    public AddStudentController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    @PostMapping("/api/addStudent")
    public boolean addStudent( @RequestBody Student student) {
        boolean success = false;
        userDAO.addStudent(student);
        Student testStudent = userDAO.checkStudent(student.getId());
        if (student.getId() == testStudent.getId()) {
            success = true;
        }
        return success;
    } 
}
