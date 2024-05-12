package com.example.securitytest.monitoring;

import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

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
        log.info("SessionRegistry에 저장하는 것을 LoginProcess 메소드에서 진행할 것이다.");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    	log.info("세션 이벤트 모니터링 - 파기 : {}", se.getSession().getId());
        sessionRegistry.removeSessionInformation(se.getSession().getId());
    }
    
}
