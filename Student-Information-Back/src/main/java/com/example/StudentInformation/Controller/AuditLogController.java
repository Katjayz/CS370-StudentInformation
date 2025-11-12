package com.example.StudentInformation.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController

@RequestMapping("/api/logs")
public class AuditLogController {

    //JdbcTemplate lets you run SQL statements from Java
    private final JdbcTemplate jdbcTemplate;

    public AuditLogController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<Map<String, Object>> getAllLogs() {

        // SQL command that will get the rows from AUDIT_LOG.
        String sql = "SELECT ID AS id, USER_EMAIL AS user_email, ROLE AS role, ACTION AS action, STATUS AS status, MESSAGE AS message, CREATED_AT AS created_at FROM AUDIT_LOG ORDER BY CREATED_AT DESC";


        return jdbcTemplate.queryForList(sql);
    }
}