package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer> {
    @Query("SELECT stu FROM Student stu")
    List<Student> list();

    List<Student> findByName(String name);

    Student findByStudentNumber(String studentNumber);

}
