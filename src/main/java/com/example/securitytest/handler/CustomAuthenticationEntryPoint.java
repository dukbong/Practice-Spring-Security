package com.example.securitytest.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
    	
    	request.getSession().invalidate(); // 세션 파기
    	
    	log.error("401 error 발생");
    	
    	response.setContentType("applicatoin/json");
    	response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
    	response.getWriter().write("인증 되지 않은 상태에서 접근 하였습니다.");
    	response.getWriter().flush();
    	
    }
}
