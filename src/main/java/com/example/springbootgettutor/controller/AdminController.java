package com.example.springbootgettutor.controller;

import com.example.springbootgettutor.entity.Course;
import com.example.springbootgettutor.entity.Student;
import com.example.springbootgettutor.entity.Tutor;
import com.example.springbootgettutor.entity.User;
import com.example.springbootgettutor.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/")
@Slf4j
public class AdminController {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;

    @PostMapping("tutor")
    public Map addTutor(@Valid @RequestBody User user){
        Tutor tutor = new Tutor();
        if(user.getNumber()!=null && user.getName()!=null){
            User u = new User();
            u.setNumber(user.getNumber());
            u.setName(user.getName());
            u.setPassword(encoder.encode(String.valueOf(user.getNumber())));
            u.setRole(User.Role.TUTOR);
            tutor.setQuantity(0);
            tutor.setRanges(30);
            tutor.setReservedRange(50);
            tutor.setUser(u);
            userService.addTutot(tutor);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Name, number cannot be empty.");
        }
        return Map.of(
                "tutor",tutor
        );
    }

    @DeleteMapping("tutor/{tid}")
    public Map deleteTotur(@PathVariable int tid){
        if(userService.getTutorById(tid)==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The tutor you want to delete does not exist.");
        }
        userService.deletTutor(tid);
        return Map.of("massage", "Successful delete!");
    }

    @PostMapping("student")
    public Map addStudent(@Valid @RequestBody User user){
        Student student = new Student();
        if(user.getNumber() != null && user.getName() != null){
            User u = new User();
            u.setNumber(user.getNumber());
            u.setName(user.getName());
            u.setPassword(encoder.encode(String.valueOf(user.getNumber())));
            u.setRole(User.Role.TUTOR);
            student.setUser(u);
            userService.addStudent(student);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Name, number cannot be empty.");
        }
        return Map.of(
                "student",student
        );
    }
}
