//package com.example.securitytest.serviceImpl;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.example.securitytest.dto.CustomUserDetails;
//import com.example.securitytest.dto.LoginProcessDTO;
//import com.example.securitytest.entity.UserEntity;
//import com.example.securitytest.repository.UserEntityRepository;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//	
//	private final UserEntityRepository userEntityRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		log.info("CustomUserDetailsService - loadUserByUsername()");
//		UserEntity findUserEntity = userEntityRepository.findByUserName(username).orElseThrow(() -> new IllegalArgumentException("Not Found User"));
//		return new CustomUserDetails(LoginProcessDTO.builder().userName(username)
//															  .passWord(findUserEntity.getPassWord())
//															  .role(findUserEntity.getRole())
//															  .build());
//	}
//
//}
