package com.vibesync.login.service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.common.service.EmailService;
import com.vibesync.login.domain.LoginDTO;
import com.vibesync.login.domain.SignUpDTO;
import com.vibesync.login.domain.UserVO;
import com.vibesync.login.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		UserVO userVO = this.userMapper.findByEmail(dto.getEmail());
		if (userVO != null && passwordEncoder.matches(dto.getPassword(), userVO.get)) {
			
		}
		return 
	}

	@Override
	public UserVO autoLogin(String email) throws Exception {
	
		return this.userMapper.findByEmail(email);
	}

	// 소셜 로그인 가입 후 즉시 로그인 처리
	@Override
	public UserVO getUserByEmail(String email) {
		
		return this.userMapper.findByEmail(email);
	}

	@Override
	public int register(SignUpDTO dto) throws Exception {
		List<UserVO> duplicates = userMapper.duplicateTest(dto.getNickname(), dto.getEmail());
		
		if (duplicates.isEmpty()) { // 중복되는 닉네임, 이메일 없음
			
			String encodedPassword = passwordEncoder.encode(dto.getPassword());
			dto.setPassword(encodedPassword);
			
			return this.userMapper.insertUser(dto);
			
        } else {
        	boolean nicknameDupl = false;
        	boolean emailDupl = false;
        	
        	for (UserVO userVO : duplicates) {
				if (userVO.getNickname().equals(dto.getNickname())) {
					nicknameDupl = true;
				}
				if (userVO.getEmail().equals(dto.getEmail())) {
					emailDupl = true;
				}
			}
        	
        	if (nicknameDupl && emailDupl) {
        		throw new IllegalArgumentException(String.format("닉네임 [%s]이 이미 사용 중입니다.<br>이미 가입한 정보가 존재하는 이메일입니다.", dto.getNickname()));
        	} else if (nicknameDupl) {
        		throw new IllegalArgumentException(String.format("[%s]는 이미 사용 중인 닉네임입니다.", dto.getNickname()));
        	} else if (emailDupl) {
        		throw new IllegalArgumentException("이미 가입한 정보가 존재하는 이메일입니다.");
        	}
        	
        	return 0;
        } 
	}

	@Override
	public void initiateReset(String email, String requestURL) throws SQLException {
		if(this.userMapper.findByEmail(email) == null) {
			 // 존재하지 않는 이메일이면, 보안을 위해 아무 작업도 하지 않고 조용히 종료.
            return;
		};
		
		// 2. 토큰 생성 및 DB 저장
        String token = UUID.randomUUID().toString();
        this.userMapper.saveResetToken(email, token);
	}

	@Override
	@Transactional
	public boolean finalizeReset(String token, String newPassword) {
		boolean isSuccess = false;
		
		// 1. 유효한 토큰인지 확인하고 이메일 가져오기
		String email = this.userMapper.findEmailByValidToken(token);
		
		if (email != null) {
			String encodedPassword = passwordEncoder.encode(newPassword);
			userMapper.updatePassword(email, encodedPassword);
			userMapper.deleteToken(token);
			isSuccess = true;
		}
		
		return isSuccess;
	}
	
}
