package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Tutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorRepository extends BaseRepository<Tutor, Integer> {
    @Query("FROM Tutor tu")
    List<Tutor> list();
}
