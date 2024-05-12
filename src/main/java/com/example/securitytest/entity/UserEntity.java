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

	// EmbeddedId, Embeddable를 추후에 사용해서 userName과 accessUrl 두개를 복합키로 만들어서 사용할 예정 
	@Id
	@GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(unique = true)
	private String userName;
	
	private String passWord;
	
	private String accessUrl;
	
	private String role;

	@Builder
	public UserEntity(Long id, String userName, String passWord, String role, String accessUrl) {
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.role = role;
		this.accessUrl = accessUrl;
	}
	
	public void updateUserName(String userName) {
		this.userName = userName;
	}
	
	public void updateUserPassWord(String passWord) {
		this.passWord = passWord;
	}
}
