package com.vibesync.sidebar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.board.mapper.NoteMapper;
import com.vibesync.follow.mapper.FollowMapper;
import com.vibesync.security.domain.CustomUser;
import com.vibesync.sidebar.domain.SidebarDTO;
import com.vibesync.sidebar.domain.UserProfileViewDTO;
import com.vibesync.sidebar.domain.UserSummaryVO;

@Service
public class SidebarService {

    @Autowired
    private NoteMapper noteMapper; // 사용자 정보용 매퍼
    
    @Autowired
    private FollowMapper followMapper; // 팔로우 정보용 매퍼

    @Transactional(readOnly = true)
    public SidebarDTO loadSidebar(CustomUser customUser) {
        
        // 1. DTO 객체 생성
        SidebarDTO sidebarDTO = new SidebarDTO();
        
        int ac_idx = customUser.getAc_idx();
        
        // 2. DB에서 데이터 조회
    	List<UserSummaryVO> followingList = followMapper.userFollowingList(ac_idx);
    	List<UserSummaryVO> followerList = followMapper.userFollowerList(ac_idx);
        
        int postCount = noteMapper.getNoteIdxListByUser(ac_idx).size();
		int postViewCount = noteMapper.getViewCountsForNotesAllByUser(ac_idx);
		
		UserProfileViewDTO userProfile = UserProfileViewDTO.builder()
														   .ac_idx(ac_idx)
														   .nickname(customUser.getNickname())
														   .postCount(postCount)
														   .postViewCount(postViewCount)
														   .followingCount(followingList.size())
														   .followerCount(followerList.size())
														   .build();
        
	 
        return new SidebarDTO().builder()
				 .userProfile(userProfile)
				 .followingList(followingList)
				 .followerList(followerList)
				 .build();
    }

}
