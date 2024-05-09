package com.example.securitytest.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUserDetails implements UserDetails{
	
	private LoginProcessDTO loginProcessDTO;
	
	public CustomUserDetails(LoginProcessDTO loginProcessDTO) {
		log.info("CustomUserDetails - constructor / password = {}", loginProcessDTO.getPassWord());
		
		this.loginProcessDTO = loginProcessDTO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return loginProcessDTO.getRole();
			}
		});
		return collection;
	}

	@Override
	public String getPassword() {
		return loginProcessDTO.getPassWord();
	}

	@Override
	public String getUsername() {
		return loginProcessDTO.getUserName();
	}
	
	public String getUrl() {
		return loginProcessDTO.getAccessUrl();
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정 만료 확인
		// [true  : 계정이 만료되지 않았음]
		// [false : 계정이 만료 되었음]
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정 잠금 유무
		// [true  : 계정이 잠기지 않음]
		// [false : 계정이 잠김]
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 인증 자격 만료
		// [true  : 인증 자격 만료되지 않음]
		// [false : 인증 자격 만료됨]
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 계정 활성화 유무
		// [true  : 계정 활성화]
		// [false : 계정 비활성화]
		return true;
	}

}
