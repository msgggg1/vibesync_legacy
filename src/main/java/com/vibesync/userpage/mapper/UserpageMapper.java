package com.vibesync.userpage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vibesync.common.domain.Criteria;
import com.vibesync.userpage.domain.NoteSummaryDTO;
import com.vibesync.userpage.domain.UserPageInfoDTO;
import com.vibesync.userpage.domain.UserProfileDTO;

@Mapper
public interface UserpageMapper {
	
	// 프로필 정보, 게시글/팔로워/팔로잉 수, 현재 로그인 유저의 팔로우 여부를 한 번에 가져오는 쿼리
    UserPageInfoDTO getUserPageInfo(@Param("profileUserAcIdx") int profileUserAcIdx, @Param("loggedInUserAcIdx") int loggedInUserAcIdx);

    // 특정 유저의 게시글 목록을 가져오는 쿼리
    List<NoteSummaryDTO> getPostsByUser(@Param("profileUserAcIdx") int profileUserAcIdx, @Param("offset") int offset, @Param("limit") int limit);
    
    // 유저 페이지의 프로필 기본 정보
    UserProfileDTO findUserProfileByIdx(
            @Param("targetUserId") int targetUserId, 
            @Param("currentUserId") Integer currentUserId);
    
    // 게시글 목록 조회
    List<NoteSummaryDTO> findPostsByAuthorIdx(@Param("acIdx") int acIdx, 
            								  @Param("criteria") Criteria criteria);
    
}
