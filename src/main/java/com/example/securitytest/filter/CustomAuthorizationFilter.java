package com.example.securitytest.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


// [테스트] 시큐리티 필터 적용 전 SecurityContextHolder에 객체가 잘 적용되어있는지 확인하기 위한 필터
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
	
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
        Authentication details = SecurityContextHolder.getContext().getAuthentication();

        if(details != null) {
        	log.info("details NOT NULL");
        } else {
        	log.info("details NULL");
        }
        
        chain.doFilter(request, response);
    }
    
}
