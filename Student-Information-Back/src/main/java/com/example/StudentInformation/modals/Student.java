package com.example.StudentInformation.modals;

public class Student {
    
    private String email;
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    
    public Student(String email, int id, String firstName, String lastName, String address, String phone) {
        this.email = email;
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
}
