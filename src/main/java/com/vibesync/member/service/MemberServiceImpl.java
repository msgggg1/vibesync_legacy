package com.vibesync.member.service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.common.service.EmailService;
import com.vibesync.member.domain.LoginDTO;
import com.vibesync.member.domain.Member;
import com.vibesync.member.domain.MemberVO;
import com.vibesync.member.domain.SignUpDTO;
import com.vibesync.member.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	/*
	@Override
	public MemberVO login(LoginDTO dto) throws Exception {
		// 1. DTO에서 받은 이메일로 사용자 정보를 조회합니다.
        Member member = memberMapper.findByEmail(dto.getEmail());

        // 2. 사용자 정보가 존재하고, 입력된 비밀번호가 DB의 암호화된 비밀번호와 일치하는지 확인합니다.
        if (member != null && passwordEncoder.matches(dto.getPassword(), member.getPw())) {
            // 3. 로그인 성공 시, 사용자 정보 객체를 반환합니다.
        	MemberVO memberVO = MemberVO.builder().acIdx(member.getAcIdx())
        											.categoryIdx(member.getCategoryIdx())
        											.createdAt(member.getCreatedAt())
        											.email(member.getEmail())
        											.img(member.getImg())
        											.kakaoAuthId(member.getKakaoAuthId())
        											.name(member.getName())
        											.nickname(member.getNickname())
        											.build();
            return memberVO;
        }
        // 로그인 실패 시 null을 반환합니다.
        return null; 
	}
	*/

	@Override
	public MemberVO autoLogin(String email) throws Exception {
		return this.memberMapper.findVOByEmail(email);
	}

	// 소셜 로그인 가입 후 즉시 로그인 처리
	@Override
	public MemberVO getUserByEmail(String email) {
		return this.memberMapper.findVOByEmail(email);
	}

	@Override
	public int register(SignUpDTO dto) throws Exception {
		List<MemberVO> duplicates = memberMapper.duplicateTest(dto.getNickname(), dto.getEmail());
		
		if (duplicates.isEmpty()) { // 중복되는 닉네임, 이메일 없음
			String encodedPassword = passwordEncoder.encode(dto.getPassword());
			dto.setPassword(encodedPassword);
			
			return this.memberMapper.insertUser(dto);
			
        } else {
        	boolean nicknameDupl = false;
        	boolean emailDupl = false;
        	
        	for (MemberVO memberVO : duplicates) {
				if (memberVO.getNickname().equals(dto.getNickname())) {
					nicknameDupl = true;
				}
				if (memberVO.getEmail().equals(dto.getEmail())) {
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
	@Transactional
	public void initiateReset(String email, String requestURL) throws SQLException {
		if(this.memberMapper.findByEmail(email) == null) {
			 // 존재하지 않는 이메일이면, 보안을 위해 아무 작업도 하지 않고 조용히 종료.
            return;
		};
		
		// 2. 토큰 생성 및 DB 저장
        String token = UUID.randomUUID().toString();
        this.memberMapper.saveResetToken(email, token);
        
        // 3. 이메일로 보낼 재설정 링크 생성 (Spring MVC 경로에 맞게 수정)
        String resetLink = requestURL + "/user/show-reset-form?token=" + token;

        // 4. 이메일 발송 서비스 호출
        emailService.sendEmail(email, resetLink);
	}

	@Override
	@Transactional
	public boolean finalizeReset(String token, String newPassword) {
		// 1. 유효한 토큰인지 확인하고 이메일 가져오기
        String email = this.memberMapper.findEmailByValidToken(token);

        if (email != null) {
            // 2. 새 비밀번호를 암호화
            String encodedPassword = passwordEncoder.encode(newPassword);
            
            // 3. 암호화된 새 비밀번호로 업데이트
            memberMapper.updatePassword(email, encodedPassword);
            
            // 4. 사용된 토큰은 즉시 삭제
            memberMapper.deleteToken(token);
            
            return true;
        }

        return false;
	}
	
	
	
}
