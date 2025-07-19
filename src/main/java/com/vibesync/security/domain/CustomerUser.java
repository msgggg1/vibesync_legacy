package com.vibesync.security.domain;

import com.vibesync.member.domain.Member;

import lombok.Getter;

@Getter
public class CustomerUser {
	
	private final Member member = new Member();
	

}
