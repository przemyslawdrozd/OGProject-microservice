package com.example.ms.repository;

import com.example.ms.model.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacilitiesRepository extends JpaRepository<BuildingEntity, Long> {

    List<BuildingEntity> findAllByUserId(String userId);

    Optional<BuildingEntity> findByUserIdAndName(String userId, String name);

    @Transactional @Modifying
    @Query("UPDATE BuildingEntity b SET b.buildState = 'FREEZE' WHERE b.buildState = 'READY' AND b.userId = :userId")
    void updateBuildStateAsFreezeByUserId(@Param("userId") String userId);

    List<BuildingEntity> findAllByBuildState(String buildState);
}
