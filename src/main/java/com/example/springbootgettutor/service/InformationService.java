package com.example.springbootgettutor.service;

import com.example.springbootgettutor.entity.Course;
import com.example.springbootgettutor.entity.Direction;
import com.example.springbootgettutor.entity.Student;
import com.example.springbootgettutor.entity.Tutor;
import com.example.springbootgettutor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class InformationService {
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private ElectiveRepository electiveRepository;
    @Autowired
    private DirectionElectiveRepository directionElectiveRepository;
    @Autowired
    private InformationService informationService;


    //Tutor
    public Tutor addTutor(Tutor tutor){
        return tutorRepository.save(tutor);
    }

    public Tutor getTutor(String name){
        return tutorRepository.findByName(name).orElse(null);
    }

    public Tutor getTutor(int id){
        return tutorRepository.findById(id).orElse(null);
    }

    public List<Tutor> getAllTutors(){
        return tutorRepository.list().orElse(null);
    }

    public Tutor updateTutorPassword(int id,String password){
        tutorRepository.updatePassword(password, id);
        return informationService.getTutor(id);
    }

    public void deleteTutor(String name){
        tutorRepository.deleteByName(name);
    }

    public void deleteTutor(int id){
        tutorRepository.deleteById(id);
    }

    public void deleteAllTutors(){
        tutorRepository.deleteAll();
    }

    //Student
    public Student addStudent(Student student){
        return studentRepository.save(student);
    }

    public List<Student> getStudentByName(String name){
        return studentRepository.findByName(name).orElse(null);
    }

    public Student getStudentByStudentNember(String studentNumber){
        return studentRepository.findByStudentNumber(studentNumber).orElse(null);
    }

    public Student getStudentById(int id){
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getAllStudents(){
        return studentRepository.list().orElse(null);
    }

    public Student updateStudentPassword(int id, String password){
        studentRepository.updatePassword(password, id);
        return informationService.getStudentById(id);
    }

    public Student updateTutor(int id, Tutor tutor){
        studentRepository.updateTutor(tutor, id);
        return informationService.getStudentById(id);
    }

    public void deleteStudentByName(String name){
        studentRepository.deleteByName(name);
    }

    public void deleteStudentByStudentNumber(String studentNumber){
        studentRepository.findByStudentNumber(studentNumber);
    }

    public void deleteStudentById(int id){
        studentRepository.deleteById(id);
    }

    public void deleteAllStudents(){
        studentRepository.deleteAll();
    }

    //Course
    public Course addCourse(Course course){
        return courseRepository.save(course);
    }

    public List<Course> getCourseByName(String name){
        return courseRepository.findByName(name).orElse(null);
    }

    public Course getCourseById(int id){
        return courseRepository.findById(id).orElse(null);
    }

    public List<Course> getAllCourses(){
        return courseRepository.list().orElse(null);
    }

    public Course updateCourseLowestMark(int id,float lowestMark){
        courseRepository.updateLowestMark(lowestMark,id);
        return informationService.getCourseById(id);
    }

    public Course updateCourseIntentionWeight(int id, float intentionWeight){
        courseRepository.updateIntentionWeight(intentionWeight, id);
        return informationService.getCourseById(id);
    }

    public void deleteCourseByName(String name){
        courseRepository.deleteByName(name);
    }

    public void deleteAllCourses(){
        courseRepository.deleteAll();
    }


    //Direction
    public Direction addDirection(Direction direction){
        return directionRepository.save(direction);
    }

    public Direction getDirection(String name){
        return directionRepository.findByName(name).orElse(null);
    }

    public Direction getDirection(int id){
        return directionRepository.findById(id).orElse(null);
    }

    public List<Direction> getAllDirections(){
        return directionRepository.list()
                .orElse(null);
    }

    public void deleteDirectionByName(String name){
        directionRepository.deleteDirectionByName(name);
    }

    public void deleteAllDirection(){
        directionRepository.deleteAll();
    }

}
