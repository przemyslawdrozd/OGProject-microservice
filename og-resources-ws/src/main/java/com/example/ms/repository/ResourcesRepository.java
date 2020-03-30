package com.example.ms.repository;

import com.example.ms.model.ResourcesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ResourcesRepository extends JpaRepository<ResourcesEntity, Long> {

    Optional<ResourcesEntity> findByUserId(String userId);

    @Transactional @Modifying
    @Query("UPDATE ResourcesEntity r SET r.metal = :metal, r.crystal = :crystal, r.deuterium = :deuterium WHERE r.userId = :userId")
    void updateResourcesByUserId(@Param("metal") int metal,
                                 @Param("crystal") int crystal,
                                 @Param("deuterium") int deuterium,
                                 @Param("userId") String userId);
}
