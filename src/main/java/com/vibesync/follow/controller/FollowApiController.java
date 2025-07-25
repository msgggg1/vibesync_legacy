package com.vibesync.follow.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vibesync.common.annotation.AuthenticatedUserPages;
import com.vibesync.follow.service.FollowService;
import com.vibesync.security.domain.CustomUser;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("/api/follow/*")
@AuthenticatedUserPages
public class FollowApiController {
	
	@Autowired
	FollowService followService;
	
	// 팔로우 토글 (/vibesync/follow/followToggle)
	@PostMapping(value="/followToggle")
	public ResponseEntity<Map<String, Object>> followToggle(@RequestParam("targetUserAcIdx") int targetUserAcIdx,
						@AuthenticationPrincipal CustomUser user) {
		log.info("팔로우 토글 요청...GET");
		
		int followerAcIdx = user.getAcIdx();
		boolean isFollowing = this.followService.toggleFollow(followerAcIdx, targetUserAcIdx);
		int newFollowingCount = this.followService.getFollowingCount(followerAcIdx);
		int newFollowerCount = this.followService.getFollowerCount(targetUserAcIdx);
		
		Map<String, Object> response = new HashMap<>();
		response.put("isFollowing", isFollowing);
		response.put("currentUserFollowingCount", newFollowingCount);
		response.put("targetUserFollowingCount", newFollowerCount);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
}
