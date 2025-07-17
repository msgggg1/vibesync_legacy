package com.vibesync.login.service;

import java.sql.SQLException;

import com.vibesync.login.domain.LoginDTO;
import com.vibesync.login.domain.SignUpDTO;
import com.vibesync.login.domain.UserVO;

public interface UserService {
		
		// 로그인 : 이메일, 비밀번호 활용
		public UserVO login(LoginDTO dto) throws Exception; 
		
		// 자동로그인 : 쿠키의 사용자 이메일 정보 활용
		public UserVO autoLogin(String email) throws Exception;
		
		// 소셜 로그인 가입 후, 즉시 로그인 처리를 위해 추가
		public UserVO getUserByEmail(String email); 
		
		// 회원가입
		public int register(SignUpDTO dto) throws Exception;
		
		// 비밀번호 초기화
		public void initiateReset(String email, String requestURL) throws SQLException;
		
		// 토큰 검증 후 비밀번호 업데이트
		public boolean finalizeReset(String token, String newPassword);
		 
}
