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
    @PatchMapping("course")
    public Map updateCourse(@Valid @RequestBody Course course) {
        Tutor tutor = userService.getTutorById(requestComponent.getUid());
        Course c = classService.getCourse(course.getId());
        if(c == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The course you want to change doesn't exist");
        }
        c.setWeight(course.getWeight());
        c.setLowestMark(course.getLowestMark());
        c.setName(course.getName());
        classService.updateCourse(c);
        return Map.of(
                "course",c
        );
    }

    //Course Information Set to Default
    @PatchMapping("courses")
    public Map updateCourse() {
        Tutor tutor = userService.getTutorById(requestComponent.getUid());
        List<Course> courses = classService.listCourseByTutorID(tutor.getId());
        courses.forEach(c->{
            Course course = classService.getCourse(c.getId());
            course.setWeight(0.5);
            course.setLowestMark(60);
            classService.updateCourse(course);
        });
        courses = classService.listCourseByTutorID(tutor.getId());
        return Map.of(
                "courses",courses
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
            Student s = userService.getStudentByUserNumber(elective.getStudent().getUser().getNumber());
            Course c = classService.getCourse(course.getName(), tutor.getId());
            if(student == null || course == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Students and classes cannot be empty");
            }
            if(s == null){
                User u = new User();
                u.setName(student.getUser().getName());
                u.setNumber(student.getUser().getNumber());
                u.setRole(User.Role.STUDENT);
                u.setPassword(encoder.encode(String.valueOf(student.getUser().getNumber())));
                s = new Student();
                s.setUser(u);
                userService.addStudent(s);
            }
            if(c == null){
                c = new Course();
                c.setName(course.getName());
                c.setTutor(tutor);
                classService.addCourse(c);
            }
            Elective e = new Elective();
            e.setGrade(elective.getGrade());
            e.setStudent(userService.getStudent(s.getId()));
            e.setCourse(classService.getCourse(course.getName(), tutor.getId()));
            classService.addElective(e);
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
        Direction d =  classService.getDirection(direction.getId());
        if(d==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The direction you want to change does not exist");

        }
        d.setName(direction.getName());
        classService.updateDirection(d);
        return Map.of(
                "direction",d
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

    //Student Ranking
    @GetMapping("ranking")
    public Map getStudents(){
        List<Student> students = classService.RankStudents(requestComponent.getUid());
        return Map.of(
                "student",students
        );
    }

    //Elected in advance
    @PostMapping("advance")
    public Map electStudentAdvance(@Valid @RequestBody Student student){
        Tutor tutor = userService.getTutorById(requestComponent.getUid());
        int quan = userService.getStudentsByTutorId(requestComponent.getUid()).size();
        int ran = tutor.getRanges();
        if(quan >= ran){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The number of students instructed has reached the upper limit！");
        }
        if(userService.getUserByNumber(student.getUser().getNumber())==null){
            User u = new User();
            u.setName(student.getUser().getName());
            u.setNumber(student.getUser().getNumber());
            u.setRole(User.Role.STUDENT);
            u.setPassword(encoder.encode(String.valueOf(student.getUser().getNumber())));
            Student s = new Student();
            s.setUser(u);
            s.setTutor(tutor);
            userService.addStudent(s);
        }
        else {
            Student student1 = userService.getStudentByUserNumber(student.getUser().getNumber());
            student1.setTutor(tutor);
            userService.updateStudent(student1);
        }
        tutor.setQuantity(quan+1);
        userService.updateTutor(tutor);
        return Map.of(
                "student",student
        );
    }


}
