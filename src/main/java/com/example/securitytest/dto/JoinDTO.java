package com.example.securitytest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinDTO {

	private String userName;
	private String passWord;
	private String accessUrl;
	
	@Builder
	public JoinDTO(String userName, String passWord, String accessUrl) {
		this.userName = userName;
		this.passWord = passWord;
		this.accessUrl = accessUrl;
	}
	
}
