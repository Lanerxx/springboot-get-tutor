package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.DirctionElective;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DirectionElectiveRepository extends BaseRepository<DirctionElective , Integer> {
    @Query("FROM DirctionElective de")
    Optional<List<DirctionElective>> list();

}
