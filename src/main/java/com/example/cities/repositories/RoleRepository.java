package com.example.cities.repositories;

import com.example.cities.models.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "SELECT * FROM roles_t WHERE name=?1;", nativeQuery = true)
    Optional<Role> findByName(String name);
}
