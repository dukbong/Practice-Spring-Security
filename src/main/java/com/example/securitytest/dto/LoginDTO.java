package com.example.securitytest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginDTO {
	
	private String userName;
	private String passWord;
	
	@Builder
	public LoginDTO(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}
	
}