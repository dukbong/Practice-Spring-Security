package com.example.securitytest.serviceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.securitytest.dto.JoinDTO;
import com.example.securitytest.entity.UserEntity;
import com.example.securitytest.entity.UserEntityId;
import com.example.securitytest.enums.PasswordRegex;
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
		boolean duplicateUserNameAndAccessUrl = userEntityRepository.existsById_UserNameAndId_AccessUrl(joinDTO.getUserName(), joinDTO.getAccessUrl());
		if(duplicateUserNameAndAccessUrl) {
			throw new IllegalArgumentException("Duplicate UserName or AccessUrl");
		}
		validateUserPwd(joinDTO.getPassWord());
		
		UserEntity userEntity = UserEntity.builder().id(UserEntityId.builder().userName(joinDTO.getUserName()).accessUrl(joinDTO.getAccessUrl()).build())
											        .passWord(bCryptPasswordEncoder.encode(joinDTO.getPassWord()))
											        .role("ROLE_B")
											        .build();
		userEntityRepository.save(userEntity);
	}
	
	private void validateUserPwd(String pwd) {
		if (pwd == null || pwd.isEmpty()) {
			throw new IllegalArgumentException("비밀번호는 비어있을 수 없습니다.");
		}

		if (pwd.length() < 8) {
			throw new IllegalArgumentException("비밀번호는 8자 이상이여야 합니다.");
		}

		if (!pwd.matches(PasswordRegex.UPPERCASE.getRegex())) {
			throw new IllegalArgumentException("비밀번호는 대문자가 한개 이상 포함되어야 합니다.");
		}

		if (!pwd.matches(PasswordRegex.LOWERCASE.getRegex())) {
			throw new IllegalArgumentException("비밀번호는 소문자가 한개 이상 포함되어야 합니다.");
		}

		if (!pwd.matches(PasswordRegex.SPECIAL_CHARACTER.getRegex())) {
			throw new IllegalArgumentException("비밀번호는 특수문자가 한개 이상 포함되어야 합니다.");
		}
	}

}
