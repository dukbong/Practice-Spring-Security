package com.example.securitytest.custom;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomToken extends UsernamePasswordAuthenticationToken {
	
	private final LocalDateTime startTime;

	public CustomToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.startTime = LocalDateTime.now();
		log.info("Login time : " + startTime);
	}
	
	
	public CustomToken(Object principal, Object credentials) {
		super(principal, credentials);
		this.startTime = null;
	}


	public LocalDateTime getStartTime() {
		return this.startTime;
	}

}
