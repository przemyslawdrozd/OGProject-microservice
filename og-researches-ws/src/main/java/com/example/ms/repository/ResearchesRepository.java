package com.example.ms.repository;

import com.example.ms.model.TechnologyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResearchesRepository extends JpaRepository<TechnologyEntity, Long> {

    List<TechnologyEntity> findAllByUserId(String userId);

    Optional<TechnologyEntity> findByUserIdAndName(String userId, String name);

    List<TechnologyEntity> findAllByBuildState(String buildState);

    @Transactional @Modifying
    @Query("UPDATE TechnologyEntity t SET t.buildState = 'FREEZE' WHERE t.buildState = 'READY' AND t.userId = :userId")
    void updateTechStateAsFreezeByUserId(@Param("userId") String userId);

    @Transactional @Modifying
    @Query("UPDATE TechnologyEntity t SET t.buildState = 'READY' WHERE t.buildState = 'FREEZE' AND t.userId = :userId")
    void updateTechStateAsReadyByUserId(@Param("userId") String userId);
}
