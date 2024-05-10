package com.example.securitytest.serviceImpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.securitytest.dto.CustomUserDetails;
import com.example.securitytest.dto.LoginProcessDTO;
import com.example.securitytest.entity.UserEntity;
import com.example.securitytest.repository.UserEntityRepository;
import com.example.securitytest.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
	
	private final UserEntityRepository userEntityRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("CustomAddUrlUserDetailsService - loadUserByUsername()");
		UserEntity findUserEntity = userEntityRepository.findByUserName(username).orElseThrow(() -> new IllegalArgumentException("Not Found User"));
		return new CustomUserDetails(LoginProcessDTO.builder().userName(username)
															  .passWord(findUserEntity.getPassWord())
															  .role(findUserEntity.getRole())
															  .build());
	}

	@Override
	public UserDetails loadUserByUsernameAndUrl(String username, String url) throws UsernameNotFoundException {
		log.info("CustomAddUrlUserDetailsService - loadUserByUsernameAndUrl()");
		UserEntity findUserEntity = userEntityRepository.findByUserNameAndAccessUrl(username, url).orElseThrow(() -> new IllegalArgumentException("Not Found User"));
		return new CustomUserDetails(LoginProcessDTO.builder().userName(username)
															  .passWord(findUserEntity.getPassWord())
															  .role(findUserEntity.getRole())
															  .accessUrl(url)
															  .build());
	}

}
