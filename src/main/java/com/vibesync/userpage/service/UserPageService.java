package com.vibesync.userpage.service;

import java.util.List;

import com.vibesync.common.domain.Criteria;
import com.vibesync.security.domain.CustomUser;
import com.vibesync.userpage.domain.NoteSummaryDTO;
import com.vibesync.userpage.domain.UserPageDTO;

public interface UserPageService {

	// 유저 페이지 데이터
	public UserPageDTO getUserPageData(int targetUserId, CustomUser currentUser);
	
	// 추가 게시물 로드
    List<NoteSummaryDTO> getMorePosts(int targetUserId, Criteria criteria);
	
}
