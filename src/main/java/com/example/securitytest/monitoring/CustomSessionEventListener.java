package com.example.securitytest.monitoring;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// HttpSessionEventPublisher에 의한 이벤트 
@Component
@Slf4j
@RequiredArgsConstructor
public class CustomSessionEventListener implements HttpSessionListener  {
	
	 private final SessionRegistry sessionRegistry;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("세션 이벤트 모니터링 - 생성 : {}", se.getSession().getId());
        
        if(SecurityContextHolder.getContext().getAuthentication() == null) {
        	return;
        }
        
       	sessionRegistry.registerNewSession(se.getSession().getId(), SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    	log.info("세션 이벤트 모니터링 - 파기 : {}", se.getSession().getId());
    	
        sessionRegistry.removeSessionInformation(se.getSession().getId());
    }
    
}
