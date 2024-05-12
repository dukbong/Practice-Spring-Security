package com.example.securitytest.monitoring;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

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
