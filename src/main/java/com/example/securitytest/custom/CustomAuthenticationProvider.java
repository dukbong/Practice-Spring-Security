package com.example.securitytest.custom;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.securitytest.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private final CustomUserDetailsService customAddUrlUserDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		Map<String, Object> detail = (Map<String, Object>) authentication.getDetails(); 

		UserDetails userDetails = customAddUrlUserDetailsService.loadUserByUsernameAndUrl(username, (String)detail.get("url"));
        
		if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("유효하지 않은 비밀번호 입니다!!");
		}
        
		// Url 로직 추가 예정
        
		// return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
		return new CustomToken(username, password, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
		return CustomToken.class.isAssignableFrom(authentication);
	}

}
