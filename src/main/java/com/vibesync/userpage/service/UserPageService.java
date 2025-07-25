package com.vibesync.userpage.service;

import com.vibesync.userpage.domain.MorePostsDTO;
import com.vibesync.userpage.domain.UserPageDataDTO;

public interface UserPageService {
	
	public UserPageDataDTO getUserPageData(int profileUserAcIdx, int loggedInUserAcIdx, int pageNumber) ;

	public MorePostsDTO getMorePostsWithStatus(int profileUserAcIdx, int pageNumber) ;
}
