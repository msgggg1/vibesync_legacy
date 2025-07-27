package com.vibesync.userpage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vibesync.common.domain.Criteria;
import com.vibesync.security.domain.CustomUser;
import com.vibesync.userpage.domain.NoteSummaryDTO;
import com.vibesync.userpage.domain.UserPageDTO;
import com.vibesync.userpage.service.UserpageService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/userpage")
@Log4j2
public class UserpageApiController {

	@Autowired
	UserpageService userpageService;
	
	// 초기 데이터 (프로필 + 첫 페이지 게시물)
    @GetMapping("/{targetUserId}")
    public ResponseEntity<UserPageDTO> getUserPageData( @PathVariable("targetUserId") int targetUserId,
											            @AuthenticationPrincipal CustomUser currentUser) {
    	log.info("유저 페이지 Api 데이터 요청...GET");
    	UserPageDTO userPageData = userpageService.getUserPageData(targetUserId, currentUser);
    	System.out.println("> userPageData.posts : " + userPageData.getPosts());
        return ResponseEntity.ok(userPageData);
    }
    
    // 추가 게시물 로딩
    @GetMapping("/{targetUserId}/posts")
    public ResponseEntity<List<NoteSummaryDTO>> getMorePosts( @PathVariable("targetUserId") int targetUserId,
													          Criteria criteria) { // pageNum 파라미터를 자동으로 바인딩
    	log.info("유저 페이지 추가 게시물 Api 데이터 요청...GET");
        List<NoteSummaryDTO> morePosts = userpageService.getMorePosts(targetUserId, criteria);
        return ResponseEntity.ok(morePosts);
    }
	
}
