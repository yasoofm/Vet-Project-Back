package com.coded.VetApp.repositories;

import com.coded.VetApp.entities.FavEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavRepo extends JpaRepository<FavEntity, Long> {

}
