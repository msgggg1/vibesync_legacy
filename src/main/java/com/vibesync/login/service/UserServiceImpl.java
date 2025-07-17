package com.vibesync.login.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibesync.login.domain.LoginDTO;
import com.vibesync.login.domain.SignUpDTO;
import com.vibesync.login.domain.UserVO;
import com.vibesync.login.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserVO login(LoginDTO dto) throws Exception {

		return this.userMapper.login(dto);
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
		
		return this.userMapper.insertUser(dto);
	}

	@Override
	public void initiateReset(String email, String requestURL) throws SQLException {
		this.userMapper.findByEmail(email);
		
	}

	@Override
	public boolean finalizeReset(String token, String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
