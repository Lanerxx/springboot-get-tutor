package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.Tutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends BaseRepository<Tutor, Integer> {
    @Query("FROM Tutor t")
    Optional<List<Tutor>> list();

    @Modifying
    @Query("UPDATE Tutor t SET t.sumCount=:sumCount WHERE t.id=:id")
    int updateSumCount(@Param("sumCount")int sumCount,@Param("id") int id);

    @Modifying
    @Query("UPDATE Tutor t SET t.remainCount=:remainCount WHERE t.id=:id")
    int updateRemainCount(@Param("remainCount")int remainCount,@Param("id") int id);

    @Modifying
    @Query("UPDATE Tutor t SET t.password=:password WHERE t.id=:id")
    int updatePassword(@Param("password")String password,@Param("id") int id);


    Optional<Tutor> findById(int id);
    Optional<Tutor> findByName(String name);
    void deleteByName(String name);
}
