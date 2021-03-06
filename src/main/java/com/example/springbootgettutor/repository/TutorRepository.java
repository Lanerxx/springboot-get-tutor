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
    @Query("UPDATE Tutor t SET t.quantity=:quantity WHERE t.id=:id")
    int updateQuantity(@Param("quantity")int quantity,@Param("id") int id);

    @Modifying
    @Query("UPDATE Tutor t SET t.ranges=:ranges WHERE t.id=:id")
    int updateRanges(@Param("ranges")int ranges,@Param("id") int id);

    Optional<Tutor> findById(int id);

    void deleteById(int id);
}
