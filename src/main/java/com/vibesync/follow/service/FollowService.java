package com.vibesync.follow.service;

import java.util.List;

import com.vibesync.follow.domain.FollowUserDTO;

public interface FollowService {
    
	/** 팔로우 상태 토글 : 두 사용자 간의 팔로우 상태 전환 (팔로우/언팔로우)
     * @param followerAcIdx 팔로우/언팔로우를 하는 사용자 ID
     * @param targetUserAcIdx 팔로우/언팔로우를 당하는 사용자 ID
     * @return 결과가 팔로우면 true, 언팔로우면 false
     */
    public boolean toggleFollow(int followerAcIdx, int targetUserAcIdx);
	
    /** 팔로우 상태 확인 : 한 사용자가 다른 사용자를 팔로우하고 있는지 확인
     * @param followerAcIdx 팔로우를 하는 사용자 ID
     * @param targetUserAcIdx 팔로우를 당하는 사용자 ID
     * @return 팔로우 중이면 true, 아니면 false
     */
    public boolean isFollowing(int followerAcIdx, int targetUserAcIdx);
    
    /** 팔로잉 목록 : 유저가 팔로우하고 있는 사용자 ID 목록을 조회
     * @param acIdx 팔로우를 하는 사용자 ID
     * @return 팔로우하는 사용자 ID 목록
     */
    public List<Integer> userFollowingIdList(int acIdx);
    
    /** 팔로잉 목록 : 유저가 팔로우하고 있는 사용자 상세 정보 목록을 조회
     * @param acIdx 팔로우를 하는 사용자 ID
     * @return 팔로우하는 사용자 상세 정보 목록
     */
    public List<FollowUserDTO> getFollowingList(int acIdx);
    
    /** 팔로워 목록 : 유저를 팔로우하고 있는 사용자 상세 정보 목록을 조회
     * @param acIdx 팔로우를 당하는 사용자 ID
     * @return 팔로우하는 사용자 상세 정보 목록
     */
    public List<FollowUserDTO> getFollowerList(int acIdx);
    
    /** 팔로잉 카운트 : 유저가 팔로우하고 있는 사용자 수를 조회
     * @param acIdx 팔로우를 하는 사용자 ID
     * @return 팔로잉 수
     */
    public int getFollowingCount(int acIdx);
    
    /** 팔로워 카운트 : 유저를 팔로우하고 있는 사용자 수를 조회
     * @param acIdx 팔로우를 당하는 사용자 ID
     * @return 팔로워 수
     */
    public int getFollowerCount(int acIdx);
    
}
