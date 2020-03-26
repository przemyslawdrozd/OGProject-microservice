package com.example.ms.repository;

import com.example.ms.model.ResourcesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourcesRepository extends JpaRepository<ResourcesEntity, Long> {

    Optional<ResourcesEntity> findByUserId(String userId);
}
