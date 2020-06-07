package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Student;
import com.example.springbootgettutor.entity.Tutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer> {
    @Query("SELECT stu FROM Student stu")
    Optional<List<Student>> list();

    Optional<Student> findById (int  id);

    @Query("SELECT s FROM Student  s WHERE s.tutor.id=:id")
    Optional<List<Student>> getStudentsByTutorId (@Param("id")int id);

    @Query("SELECT s FROM Student  s WHERE s.user.number=:number")
    Optional<Student> getStudentsByUserNumber (@Param("number")int number);

    @Modifying
    @Query("UPDATE Student s SET s.tutor=:tutor WHERE s.id=:id")
    int updateTutor(@Param("tutor") Tutor tutor, @Param("id") int id);

    @Modifying
    @Query("UPDATE Student s SET s.weightedGrade=:weightedGrade WHERE s.id=:id")
    int updateWeightedGrade(@Param("weightedGrade") double weightedGrade, @Param("id") int id);


    void deleteById(int id);

}
