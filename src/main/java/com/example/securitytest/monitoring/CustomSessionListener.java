package com.example.securitytest.monitoring;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 고민중....

// HttpSessionEventPublisher에 의한 이벤트 
@Component
@Slf4j
@RequiredArgsConstructor
public class CustomSessionListener implements HttpSessionListener  {
	
	 private final SessionRegistry sessionRegistry;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("세션 이벤트 모니터링 - 생성 : {}", se.getSession().getId());
        
        // 세션이 생기는데 이게 인증된 사용자가 아닐 경우 세션을 지워야 한다.
        // 이유는 각 사용자별로 하나의 세션만 갖을 수 있게 설정했기 때문이다.
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
