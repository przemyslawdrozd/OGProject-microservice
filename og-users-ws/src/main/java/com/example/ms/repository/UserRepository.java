package com.example.ms.repository;

import com.example.ms.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
}
