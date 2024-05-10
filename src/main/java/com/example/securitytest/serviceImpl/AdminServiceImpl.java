package com.example.securitytest.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
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

        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        log.info("allPrincipals size = {}", allPrincipals.size());
        
        for(Object principal : allPrincipals) {
        	List<SessionInformation> test = sessionRegistry.getAllSessions(principal, false);
        	log.info("test size=  {}", test.size());
        	
        }
        // 각 Principal(사용자)에 대한 세션 정보를 가져옵니다.
        for (Object principal : allPrincipals) {
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                List<SessionInformation> sessions = sessionRegistry.getAllSessions(userDetails, false);
                sessions.stream().forEach(s-> log.info(userDetails.getUsername()+" : "+ s.getSessionId()));
            }
        }
        
        return null;
    }

}
