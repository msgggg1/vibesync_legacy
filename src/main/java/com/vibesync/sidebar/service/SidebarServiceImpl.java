package com.vibesync.sidebar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.security.domain.CustomUser;
import com.vibesync.sidebar.domain.SidebarDTO;
import com.vibesync.sidebar.domain.UserProfileViewDTO;
import com.vibesync.sidebar.domain.UserSummaryVO;
import com.vibesync.sidebar.mapper.SidebarFollowMapper;
import com.vibesync.sidebar.mapper.SidebarNoteMapper;

@Service
public class SidebarServiceImpl implements SidebarService {

    @Autowired
    private SidebarNoteMapper noteMapper; // 사용자 정보용 매퍼
    
    @Autowired
    private SidebarFollowMapper followMapper; // 팔로우 정보용 매퍼

    @Override
    @Transactional(readOnly = true)
    public SidebarDTO loadSidebar(CustomUser customUser) {
        
        // 1. DTO 객체 생성
        SidebarDTO sidebarDTO = new SidebarDTO();
        
        int acIdx = customUser.getAcIdx();
        
        // 2. DB에서 데이터 조회
    	List<UserSummaryVO> followingList = followMapper.userFollowingList(acIdx);
    	List<UserSummaryVO> followerList = followMapper.userFollowerList(acIdx);
        
        int postCount = noteMapper.getNoteIdxListByUser(acIdx).size();
		int postViewCount = noteMapper.getViewCountsForNotesAllByUser(acIdx);
		
		UserProfileViewDTO userProfile = UserProfileViewDTO.builder()
														   .acIdx(acIdx)
														   .nickname(customUser.getNickname())
														   .postCount(postCount)
														   .postViewCount(postViewCount)
														   .followingCount(followingList.size())
														   .followerCount(followerList.size())
														   .build();
        
		sidebarDTO = SidebarDTO.builder()
								 .userProfile(userProfile)
								 .followingList(followingList)
								 .followerList(followerList)
								 .build();
		
        return sidebarDTO;
    }

}
