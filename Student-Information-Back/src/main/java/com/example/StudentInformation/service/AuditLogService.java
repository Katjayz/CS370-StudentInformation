package com.example.StudentInformation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

@Service
public class AuditLogService {

    // JdbcTemplate lets you run SQL statements in Java.
    private final JdbcTemplate jdbcTemplate;
    private final HttpSession session;

    public AuditLogService(JdbcTemplate jdbcTemplate, HttpSession session) {
        this.jdbcTemplate = jdbcTemplate;
        this.session = session;
    }

    public void record(String userEmail, String role, String action, String status, String message) {
        if (userEmail == null || userEmail.isEmpty()) {
            Object sessionEmail = session.getAttribute("userEmail");
            if (sessionEmail != null) {
                userEmail = sessionEmail.toString();
            }
        }
        // SQL INSERT statement
        String sql = "INSERT INTO AUDIT_LOG (user_email, role, action, status, message) VALUES (?, ?, ?, ?, ?)";

        // 'created_at' will be automatically created in the database.
        jdbcTemplate.update(sql, userEmail, role, action, status, message);
    }
}