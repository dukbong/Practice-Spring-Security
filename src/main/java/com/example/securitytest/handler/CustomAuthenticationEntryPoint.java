package com.example.securitytest.handler;

import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private final MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
    	
    	request.getSession().invalidate(); // 세션 파기
    	
    	log.error("401 error 발생");
    	
    	response.setContentType("applicatoin/json");
    	response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
    	response.getWriter().write(messageSource.getMessage("authentication.entry.point.message", null, LocaleContextHolder.getLocale()));
    	response.getWriter().flush();
    	
    }
}
