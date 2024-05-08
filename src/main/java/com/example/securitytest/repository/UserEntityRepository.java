package com.example.securitytest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securitytest.entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
	boolean existsByUserName(String userName);

	Optional<UserEntity> findByUserName(String username);
}
