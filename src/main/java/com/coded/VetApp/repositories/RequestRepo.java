package com.coded.VetApp.repositories;

import com.coded.VetApp.entities.RequestEntity;
import com.coded.VetApp.entities.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends JpaRepository<RequestEntity, Long> {
}
