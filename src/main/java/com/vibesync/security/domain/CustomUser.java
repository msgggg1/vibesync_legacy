package com.vibesync.security.domain;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User; // Spring Security의 User 클래스

import com.vibesync.member.domain.Member; // 비밀번호가 있는 Member 클래스

import lombok.Getter;

@Getter
public class CustomUser extends User{
	
	// private final Member member;

	// Member 객체의 정보 중, User 클래스가 갖지 않는 추가 정보만 정의
	private final int acIdx;
	private final String nickname;
	private final String img;
	private final int categoryIdx;

	public CustomUser(Member member) {

		// 1. 부모 클래스(User)의 생성자를 호출하여 핵심 정보(ID, PW, 권한)를 전달
		super(	member.getEmail(), // username
				member.getPw(), // password
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRole())) // authorities
		);
		
		// 2. 주입받은 Member 객체를 필드에 저장합니다.
        // this.member = member;

		// 2. 이 클래스만의 추가 정보를 초기화
		this.acIdx = member.getAcIdx();
		this.nickname = member.getNickname();
		this.img = member.getImg();
		this.categoryIdx = member.getCategoryIdx();
    
	}
}