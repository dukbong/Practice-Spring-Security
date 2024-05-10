package com.example.securitytest.monitoring;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomSessionListener implements HttpSessionListener  {
	
	 private final SessionRegistry sessionRegistry;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session Created: " + se.getSession().getId());
        log.info("세션 생성 : {}", se.getSession().getId());
        
        // 로그인 성공시 뭘 할것인지 정할 수 있다.
        
        // 아직 모름
        sessionRegistry.registerNewSession(se.getSession().getId(), SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    	log.info("세션 파기 : {}", se.getSession().getId());
    	
        // 로그인 실패시 뭘 할것인지 정할 수 있다.
        
        // 아직 모름
        sessionRegistry.removeSessionInformation(se.getSession().getId());
    }
	
}
