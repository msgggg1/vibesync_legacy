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
public class MemberVO {

	private int acIdx;
	private String email;
	private String nickname;
	private String img;
	private String name;
	private Timestamp createdAt;
	private int categoryIdx;
	
	private Long kakaoAuthId; 
}