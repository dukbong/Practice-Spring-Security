package com.example.securitytest.serviceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.securitytest.dto.JoinDTO;
import com.example.securitytest.entity.UserEntity;
import com.example.securitytest.repository.UserEntityRepository;
import com.example.securitytest.service.JoinService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {

	private final UserEntityRepository userEntityRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void joinProcess(JoinDTO joinDTO) {
		log.info("JoinServiceImpl - joinProcess()");
		boolean duplicateUserName = userEntityRepository.existsByUserName(joinDTO.getUserName());
		if(duplicateUserName) {
			throw new IllegalArgumentException("Duplicate UserName");
		}
		
		UserEntity userEntity = UserEntity.builder().userName(joinDTO.getUserName())
											        .passWord(bCryptPasswordEncoder.encode(joinDTO.getPassWord()))
											        .role("ROLE_B")
											        .build();
		userEntityRepository.save(userEntity);
	}

}
