package com.vibesync.security.domain;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User; // Spring Security의 User 클래스

import com.vibesync.member.domain.MemberProfileDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUser extends User {

	private static final long serialVersionUID = 1L;	
	
	// MemberProfileDTO 객체의 정보 중, User 클래스가 갖지 않는 추가 정보만 정의
	
	// final: 세션 동안 절대 변하지 않는 값
	private final int acIdx;
	private final String email;
	private final String nickname;
	private final String role;
	private final int categoryIdx;
	
	// non-final: 세션 중에 서비스가 직접 변경할 수 있는 값
	private String img;
	private int followingCount;
	private int followerCount;
	private String theme;

	public CustomUser(MemberProfileDTO member) {

		// 1. 부모 클래스(User)의 생성자를 호출하여 핵심 정보(ID, PW, 권한)를 전달
		super(	member.getEmail(), // username
				member.getPw(), // password
				Collections.singletonList(new SimpleGrantedAuthority(member.getRole())) // authorities
		);

		// 2. 이 클래스만의 추가 정보를 초기화
		this.acIdx = member.getAcIdx();
		this.email = member.getEmail();
		this.nickname = member.getNickname();
		this.role = member.getRole();
		this.categoryIdx = member.getCategoryIdx();
		this.img = member.getImg();
		this.followingCount = member.getFollowingCount();
		this.followerCount = member.getFollowerCount();
		this.theme = member.getTheme();
    
	}
	
	public MemberProfileDTO toMemberProfileDTO() {
		MemberProfileDTO member = MemberProfileDTO.builder()
									.acIdx(this.acIdx)
									.email(this.email)
									.nickname(this.nickname)
									.role(this.role)
									.categoryIdx(this.categoryIdx)
									.img(this.img)
									.followingCount(this.followingCount)
									.followerCount(this.followerCount)
									.theme(this.theme)
									.build();
		
		return member;
	}
}