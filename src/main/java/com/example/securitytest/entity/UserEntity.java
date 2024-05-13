package com.example.securitytest.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

	
	@EmbeddedId
	UserEntityId id;
	
	private String passWord;
	
	private String role;

	@Builder
	public UserEntity(UserEntityId id, String passWord, String role) {
		this.id = id;
		this.passWord = passWord;
		this.role = role;
	}
	
	public void updateUserName(String userName) {
		this.id.updateUseName(userName);
	}
	
	public void updateUserPassWord(String passWord) {
		this.passWord = passWord;
	}
}
