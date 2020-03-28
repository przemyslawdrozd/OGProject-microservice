package com.example.ms.repository;

import com.example.ms.model.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilitiesRepository extends JpaRepository<BuildingEntity, Long> {
}
