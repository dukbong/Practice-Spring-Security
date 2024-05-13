package com.example.securitytest.handler;

import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomSessionExpiredStrategy implements SessionInformationExpiredStrategy {

	private MessageSource messageSource;
	
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
//        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();
        
        // 세션 만료 메시지를 응답으로 전송
        response.setContentType("applicatoin/json");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(messageSource.getMessage("expire.session.message", null, LocaleContextHolder.getLocale()));
        response.getWriter().flush();
    }
}
