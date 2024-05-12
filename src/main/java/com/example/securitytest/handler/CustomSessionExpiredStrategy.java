package com.example.securitytest.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomSessionExpiredStrategy implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
//        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();
        
        // 세션 만료 메시지를 응답으로 전송
        response.setContentType("applicatoin/json");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write("세션이 만료되었습니다. 재 로그인을 하세요.");
        response.getWriter().flush();
    }
}
