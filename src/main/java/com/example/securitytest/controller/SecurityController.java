package com.example.securitytest.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.securitytest.dto.CustomUserDetails;
import com.example.securitytest.dto.JoinDTO;
import com.example.securitytest.dto.LoginDTO;
import com.example.securitytest.service.AdminService;
import com.example.securitytest.service.JoinService;
import com.example.securitytest.service.LoginService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SecurityController {
	
	private final LoginService loginServiceImpl;
	private final JoinService joinServiceImpl;
	private final AdminService adminServiceImpl;
	private final MessageSource messageSource;

	@GetMapping("/")
	public ResponseEntity<String> mainP() {
		return ResponseEntity.ok().body("Main Page");
	}
	
	@PostMapping("/login/{url}")
	public ResponseEntity<LoginDTO> loginProcess(@PathVariable("url") String url, @RequestBody LoginDTO loginDTO, HttpSession session) {
		try {
			loginServiceImpl.loginProcess(url, loginDTO);
		} catch (Exception e) {
			session.invalidate();
		}
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
    		log.info("info - username    = {}", detail.getUsername());
    		log.info("info - password    = {}", detail.getPassword());
    		List<String> list =  detail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    		log.info("info - authorities = {}", list);
    		log.info("info - url         = {}", detail.getInfo().get("url"));
    		log.info("info - test        = {}", detail.getInfo().get("test"));
    		log.info("info - loginTime   = {}", detail.getLoginTime());
    	}
    	return ResponseEntity.ok().body("taking Page");
    }
    
    @GetMapping("/get/allsession")
    public ResponseEntity<List<Map<String, Object>>> allSession() {
    	List<Map<String, Object>> activeSessions = adminServiceImpl.sessionManagement();
    	return ResponseEntity.ok().body(activeSessions);
    }
    
    @GetMapping("/delete/session/c")
    public ResponseEntity<String> invalidateAllSessions() {
    	adminServiceImpl.expireSessionsWithoutRoleC();
    	return ResponseEntity.ok().body(messageSource.getMessage("expire.session.message", null, LocaleContextHolder.getLocale()));
    }
    
}
