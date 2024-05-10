package com.example.securitytest.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginProcessDTO {

	private String userName;
	private String passWord;
	private String role;
	private Map<String, String> info = new HashMap<>();
	
	@Builder
	public LoginProcessDTO(String userName, String passWord, String role, Map<String, String> info) {
		this.userName = userName;
		this.passWord = passWord;
		this.role = role;
		this.info = info;
	}
	
}
