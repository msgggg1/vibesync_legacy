package com.vibesync.member.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.common.service.EmailService;
import com.vibesync.member.domain.MemberProfileDTO;
import com.vibesync.member.domain.SettingVO;
import com.vibesync.member.domain.SignUpDTO;
import com.vibesync.member.mapper.MemberMapper;
import com.vibesync.member.mapper.SettingMapper;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private SettingMapper settingMapper;

	@Override
	public MemberProfileDTO autoLogin(String email) throws Exception {
		return this.memberMapper.findProfileByEmail(email);
	}

	// 소셜 로그인 가입 후 즉시 로그인 처리
	@Override
	public MemberProfileDTO getUserByEmail(String email) {
		return this.memberMapper.findProfileByEmail(email);
	}

	@Override
	public void register(SignUpDTO dto) throws Exception {
		List<MemberProfileDTO> duplicates = memberMapper.duplicateTest(dto.getNickname(), dto.getEmail());
		
		if (duplicates.isEmpty()) { // 중복되는 닉네임, 이메일 없음
			String encodedPassword = passwordEncoder.encode(dto.getPassword());
			dto.setPassword(encodedPassword);
			
			// userAccount 테이블에 회원 정보 INSERT
			this.memberMapper.insertUser(dto);
			
			// setting 테이블에 기본 설정 INSERT
			SettingVO defaultSetting = new SettingVO();
	        defaultSetting.setAcIdx(dto.getAcIdx());
	        settingMapper.insertDefaultSetting(defaultSetting);
	        
	        // notification_settings 테이블에 모든 알림 종류의 기본값(ON)을 INSERT
	        List<String> notificationTypes = Arrays.asList("LIKE", "COMMENT", "FOLLOW");
	        if (!notificationTypes.isEmpty()) {
	            settingMapper.insertDefaultNotificationSettings(defaultSetting.getSettingIdx(), notificationTypes);
	        }
			
        } else {
        	boolean nicknameDupl = false;
        	boolean emailDupl = false;
        	
        	for (MemberProfileDTO memberVO : duplicates) {
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
        	
        } 
	}

	@Override
	@Transactional
	public void initiateReset(String email, String requestURL) throws SQLException {
		if(this.memberMapper.findByEmailForAuth(email) == null) {
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
