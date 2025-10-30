package com.example.StudentInformation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;


@SpringBootApplication
public class StudentInformationApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentInformationApplication.class, args);
	}

	@Bean
    public CommandLineRunner testConnection(DataSource dataSource) {
        return args -> {
            try (var conn = dataSource.getConnection()) {
                System.out.println("✅ Connected to: " + conn.getMetaData().getURL());
            } catch (Exception e) {
                System.out.println("❌ Database connection failed: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}