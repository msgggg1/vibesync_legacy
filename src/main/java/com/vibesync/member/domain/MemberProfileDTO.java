package com.vibesync.member.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberProfileDTO {
	
	private int acIdx;
	
	private String email;
	private String pw;
	private String nickname;
	private String name;
	private String role;
	
	private int categoryIdx;
	
	private String img;
	private Long kakaoAuthId; 
	private Long googleId;
	
	private int followingCount;
	private int followerCount;
	private Timestamp createdAt;
	
	// setting 테이블의 theme 컬럼
	private String theme;
	
}