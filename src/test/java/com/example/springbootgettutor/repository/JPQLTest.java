package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Course;
import com.example.springbootgettutor.entity.Elective;
import com.example.springbootgettutor.entity.Student;
import com.example.springbootgettutor.entity.Tutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class JPQLTest {
    @Autowired
    private CourseRepository  courseRepository;
    @Autowired
    private ElectiveRepository electiveRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TutorRepository tutorRepository;

    @Test
    @Transactional
    @Rollback(value = false)

    public void init(){
        //创建一个导师
        Tutor tutor = new Tutor();
        tutor.setName("BO");
        tutor.setSumCount(2);
        tutorRepository.save(tutor);

        //创建两个学生
        Student student1 = new Student();
        student1.setId(2017224492);
        student1.setName("Laner");
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setId(2017224493);
        student2.setName("jojo");
        studentRepository.save(student2);

        //创建两个课程
        Course course1 = new Course();
        course1.setName("web系统框架");
        course1.setTutor(tutor);
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setName("java");
        course2.setTutor(tutor);
        courseRepository.save(course2);


        //创建三条选课记录
        Elective elective1 = new Elective();
        elective1.setStudent(student1);
        elective1.setCourse(course1);
        electiveRepository.save(elective1);

        Elective elective2 = new Elective();
        elective2.setStudent(student1);
        elective2.setCourse(course2);
        electiveRepository.save(elective2);

        Elective elective3 = new Elective();
        elective3.setStudent(student2);
        elective3.setCourse(course1);
        electiveRepository.save(elective3);

    }
}
