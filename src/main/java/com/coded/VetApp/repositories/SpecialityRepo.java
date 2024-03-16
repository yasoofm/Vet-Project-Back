package com.coded.VetApp.repositories;

import com.coded.VetApp.entities.SpecialityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepo extends JpaRepository<SpecialityEntity, Long> {
}
