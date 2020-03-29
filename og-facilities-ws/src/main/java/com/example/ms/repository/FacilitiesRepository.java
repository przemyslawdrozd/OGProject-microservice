package com.example.ms.repository;

import com.example.ms.model.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacilitiesRepository extends JpaRepository<BuildingEntity, Long> {

    List<BuildingEntity> findAllByUserId(String userId);

    Optional<BuildingEntity> findByUserIdAndName(String userId, String name);
}
