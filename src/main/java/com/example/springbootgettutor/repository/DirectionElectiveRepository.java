package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.DirectionElective;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DirectionElectiveRepository extends BaseRepository<DirectionElective, Integer> {
    @Query("FROM DirectionElective de")
    Optional<List<DirectionElective>> list();

    @Query("SELECT de FROM DirectionElective de WHERE de.student.name=:name")
    Optional<List<DirectionElective>> getDirectionElectivesByStudentName(@Param("name")String name);

    @Query("SELECT de FROM DirectionElective de WHERE de.student.studentNumber=:studentNumber")
    Optional<List<DirectionElective>> getDirectionElectivesByStudentNumber(@Param("studentNumber")String studentNumber);

    @Query("SELECT de FROM DirectionElective de WHERE de.student.id=:id")
    Optional<List<DirectionElective>> getDirectionElectivesStudentId(@Param("id")int id);

    @Query("SELECT de FROM DirectionElective de WHERE de.direction.name=:name")
    Optional<List<DirectionElective>> getDirectionElectivesByDirection(@Param("name")String name);

    @Query("SELECT de FROM DirectionElective de WHERE de.direction.id=:id")
    Optional<List<DirectionElective>> getDirectionElectivesByDirection(@Param("id")int id);

}
