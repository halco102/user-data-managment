package com.user.data.management.repository;

import com.user.data.management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM roles where name = :name", nativeQuery = true)
    Optional<Role> findRoleByName(@Param("name") String name);

}
