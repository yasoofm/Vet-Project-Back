package com.coded.VetApp.repositories;

import com.coded.VetApp.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    @Query(value = "SELECT * FROM Role_Entity r where r.title = :title", nativeQuery = true)
    Optional<RoleEntity> findRoleEntityByTitle(String title);
}
