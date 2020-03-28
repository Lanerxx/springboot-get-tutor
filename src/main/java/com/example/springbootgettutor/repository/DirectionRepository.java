package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Direction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectionRepository extends BaseRepository<Direction,Integer>{
    @Query("FROM Direction di")
    Optional<List<Direction>> list();

    Optional<Direction> findByName(String name);
    void deleteDirectionByName(String name);
}
