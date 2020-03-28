package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.DirctionElective;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DirectionElectiveRepository extends BaseRepository<DirctionElective , Integer> {
    @Query("FROM DirctionElective de")
    List<DirctionElective> list();

}
