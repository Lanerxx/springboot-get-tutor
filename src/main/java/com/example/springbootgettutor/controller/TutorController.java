package com.example.springbootgettutor.controller;

import com.example.springbootgettutor.component.EncryptComponent;
import com.example.springbootgettutor.component.RequestComponent;
import com.example.springbootgettutor.entity.*;
import com.example.springbootgettutor.service.ClassService;
import com.example.springbootgettutor.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/tutor/")
public class TutorController {
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;
    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private PasswordEncoder encoder;

    /**
     * @describtion: Tutor level 1 page -> welcome
     * 1. Display welcome tutor information
     * 2. Display all the selected students
     * @param:
     * @return:
     */
    @GetMapping("index")
    public Map getIndex(){
        Tutor tutor = userService.getTutorById(requestComponent.getUid());
        List<Student> students = userService.getStudentsByTutorId(requestComponent.getUid());
        return Map.of(
                "tutor",tutor,
                "students",students
        );
    }

    //Course Information
    @PostMapping("courses")
    public Map addCourse(@Valid @RequestBody Course course) {
        Tutor tutor = userService.getTutorById(requestComponent.getUid());
        List<Course> courses = classService.listCourseByTutorID(requestComponent.getUid());
        courses.forEach(c -> {
            if(c.getName().equals(course.getName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "You have already added the course and cannot repeat it!");

            }
        });
        course.setTutor(tutor);
        classService.addCourse(course);
        return Map.of(
                "course",course
        );
    }

    @DeleteMapping("courses/{cid}")
    public Map deleteCourses(@PathVariable int cid) {
        classService.deleteCourse(cid);
        return Map.of(
                "massage","successful delete!"
        );
    }

    //Course weight Information
    @PatchMapping("courses")
    public Map updateCourse(@Valid @RequestBody Course course) {
        Tutor tutor = userService.getTutorById(requestComponent.getUid());
        Course OldCourse = classService.getCourse(course.getId());
        if(OldCourse == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The course you want to change doesn't exist");
        }
        classService.updateCourse(course);
        return Map.of(
                "course",course
        );
    }

    @GetMapping("courses")
    public Map getCourses(){
        List<Course> courses = classService.listCourseByTutorID(requestComponent.getUid());
        return Map.of(
                "courses",courses
        );
    }


    //Electives Information
    @PostMapping("electives")
    public Map addElectives(@Valid @RequestBody List<Elective> electives){
        Tutor tutor = userService.getTutorById(requestComponent.getUid());
        electives.forEach(elective -> {
            Student student = elective.getStudent();
            Course course = elective.getCourse();
            if(student == null || course == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Students and classes cannot be empty");
            }
            if(userService.getStudent(student.getId())==null){
                User u = new User();
                u.setNumber(student.getUser().getNumber());
                u.setRole(User.Role.STUDENT);
                u.setPassword(encoder.encode(String.valueOf(student.getUser().getNumber())));
                student.setUser(u);
                userService.addStudent(student);
            }
            if(classService.getCourse(course.getId())==null){
                course.setTutor(tutor);
                classService.addCourse(course);
            }
            classService.addElective(elective);
        });
        return Map.of(
                "electives",electives
        );
    }

    //Directions Information
    @PostMapping("direction")
    public Map addDirection(@Valid @RequestBody Direction direction){
        List<Direction> directions = classService.listDirections();
        directions.forEach(d -> {
            if(d.getName() == direction.getName()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "This direction already exists!");

            }
        });
        classService.addDirection(direction);
        return Map.of(
                "direction",direction
        );
    }

    @DeleteMapping("direction")
    public Map deleteDirection(@PathVariable int did){
        classService.deleteDirection(did);
        return Map.of(
                "massage","successful delete!"
        );
    }

    @PatchMapping("direction")
    public Map updateDirection(@Valid @RequestBody Direction direction){
        if(classService.getDirection(direction.getId())==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The direction you want to change does not exist");

        }
        classService.updateDirection(direction);
        return Map.of(
                "direction",direction
        );
    }

    @GetMapping("directions")
    public Map getDirections(){
        List<Direction> directions = classService.listDirections();
        return Map.of(
                "directions",directions
        );
    }

    //Tutor Information
    @PatchMapping("information/ranges/{ranges}/reservedRange/{reservedRange}")
    public Map updateTutor(@PathVariable int ranges, @PathVariable int reservedRange){
        Tutor tutor = userService.getTutorById(requestComponent.getUid());
        tutor.setRanges(ranges);
        tutor.setReservedRange(reservedRange);
        userService.updateTutor(tutor);
        return Map.of(
                "tutor",tutor
        );
    }








}
