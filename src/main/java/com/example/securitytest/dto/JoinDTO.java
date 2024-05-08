package com.example.securitytest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinDTO {

	private String userName;
	private String passWord;
	
	@Builder
	public JoinDTO(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}
	
}
