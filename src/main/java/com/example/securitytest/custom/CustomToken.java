package com.example.securitytest.custom;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

// 현재 실질적으로 필요는 없다.
public class CustomToken extends UsernamePasswordAuthenticationToken {
	
	private final LocalDateTime startTime;

	public CustomToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		// 인증 성공시 시간을 저장한다.
		this.startTime = LocalDateTime.now();
	}
	
	
	public CustomToken(Object principal, Object credentials) {
		super(principal, credentials);
		this.startTime = null;
	}


	public LocalDateTime getStartTime() {
		return this.startTime;
	}

}
