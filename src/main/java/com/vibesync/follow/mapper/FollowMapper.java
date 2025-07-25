package com.vibesync.follow.mapper;

import com.vibesync.follow.domain.FollowVO;

public interface FollowMapper {
	
	// 팔로우 : 팔로우 데이터 추가
	public int insertFollow(FollowVO follow);
	
	// 언팔로우 : 팔로우 데이터 삭제
	public int deleteFollow(FollowVO follow);
	
	// 팔로우 관계 확인
    public int checkFollowStatus(FollowVO follow);
	
}
