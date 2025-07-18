package com.vibesync.login.domain;

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
public class UserVO {

	private int ac_idx;
	private String email;
	private String nickname;
	private String img;
	private String name;
	private Timestamp created_at;
	private int category_idx;
	
	private Long kakao_auth_id; 
}