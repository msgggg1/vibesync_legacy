package com.vibesync.follow.mapper;

import java.util.List;

import com.vibesync.follow.domain.FollowUserDTO;
import com.vibesync.follow.domain.FollowVO;

public interface FollowMapper {
	
	// 팔로우 : 팔로우 데이터 추가
	public int insertFollow(FollowVO follow);
	
	// 언팔로우 : 팔로우 데이터 삭제
	public int deleteFollow(FollowVO follow);
	
	// 팔로우 관계 확인
    public int checkFollowStatus(FollowVO follow);
    
    // 팔로잉 목록 : 유저가 팔로우하고 있는 사용자 ID 목록 조회
    public List<Integer> userFollowingIdList(int acIdx);
    
    // 팔로잉 목록 : 유저가 팔로우하고 있는 사용자 상세 정보 목록 조회
    public List<FollowUserDTO> getFollowingList(int acIdx);
    
    // 팔로워 목록 : 유저를 팔로우하고 있는 사용자 상세 정보 목록 조회
    public List<FollowUserDTO> getFollowerList(int acIdx);
    
    // 팔로잉 카운트 : 유저가 팔로우하고 있는 사용자 수 조회
    public int getFollowingCount(int acIdx);
    
    // 팔로워 카운트 : 유저를 팔로우하고 있는 사용자 수 조회
    public int getFollowerCount(int acIdx);
	
}
