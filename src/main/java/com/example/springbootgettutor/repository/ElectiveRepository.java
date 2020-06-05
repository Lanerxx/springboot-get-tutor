package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Elective;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElectiveRepository extends BaseRepository<Elective,Integer>{
    @Query("FROM Elective ele")
    Optional<List<Elective>> list();

    @Query("SELECT ele FROM Elective ele WHERE ele.student.id=:studentId AND ele.course.id=:courseId")
    Optional<Elective> getElectivesByStudentIdAndCourseId(@Param("studentId")int studentId,@Param("courseId")int courseId);

//    @Query("SELECT ele FROM Elective ele WHERE ele.student.id=:studentId AND ele.course.tutor.id")
//    Optional<List<Elective>> getElectivesByStudentIdAndTutorId(@Param("studentId")int studentId,@Param("TutorId")int TutorId);

    @Query("SELECT ele FROM Elective ele WHERE ele.student.id=:id")
    Optional<List<Elective>> getElectivesStudentId(@Param("id")int id);

    @Query("SELECT ele FROM Elective ele WHERE ele.course.id=:id")
    Optional<List<Elective>> getElectivesCourseId(@Param("id")int id);

}
