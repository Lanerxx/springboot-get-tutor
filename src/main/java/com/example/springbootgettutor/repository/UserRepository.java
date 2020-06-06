package com.example.springbootgettutor.repository;

import com.example.springbootgettutor.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Integer>{
    Optional<User> findByNumber(int number);
    Optional<User> findByName(String name);

    @Modifying
    @Query("UPDATE User u SET u.password=:password WHERE u.id=:id")
    int updatePassword(@Param("password") String password, @Param("id") int id);
}
