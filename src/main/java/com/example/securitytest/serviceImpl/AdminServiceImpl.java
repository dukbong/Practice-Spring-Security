package com.example.securitytest.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

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
	public List<Map<String, Object>> sessionManagement() {
		log.info("sessionManagement");

		List<Map<String, Object>> activeSessions = new ArrayList<>();

		List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		log.info("활성 세션 수 [세션이 존재하는 사용자 수] = {}", allPrincipals.size());

		for (Object principal : allPrincipals) {
			log.info("test = {}", principal);
			if (principal instanceof CustomUserDetails detail) {
				detail = (CustomUserDetails) principal;
				List<SessionInformation> sessions = sessionRegistry.getAllSessions(detail, false);
				for (SessionInformation info : sessions) {
					List<String> authorities = detail.getAuthorities().stream().map(GrantedAuthority::getAuthority)
							.collect(Collectors.toList());

					Map<String, Object> sessionInfo = new HashMap<>();
					sessionInfo.put("sessionId", info.getSessionId());
					sessionInfo.put("userName", detail.getUsername());
					sessionInfo.put("accessUrl", detail.getInfo().get("url"));
					sessionInfo.put("loginTime", detail.getInfo().get("loginTime"));
					sessionInfo.put("authorities", authorities);

					activeSessions.add(sessionInfo);

					log.info("활성 유저 정보");
					log.info("session id = {}", info.getSessionId());
					log.info("user id = {}", detail.getUsername());
					log.info("user accessUrl = {}", detail.getInfo().get("url"));
					log.info("user loginTime = {}", detail.getInfo().get("loginTime"));
					log.info("user authorities = {}", authorities);
				}
			}
		}

		return activeSessions;
	}

	@Override
	public void expireSessionsWithoutRoleC() {
		List<Object> principals = sessionRegistry.getAllPrincipals();

		for (Object principal : principals) {
			if (principal instanceof CustomUserDetails detail) {
				detail = (CustomUserDetails) principal;
				List<SessionInformation> sessions = sessionRegistry.getAllSessions(detail, false);
				
				
				boolean hasRole = detail.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("ROLE_C"));
				
				if(!hasRole) {
					for (SessionInformation sessionInfo : sessions) {
						sessionInfo.expireNow();
					}
				}
			}
		}
	}

}
