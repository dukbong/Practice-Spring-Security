//package com.example.securitytest.monitoring;
//
//import org.springframework.context.ApplicationListener;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.security.web.session.HttpSessionDestroyedEvent;
//import org.springframework.stereotype.Component;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class CustomSessionDestroy implements ApplicationListener<HttpSessionDestroyedEvent> {
//
//	private final SessionRegistry sessionRegistry;
//	
//	@Override
//	public void onApplicationEvent(HttpSessionDestroyedEvent event) {
//		
//		log.info("세션 파기 이벤트 : {}", event.getSession().getId());
//		
//		sessionRegistry.removeSessionInformation(event.getSession().getId());
//	}
//
//}
