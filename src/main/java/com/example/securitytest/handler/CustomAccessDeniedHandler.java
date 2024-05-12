package com.example.securitytest.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
    	
    	log.error("403 error 발생");
    	
    	response.setContentType("application/json");
    	response.setStatus(HttpStatus.FORBIDDEN.value());
    	response.getWriter().write("접근 권한이 없습니다.");
    	response.getWriter().flush();
    	
    }
}

