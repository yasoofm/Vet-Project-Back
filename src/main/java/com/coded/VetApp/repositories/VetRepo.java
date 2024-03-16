package com.coded.VetApp.repositories;

import com.coded.VetApp.entities.VetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VetRepo extends JpaRepository<VetEntity, Long> {
}
