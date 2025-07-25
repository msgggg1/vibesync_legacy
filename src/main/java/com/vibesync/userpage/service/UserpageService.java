package com.vibesync.userpage.service;

import com.vibesync.userpage.domain.MorePostsDTO;
import com.vibesync.userpage.domain.UserPageDataDTO;

public interface UserpageService {

	// 유저 페이지 데이터
	public UserPageDataDTO getUserPageData(int profileUserAcIdx, int loggedInUserAcIdx, int pageNumber);
	
	// 무한 스크롤
	public MorePostsDTO getMorePostsWithStatus(int profileUserAcIdx, int pageNumber);
	
}
