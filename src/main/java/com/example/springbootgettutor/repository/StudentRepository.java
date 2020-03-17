package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer> {
}
