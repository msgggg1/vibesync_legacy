package com.vibesync.userpage.service;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import org.quartz.utils.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibesync.userpage.domain.MorePostsDTO;
import com.vibesync.userpage.domain.NoteSummaryDTO;
import com.vibesync.userpage.domain.UserPageDataDTO;
import com.vibesync.userpage.domain.UserPageInfoDTO;
import com.vibesync.userpage.domain.UserSummaryVO;
import com.vibesync.userpage.mapper.UserpageMapper;

@Service
public class UserpageServiceImpl implements UserpageService{

	private static final int PAGE_SIZE = 9; // 한 번에 로드할 게시글 수

	@Autowired
	private UserpageMapper userpageMapper;
	
	@Override
	public UserPageDataDTO getUserPageData(int profileUserAcIdx, int loggedInUserAcIdx, int pageNumber) {
	/*
		UserPageDataDTO userPageData = new UserPageDataDTO();
		UserPageInfoDTO userPageInfo = null;
		UserSummaryVO basicUserInfo = null;
		List<NoteSummaryDTO> posts = null;
		boolean hasMorePosts = false;

		userPageInfo = userpageMapper.getUserPageInfo(profileUserAcIdx, loggedInUserAcIdx);
		if (userPageInfo == null) {return null;}

		// UserVO에서 UserPageInfoDTO로 기본 정보 복사
		basicUserInfo = new UserPageInfoDTO(userPageData.getUserPageInfoDTO());

		// 3. 현재 로그인한 사용자가 이 프로필 사용자를 팔로우하는지 여부 설정
		if (loggedInUserAcIdx != null && loggedInUserAcIdx != profileUserAcIdx) {
			userProfileInfo.setFollowedByCurrentUser(followDAO.isFollowing(loggedInUserAcIdx, profileUserAcIdx));
		} else {
			userProfileInfo.setFollowedByCurrentUser(false); // 본인이거나 비로그인 시
		}
		pageData.setUserProfile(userProfileInfo); // 완성된 UserPageInfoDTO를 pageData에 설정

		// 4. 사용자의 게시글 목록 조회 (페이징)
		int offset = (pageNumber - 1) * PAGE_SIZE;
		posts = noteDAO.getPostsByUser(profileUserAcIdx, offset, PAGE_SIZE);
		pageData.setPosts(posts);

		// 5. 더 많은 게시글이 있는지 확인
		int totalPosts = userProfileInfo.getPostCount();
		if ((offset + posts.size()) < totalPosts) {
			hasMorePosts = true;
		}
		pageData.setHasMorePosts(hasMorePosts);
		pageData.setNextPageNumber(pageNumber + 1);
*/
		return null;
	}

	@Override
	public MorePostsDTO getMorePostsWithStatus(int profileUserAcIdx, int pageNumber) {

		return null;
	}

}
