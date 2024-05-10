package com.example.securitytest.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.securitytest.custom.CustomToken;
import com.example.securitytest.dto.CustomUserDetails;
import com.example.securitytest.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final SessionRegistry sessionRegistry;
	
    @Override
    public Map<String, String> sessionManagement() {
        log.info("sessionManagement");

        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        log.info("활성 세션 수 [세션이 존재하는 사용자 수] = {}", allPrincipals.size());
        
        for (Object principal : allPrincipals) {
            if (principal instanceof CustomUserDetails detail) {
                detail = (CustomUserDetails) principal;
                List<SessionInformation> sessions = sessionRegistry.getAllSessions(detail, false);
                for(SessionInformation info : sessions) {
                	log.info("활성 유저 정보");
                	log.info("session id = {}", info.getSessionId());
                	log.info("user id = {}", detail.getUsername());
                	log.info("user accessUrl = {}", detail.getInfo().get("url"));
                	log.info("user loginTime = {}", detail.getInfo().get("loginTime"));
                }
            }
        }
        
        return null;
    }

}
