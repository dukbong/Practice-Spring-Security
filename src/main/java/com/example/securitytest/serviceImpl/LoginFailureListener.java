package com.example.securitytest.serviceImpl;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

// HttpSessionEventPublisher에 의해 모니터링 된다.
// 로그인 실패시 동작한다. (AuthenticationFailureBadCredentialsEvent 이거 때문이다.)
@Component
@Slf4j
public class LoginFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = event.getAuthentication().getName();
        log.error("로그인 실패 : {}", username);
        
        Throwable failureCause = event.getException();
        log.error("실패 원인 : {}", failureCause.getMessage());
    }
}