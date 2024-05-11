package com.example.securitytest.monitoring;

import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import com.example.securitytest.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// HttpSessionEventPublisher 보다 먼저 발생한다.
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
    
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
    	
    	log.info("로그인 성공시 발생하는 리스너");
    	
    }
}
