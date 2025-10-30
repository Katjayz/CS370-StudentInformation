package com.example.StudentInformation.controller;

import org.springframework.web.bind.annotation.*;

//controllers are used to pass data to the front end. When the database is up and running, controllers 
// will connect to dao files which are used to connect the back end to the database and map the data to objects.
@CrossOrigin(origins = "http://localhost:4200") 
@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String getHello() {
        return "Hello?"; //this is the string that is being displayed on the bottom line.
    }
}
