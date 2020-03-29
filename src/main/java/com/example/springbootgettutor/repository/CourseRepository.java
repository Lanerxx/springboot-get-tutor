package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends BaseRepository<Course, Integer> {
    @Query("FROM Course c")
    Optional<List<Course>> list();

    @Query("SELECT c FROM Course c WHERE c.tutor.id=:id")
    Optional<List<Course>> getCourseByTutor(@Param("id")int id);

    @Modifying
    @Query("UPDATE Course c SET c.lowestMark=:lowestMark WHERE c.id=:id")
    int updateLowestMark(@Param("lowestMark") float lowestMark,@Param("id")int id);

    @Modifying
    @Query("UPDATE Course c SET c.intentionWeight=:intentionWeight WHERE c.id=:id")
    int updateIntentionWeight(@Param("intentionWeight")float intentionWeight,@Param("id")int id);

    Optional<List<Course>> findByName(String name);
    void deleteByName(String name);


}
