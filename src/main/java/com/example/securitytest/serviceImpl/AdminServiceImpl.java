package com.example.securitytest.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

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

        Map<String, String> sessionDetails = new HashMap<>();

        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        for (Object principal : allPrincipals) {
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
            for (SessionInformation sessionInfo : sessions) {
                sessionDetails.put(sessionInfo.getSessionId(), principal.toString());
            }
        }

        log.info("sessionDetails size = {}", sessionDetails.size());
        
        return sessionDetails;
    }

}
