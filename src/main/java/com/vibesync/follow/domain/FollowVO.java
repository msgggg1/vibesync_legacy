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
	private int acFollow;
	private int acFollowing;
	
}
