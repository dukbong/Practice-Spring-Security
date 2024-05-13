package com.example.securitytest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securitytest.entity.UserEntity;
import com.example.securitytest.entity.UserEntityId;

public interface UserEntityRepository extends JpaRepository<UserEntity, UserEntityId> {
	boolean existsById_UserNameAndId_AccessUrl(String userName, String accessUrl);
	
	boolean existsById_UserName(String userName);

	Optional<UserEntity> findById_UserName(String username);
	
	Optional<UserEntity> findById_UserNameAndId_AccessUrl(String username, String accessUrl);
}
