package com.vibesync.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vibesync.member.domain.Member;
import com.vibesync.member.domain.MemberSummaryVO;
import com.vibesync.member.domain.MemberVO;
import com.vibesync.member.domain.SignUpDTO;

@Mapper
public interface MemberMapper {

		// 회원가입
		int insertUser(SignUpDTO dto);
		
		// 이메일로 계정 정보 조회. 로그인 시 사용
		Member findByEmail(String email);
		
		// 일반 조회용: pw를 제외한 안전한 정보만 조회
	    public MemberVO findVOByEmail(String email); 
		
		// 회원가입 시 중복 검사 : 닉네임, 이메일 한번에
		List<MemberVO> duplicateTest(@Param("nickname") String nickname, @Param("email")String email);
		
		//회원 활동 관련
		int preferredCategoryIdx(int acIdx);
	    
	    // 특정 사용자의 기본 프로필 정보 (ID, 닉네임, 프로필 이미지 경로)를 조회.
		MemberSummaryVO getBasicUserInfoById(int ac_idx);

	    //특정 사용자가 작성한 총 게시글 수를 조회
	    int getPostCount(int ac_idx);
	    
	    /*비밀번호 재설정*/
	    //비밀번호 재설정 토큰 DB저장
	    public void saveResetToken(@Param("email") String email, @Param("token")String token);
	    
	    // 유효한 토큰 사용하여 이메일 조회
	    public String findEmailByValidToken(String token);
	    
	    // 사용된 토큰 DB삭제
	    public void deleteToken(String token);
	    
	    // 이메일 주소를 기준으로 사용자의 비밀번호 업데이트
	    public void updatePassword(@Param("email") String email, @Param("pw") String hashedPassword);
}
