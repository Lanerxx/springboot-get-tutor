package com.example.springbootgettutor.service;

import com.example.springbootgettutor.entity.Elective;
import com.example.springbootgettutor.entity.Student;
import com.example.springbootgettutor.entity.Tutor;
import com.example.springbootgettutor.entity.User;
import com.example.springbootgettutor.repository.StudentRepository;
import com.example.springbootgettutor.repository.TutorRepository;
import com.example.springbootgettutor.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;
    @Autowired
    private PasswordEncoder encoder;

    //---------"User's CURD "-----------
    public User updateUserPassword(int id, String password){
        User u = userService.getUserByID(id);
        u.setPassword(encoder.encode(password));
        userRepository.save(u);
        return u;
    }

    public User getUserByID(int id){
        return userRepository.findById(id).orElse(null);
    }
    public User getUserByNumber(int number) {
        return  userRepository.findByNumber(number).orElse(null);
    }
    public User getUserByName(String name){
        return userRepository.findByName(name).orElse(null);
    }



    //---------"Tutor's CURD "-----------
    public Tutor addTutot(Tutor tutor){
        tutorRepository.save(tutor);
        return tutor;
    }

    public void deletTutor(int id){
        tutorRepository.deleteById(id);
    }

    public Tutor updateTutor(Tutor tutor){
        tutorRepository.save(tutor);
        return tutor;
    }

    //Because the User and Tutor share the primary key，you can input an Id from the User as well as an Id from the Tutor
    public Tutor getTutorById(int id){
        return tutorRepository.findById(id).orElse(new Tutor());
    }
    public List<Tutor> listTutors(){
        return tutorRepository.findAll();
    }

    //---------"Student's CURD "-----------
    public Student addStudent(Student student){
        studentRepository.save(student);
        return student;
    }

    public void deleteStudent(int id){
        studentRepository.deleteById(id);
    }

    public Student updateStudent(Student student){
        studentRepository.deleteById(student.getId());
        studentRepository.save(student);
        return student;
    }

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }
    //Because the User and Student share the primary key，you can input an Id from the User as well as an Id from the Student
    public Student getStudent(int id) {
        return studentRepository.findById(id).orElse(new Student());
    }
    public List<Student> getStudentsByTutorId(int tid){
        return studentRepository.getStudentsByTutorId(tid).orElse(List.of());
    }

}
