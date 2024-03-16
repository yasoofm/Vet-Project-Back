package com.coded.VetApp.repositories;

import com.coded.VetApp.entities.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepo extends JpaRepository<StatusEntity, Long> {
    Optional<StatusEntity> findByVetId(Long vetId);
}
