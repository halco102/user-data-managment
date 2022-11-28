package com.user.data.management.repository;

import com.user.data.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "Select * from users where username = :username", nativeQuery = true)
    Optional<User> getUserByUsername(@Param("username") String username);

    @Query(value = "Select * from users where email = :email", nativeQuery = true)
    Optional<User> getUserByEmail(@Param("email") String email);

}
