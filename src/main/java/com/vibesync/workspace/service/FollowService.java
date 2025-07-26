package com.vibesync.workspace.service;

import java.util.List;

public interface FollowService {
    
    /**
     * 사용자가 팔로우하는 사용자들의 ID 목록을 조회합니다.
     * @param acIdx 사용자 ID
     * @return 팔로우하는 사용자 ID 목록
     */
    List<Integer> getUserFollowingIdList(int acIdx);
} 