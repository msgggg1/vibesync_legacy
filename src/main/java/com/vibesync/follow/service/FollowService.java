package com.vibesync.follow.service;

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
    
}
