package com.example.securitytest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginProcessDTO {

	private String userName;
	private String passWord;
	private String accessUrl;
	private String role;
	
	@Builder
	public LoginProcessDTO(String userName, String passWord, String role, String accessUrl) {
		this.userName = userName;
		this.passWord = passWord;
		this.role = role;
		this.accessUrl = accessUrl;
	}
	
}
