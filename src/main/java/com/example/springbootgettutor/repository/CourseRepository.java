package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends BaseRepository<Course, Integer> {
}
