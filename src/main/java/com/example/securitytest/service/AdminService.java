package com.example.securitytest.service;

import java.util.List;
import java.util.Map;

public interface AdminService {

	List<Map<String, Object>> sessionManagement();
	
	void expireSessionsWithoutRoleC();

}
