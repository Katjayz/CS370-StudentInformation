package com.example.StudentInformation.modals;

public class Student {
    
    private String email;
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String password;
    private Double gpa;
    private int credits;
    private double balance;

    public Student() {
        
    }

    
    public Student(String email, int id, String firstName, String lastName, String address, String phone) {
        this.email = email;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

     public Student(String email, int id, String firstName, String lastName, String address, String phone, String password, Double gpa, int credits, double balance) {
        this.email = email;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.gpa = gpa;
        this.credits = credits;
        this.balance = balance;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

     public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

     public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

     public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

     public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

     public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getGpa() {
        return this.gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getCredits(){
        return this.credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
