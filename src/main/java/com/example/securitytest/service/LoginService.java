package com.example.securitytest.service;

import com.example.securitytest.dto.LoginDTO;

public interface LoginService {

	void loginProcess(LoginDTO loginDTO);

	void loginProcess(String url, LoginDTO loginDTO);
	
}
