package com.example.securitytest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.securitytest.custom.CustomToken;
import com.example.securitytest.dto.CustomUserDetails;
import com.example.securitytest.dto.JoinDTO;
import com.example.securitytest.dto.LoginDTO;
import com.example.securitytest.service.JoinService;
import com.example.securitytest.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SecurityController {
	
	private final LoginService loginServiceImpl;
	private final JoinService joinServiceImpl;

	@GetMapping("/")
	public ResponseEntity<String> mainP() {
		return ResponseEntity.ok().body("Main Page");
	}
	
	@PostMapping("/login/{url}")
	public ResponseEntity<LoginDTO> loginProcess(@PathVariable("url") String url, @RequestBody LoginDTO loginDTO) {
		loginServiceImpl.loginProcess(url, loginDTO);
		return ResponseEntity.ok().body(loginDTO);
	}
	
	@PostMapping("/join")
	public ResponseEntity<JoinDTO> joinProcess(@RequestBody JoinDTO joinDTO) {
		joinServiceImpl.joinProcess(joinDTO);
		return ResponseEntity.ok().body(joinDTO);
	}
	
    @GetMapping("/taking")
    public ResponseEntity<String> taskingPage() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	CustomToken token = (CustomToken) auth;
    	log.info("username   = {}", auth.getName()); // Or auth.getPrincipal()
    	log.info("password   = {}", auth.getCredentials()); // 비밀번호 같이 민감한 정보는 인증 완료 후 null로 초기화된다.
    	log.info("access url = {}", auth.getDetails());
    	log.info("startTime  = {}", token.getStartTime());
    	return ResponseEntity.ok().body("taking Page");
    }
	
}
