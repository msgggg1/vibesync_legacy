package com.vibesync.workspace.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowServiceMapper {
    
    /**
     * 사용자가 팔로우하는 사용자들의 ID 목록을 조회합니다.
     * @param acIdx 사용자 ID
     * @return 팔로우하는 사용자 ID 목록
     */
    List<Integer> selectUserFollowingIdList(int acIdx);
} 