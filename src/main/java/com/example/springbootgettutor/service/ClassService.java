package com.example.springbootgettutor.service;

import com.example.springbootgettutor.entity.Course;
import com.example.springbootgettutor.entity.Direction;
import com.example.springbootgettutor.entity.Elective;
import com.example.springbootgettutor.entity.Tutor;
import com.example.springbootgettutor.repository.CourseRepository;
import com.example.springbootgettutor.repository.DirectionRepository;
import com.example.springbootgettutor.repository.ElectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ElectiveRepository electiveRepository;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private UserService userService;

    //---------"Course's CURD "-----------
    public Course addCourse(Course course){
        courseRepository.save(course);
        return course;
    }
    public Course addCourseWithTutor(Course course,int id){
        Tutor tutor = userService.getTutorById(id);
        course.setTutor(tutor);
        courseRepository.save(course);
        return course;
    }


    public void deletCourse(int id){
        courseRepository.deleteById(id);
    }

    public Course updateCourse(Course course){
        courseRepository.save(course);
        return course;
    }

    public Course getCourse(int id){
        return courseRepository.findById(id).orElse(new Course());
    }
    public Course getCourse(String name){
        return courseRepository.findByName(name).orElse(new Course());
    }
    public List<Course> listCourses(){
        return courseRepository.findAll();
    }
    public List<Course> listCourseByTutorID(int id){
        return courseRepository.getCourseByTutor(id).orElse(List.of());
    }


    //---------"Elective's CURD "-----------

    public Elective addElective(Elective elective) {
        electiveRepository.save(elective);
        return elective;
    }

    public void deleteElective(int id) {
        electiveRepository.deleteById(id);
    }

    public Elective getElective(int id) {
        return electiveRepository.findById(id)
                .orElse(null);
    }

    public Elective updateElective(Elective elective) {
        electiveRepository.save(elective);
        return elective;
    }


    //---------"Direction's CURD "-----------
    public Direction addDirection(Direction direction) {
        directionRepository.save(direction);
        return direction;
    }

    public Direction updateDirection(Direction direction) {
        directionRepository.save(direction);
        return direction;
    }

    public void deleteDirection(int did) {
        directionRepository.deleteById(did);
    }

    public List<Direction> listDirections() {
        return directionRepository.findAll();
    }




}
