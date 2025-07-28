package com.vibesync.follow.mapper;
import java.util.List;

import com.vibesync.follow.domain.FollowVO;
import com.vibesync.follow.domain.FollowerInfoDTO;

public interface FollowMapper {
	
	// 팔로우 : 팔로우 데이터 추가
	public int insertFollow(FollowVO follow);
	
	// 언팔로우 : 팔로우 데이터 삭제
	public int deleteFollow(FollowVO follow);
	
	// 팔로우 관계 확인
    public int checkFollowStatus(FollowVO follow);
    
    // 팔로잉 목록 : 유저가 팔로우하고 있는 사용자 ID 목록 조회
    public List<Integer> userFollowingIdList(int acIdx);
    
    // 팔로잉 수 조회
    public int selectFollowingCount(int followerAcIdx);
    
    // 팔로워 수 조회
    public int selectFollowerCount(int followedAcIdx);
    
    // 팔로잉 목록 조회
    public List<FollowerInfoDTO> selectFollowingList(int followerAcIdx);
    
    // 팔로워 목록 조회
    public List<FollowerInfoDTO> selectFollowerList(int followedAcIdx);
	
}
