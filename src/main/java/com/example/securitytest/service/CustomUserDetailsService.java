package com.example.securitytest.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// 여기서 다양한 검증 방법을 만들어서 쓸 수 있다.
public interface CustomUserDetailsService extends UserDetailsService {
	UserDetails loadUserByUsernameAndUrl(String username, Map<String, String> info, LocalDateTime StartTime) throws UsernameNotFoundException;
}
