package com.vibesync.member.domain;

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
public class MemberSummaryVO { // 조회에 필요한 최소한의 정보

	private int acIdx;
	private String nickname;
	private String profileImg;
	private int categoryIdx;
	private boolean followedByCurrentUser;
	
}
