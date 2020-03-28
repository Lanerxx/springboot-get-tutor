package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Elective;
import org.springframework.data.jpa.repository.Modifying;
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
    Optional<Elective> getElective(@Param("studentId")int studentId,@Param("courseId")int courseId);

    @Query("SELECT ele FROM Elective ele WHERE ele.student.name=:name")
    Optional<List<Elective>> getElectivesByStudentName(@Param("name")String name);

    @Query("SELECT ele FROM Elective ele WHERE ele.student.studentNumber=:studentNumber")
    Optional<List<Elective>> getElectivesByStudentNumber(@Param("name")String name);

    @Query("SELECT ele FROM Elective ele WHERE ele.student.id=:id")
    Optional<List<Elective>> getElectivesStudentId(@Param("id")int id);

    @Query("SELECT ele FROM Elective ele WHERE ele.course.name=:name")
    Optional<List<Elective>> getElectivesByCourseName(@Param("name")String name);

    @Query("SELECT ele FROM Elective ele WHERE ele.course.id=:id")
    Optional<List<Elective>> getElectivesCourseId(@Param("id")int id);

    @Modifying
    @Query("UPDATE Elective e SET e.grade=:grade WHERE e.student.id=:studentId AND e.course.id=:courseId")
    int updateGrade(@Param("grade")float grade,@Param("studentId")int studentId,@Param("courseId")int courseId);


}
