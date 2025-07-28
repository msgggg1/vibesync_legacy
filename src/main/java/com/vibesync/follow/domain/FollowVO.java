package com.vibesync.follow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowVO {

	private int followsIdx;
	private int followerAcIdx; // 팔로우하는 사용자
	private int followedAcIdx; // 팔로우 당하는 사용자
	
}
