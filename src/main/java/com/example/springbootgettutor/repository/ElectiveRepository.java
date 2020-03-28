package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Elective;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElectiveRepository extends BaseRepository<Elective,Integer>{
    @Query("FROM Elective ele")
    Optional<List<Elective>> list();

}
