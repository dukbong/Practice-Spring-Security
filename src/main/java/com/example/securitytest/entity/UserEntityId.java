package com.example.securitytest.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserEntityId {

	private String userName;
	private String accessUrl;
	
	@Builder
	public UserEntityId(String userName, String accessUrl) {
		this.userName = userName;
		this.accessUrl = accessUrl;
	}

	public void updateUseName(String userName) {
		this.userName = userName;
	}
	
}
