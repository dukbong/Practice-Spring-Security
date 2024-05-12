package com.example.securitytest.serviceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import com.example.securitytest.custom.CustomToken;
import com.example.securitytest.dto.LoginDTO;
import com.example.securitytest.service.LoginService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
	
	private final AuthenticationManager authenticationManager;
	// 세션 기반 인증을 위해 세션에 저장
	private final HttpSession httpSession;
	
	private final SessionRegistry sessionRegistry;
	
	
	@Override
	public void loginProcess(LoginDTO loginDTO) {
		log.info("LoginServiceImpl - loginProcess()");
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassWord());
		// authenticate() 메소드 실행 시 UserDetailsService의 loadByUsername이 실행된다. [Default]
		// 실제 검증이 발생하는 단계이다.
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		// 세션 기반 인증을 위해 세션에 저장
		httpSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
	}

	// Custom
//	@Override
//	public void loginProcess(String url, LoginDTO loginDTO) {
//		log.info("LoginServiceImpl - loginProcess()");
//		// UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassWord());
//		CustomToken authToken = new CustomToken(loginDTO.getUserName(), loginDTO.getPassWord());
//		
//		// Map으로 Details를 세팅함으로써 최종적으로 다양한 정보를 SecurityContextHolder에서 볼 수 있다.
//		Map<String, Object> detail = new HashMap<>();
//		detail.put("url", url);
//		detail.put("test", "원하는 정보는 무엇이든 넣을 수 있다.");
//		authToken.setDetails(detail);
//		Authentication auth = authenticationManager.authenticate(authToken);
//		Object obj = auth.getPrincipal();
//		if(obj instanceof CustomUserDetails customUser) {
//			customUser.setInfo("loginTime", LocalDateTime.now().toString());
//		}
//		SecurityContextHolder.getContext().setAuthentication(auth);
//		httpSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//		
//		// sessionRegistry로 활성화 세션을 확인하고 관리하기 위해 추가
//		sessionRegistry.registerNewSession(httpSession.getId(), SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//	}
	
	// 메소드 분리
	@Override
	public void loginProcess(String url, LoginDTO loginDTO) {
		log.info("LoginServiceImpl - loginProcess()");
		CustomToken authToken = createAuthToken(url, loginDTO);
		Authentication auth = authenticateUser(authToken);
		insertAuthenticationContext(auth);
		insertHttpSession();
		insertSessionRegister();
	}
	
	private CustomToken createAuthToken(String url, LoginDTO loginDTO) {
		CustomToken authToken = new CustomToken(loginDTO.getUserName(), loginDTO.getPassWord());
		Map<String, Object> detail = new HashMap<>();
		detail.put("url", url);
		detail.put("test", "원하는 정보는 무엇이든 넣을 수 있다.");
		authToken.setDetails(detail);
		return authToken;
	}
	
	private Authentication authenticateUser(CustomToken authToken) {
		return authenticationManager.authenticate(authToken);
	}

	private void insertAuthenticationContext(Authentication auth) {
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private void insertHttpSession() {
		httpSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
	}

	private void insertSessionRegister() {
		sessionRegistry.registerNewSession(httpSession.getId(), SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}

}
