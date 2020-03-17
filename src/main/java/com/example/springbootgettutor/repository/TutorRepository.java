package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Tutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends BaseRepository<Tutor, Integer> {
}
