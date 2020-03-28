package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends BaseRepository<Course, Integer> {
    @Query("FROM Course co")
    List<Course> list();


}
