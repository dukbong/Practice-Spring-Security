package com.example.securitytest.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.securitytest.custom.CustomToken;
import com.example.securitytest.dto.CustomUserDetails;
import com.example.securitytest.dto.JoinDTO;
import com.example.securitytest.dto.LoginDTO;
import com.example.securitytest.service.AdminService;
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
	private final AdminService adminServiceImpl;

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
	
    @GetMapping("/info")
    public ResponseEntity<String> taskingPage() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	Object ob = auth.getPrincipal();
    	if(ob instanceof CustomUserDetails detail) {
    		log.info("UserDetails 변환 성공");
    		log.info("userDetail - username    = {}", detail.getUsername());
    		log.info("userDetail - password    = {}", detail.getPassword());
    		log.info("userDetail - authorities = {}", detail.getAuthorities());
    		log.info("userDetail - url         = {}", detail.getInfo().get("url"));
    		log.info("userDetail - test        = {}", detail.getInfo().get("test"));
    	}
    	return ResponseEntity.ok().body("taking Page");
    }
    
    @GetMapping("/get/allsession")
    public ResponseEntity<Map<String, String>> allSession() {
    	Map<String, String> allSessionResult = adminServiceImpl.sessionManagement();
    	return null;
    }
	
}
