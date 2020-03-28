package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Elective;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectiveRepository extends BaseRepository<Elective,Integer>{
    @Query("FROM Elective ele")
    List<Elective> list();

}
