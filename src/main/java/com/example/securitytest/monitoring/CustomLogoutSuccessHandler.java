package com.example.securitytest.monitoring;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.example.securitytest.dto.CustomUserDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		log.info("로그아웃 성공");
		CustomUserDetails detail = (CustomUserDetails)authentication.getPrincipal();
		List<String> authority = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		log.info("logout user name = {}", detail.getUsername());
		log.info("logout user authority = {}", authority);
		log.info("logout user url = {}", detail.getInfo().get("url"));
		log.info("logout user test = {}", detail.getInfo().get("test"));
		log.info("logout time = {}", LocalDateTime.now());
	}

}
