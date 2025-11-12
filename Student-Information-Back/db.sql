/* CS 370 Project 2 */

/* Drop tables safely if they exist */
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS ENROLLMENT;
DROP TABLE IF EXISTS ADMINISTRATION;
DROP TABLE IF EXISTS FACULTY;
DROP TABLE IF EXISTS STUDENT;

/* Create the STUDENT table */
CREATE TABLE STUDENT (
    STUDENT_ID INT PRIMARY KEY,
    STUDENT_FNAME VARCHAR(15) NOT NULL,
    STUDENT_LNAME VARCHAR(15) NOT NULL,
    STUDENT_EMAIL VARCHAR(30) NOT NULL UNIQUE,
    STUDENT_PASSWORD VARCHAR(30) NOT NULL,
    STUDENT_ADDRESS VARCHAR(30) NOT NULL,
    STUDENT_PHONE VARCHAR(13) NOT NULL
);

/* Create the FACULTY table */
CREATE TABLE FACULTY (
    FACULTY_ID INT PRIMARY KEY,
    FACULTY_FNAME VARCHAR(15) NOT NULL,
    FACULTY_LNAME VARCHAR(15) NOT NULL,
    FACULTY_EMAIL VARCHAR(30) NOT NULL UNIQUE,
    FACULTY_PASSWORD VARCHAR(30) NOT NULL
);

/* Create the ADMINISTRATION table (depends on STUDENT) */
CREATE TABLE ADMINISTRATION (
    STUDENT_ID INT NOT NULL PRIMARY KEY,
    STUDENT_GPA DECIMAL(3, 2) NOT NULL,
    STUDENT_BALANCE DECIMAL(10, 2) NOT NULL,
    STUDENT_CREDITS INT NOT NULL,
    CONSTRAINT fk_admin_student FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT(STUDENT_ID)
);

/* Create the ENROLLMENT table (depends on STUDENT and FACULTY) */
CREATE TABLE ENROLLMENT (
    ENROLLMENT_ID INT PRIMARY KEY,
    STUDENT_ID INT NOT NULL,
    FACULTY_ID INT NOT NULL,
    CONSTRAINT fk_enroll_student FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT(STUDENT_ID),
    CONSTRAINT fk_enroll_faculty FOREIGN KEY (FACULTY_ID) REFERENCES FACULTY(FACULTY_ID)
);

/* Create the AUDIT_LOG table */
CREATE TABLE AUDIT_LOG (
  ID BIGINT PRIMARY KEY AUTO_INCREMENT,
  USER_EMAIL VARCHAR(255),
  role VARCHAR(64),
  action VARCHAR(64) NOT NULL,
  status VARCHAR(16) NOT NULL,
  message VARCHAR(512),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

/* Insert student data */
INSERT INTO STUDENT (STUDENT_ID, STUDENT_FNAME, STUDENT_LNAME, STUDENT_EMAIL, STUDENT_PASSWORD, STUDENT_ADDRESS, STUDENT_PHONE)
VALUES 
(1001, 'Alice', 'Johnson', 'alice.johnson@uwec.edu', 'Pass@123', '123 Maple St', '555-123-4567'),
(1002, 'Brian', 'Smith', 'brian.smith@uwec.edu', 'Qwerty@9', '456 Oak Ave', '555-234-5678'),
(1003, 'Catherine', 'Lee', 'catherine.lee@uwec.edu', 'CatLee!22', '789 Pine Rd', '555-345-6789'),
(1004, 'David', 'Martinez', 'david.martinez@uwec.edu', 'DMart@45', '321 Birch Blvd', '555-456-7890'),
(1005, 'Ella', 'Brown', 'ella.brown@uwec.edu', 'Brownie#1', '654 Cedar Ct', '555-567-8901'),
(1006, 'Frank', 'Davis', 'frank.davis@uwec.edu', 'FrankD99', '987 Walnut Dr', '555-678-9012'),
(1007, 'Grace', 'Wilson', 'grace.wilson@uwec.edu', 'Grace#2024', '159 Elm St', '555-789-0123'),
(1008, 'Henry', 'Lopez', 'henry.lopez@uwec.edu', 'HLopez!77', '753 Spruce Ln', '555-890-1234'),
(1009, 'Isabella', 'Clark', 'isabella.clark@uwec.edu', 'Isa@777', '852 Chestnut Ave', '555-901-2345'),
(1010, 'Jack', 'Hall', 'jack.hall@uwec.edu', 'JHall2025', '951 Poplar Way', '555-012-3456'),
(1011, 'Karen', 'Garcia', 'karen.garcia@uwec.edu', 'KGarcia@1', '147 Sycamore St', '555-678-2345'),
(1012, 'Liam', 'Anderson', 'liam.anderson@uwec.edu', 'LiamA@88', '258 Willow Rd', '555-789-3456'),
(1013, 'Mia', 'Thompson', 'mia.thompson@uwec.edu', 'MiaT#33', '369 Redwood Ln', '555-890-4567'),
(1014, 'Noah', 'Bennett', 'noah.bennett@uwec.edu', 'NoahB@22', '741 Hickory Pl', '555-901-5678');

/* Insert administration data */
INSERT INTO ADMINISTRATION (STUDENT_ID, STUDENT_GPA, STUDENT_BALANCE, STUDENT_CREDITS) VALUES
(1001, 3.85, 1200.50, 45),
(1002, 2.90, 3400.75, 60),
(1003, 3.60, 2750.00, 72),
(1004, 3.10, 1800.25, 48),
(1005, 3.95, 450.00, 33),
(1006, 2.75, 3890.10, 54),
(1007, 3.50, 950.40, 66),
(1008, 2.40, 4200.00, 30),
(1009, 3.70, 1700.80, 78),
(1010, 2.95, 2999.99, 51),
(1011, 3.25, 2500.00, 63),
(1012, 3.80, 875.25, 84),
(1013, 3.45, 1125.60, 57),
(1014, 3.00, 2050.00, 39);

/* Insert faculty data */
INSERT INTO FACULTY (FACULTY_ID, FACULTY_FNAME, FACULTY_LNAME, FACULTY_EMAIL, FACULTY_PASSWORD) VALUES
(5001, 'John', 'Smith', 'john.smith@uwec.edu', 'JohnsP@$$word'),
(5002, 'Jane', 'Goodall', 'jane.goodall@uwec.edu', 'MoNkEyBuSiNeSs!'),
(5003, 'Jordan', 'Robinson', 'jordan.robinson@uwec.edu', 'Password?');

/* Sample query */
SELECT 
  CONCAT(S.STUDENT_FNAME, ' ', S.STUDENT_LNAME) AS STUDENT_NAME, 
  S.STUDENT_ID AS BLUGOLD_ID, 
  S.STUDENT_ADDRESS, 
  S.STUDENT_PHONE AS PHONE_NUMBER, 
  A.STUDENT_GPA, 
  A.STUDENT_CREDITS AS TOTAL_CREDITS, 
  A.STUDENT_BALANCE
FROM STUDENT S 
JOIN ADMINISTRATION A ON S.STUDENT_ID = A.STUDENT_ID;
