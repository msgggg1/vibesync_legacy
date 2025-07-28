package com.vibesync.userpage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibesync.common.domain.Criteria;
import com.vibesync.security.domain.CustomUser;
import com.vibesync.userpage.domain.NoteSummaryDTO;
import com.vibesync.userpage.domain.UserPageDTO;
import com.vibesync.userpage.domain.UserProfileDTO;
import com.vibesync.userpage.mapper.UserpageMapper;

@Service
public class UserPageServiceImpl implements UserPageService{

	@Autowired
	private UserpageMapper userpageMapper;
	
	@Override
    public UserPageDTO getUserPageData(int targetUserId, CustomUser currentUser) {
        Integer currentUserId = (currentUser != null) ? currentUser.getAcIdx() : null;
        
        UserProfileDTO userProfile = userpageMapper.findUserProfileByIdx(targetUserId, currentUserId);
        List<NoteSummaryDTO> firstPagePosts = userpageMapper.findPostsByAuthorIdx(targetUserId, new Criteria(1, 9));
        
        return UserPageDTO.builder()
                .userProfile(userProfile)
                .posts(firstPagePosts)
                .build();
    }

    @Override
    public List<NoteSummaryDTO> getMorePosts(int targetUserId, Criteria criteria) {
        return userpageMapper.findPostsByAuthorIdx(targetUserId, criteria);
    }

}
