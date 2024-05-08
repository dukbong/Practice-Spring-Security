package com.example.securitytest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "user_gen", sequenceName = "user_seq", initialValue = 1, allocationSize = 50)
public class UserEntity {

	@Id
	@GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(unique = true)
	private String userName;
	
	private String passWord;
	
	private String role;

	@Builder
	public UserEntity(Long id, String userName, String passWord, String role) {
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.role = role;
	}
	
	public void updateUserName(String userName) {
		this.userName = userName;
	}
	
	public void updateUserPassWord(String passWord) {
		this.passWord = passWord;
	}
}
