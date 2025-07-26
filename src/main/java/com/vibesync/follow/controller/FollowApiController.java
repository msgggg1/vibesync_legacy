package com.vibesync.follow.controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vibesync.common.annotation.AuthenticatedUserPages;
import com.vibesync.follow.domain.FollowUserDTO;
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
	// 팔로잉 목록 조회
	@GetMapping("/following")
	public List<FollowUserDTO> getFollowingList(@AuthenticationPrincipal CustomUser user) {
		log.info("팔로잉 목록 조회 요청");
		return this.followService.getFollowingList(user.getAcIdx());
	}
	
	// 팔로워 목록 조회
	@GetMapping("/follower")
	public List<FollowUserDTO> getFollowerList(@AuthenticationPrincipal CustomUser user) {
		log.info("팔로워 목록 조회 요청");
		return this.followService.getFollowerList(user.getAcIdx());
	}
	
	// 팔로잉 카운트 조회
	@GetMapping("/following/count")
	public int getFollowingCount(@AuthenticationPrincipal CustomUser user) {
		log.info("팔로잉 카운트 조회 요청");
		return this.followService.getFollowingCount(user.getAcIdx());
	}
	
	// 팔로워 카운트 조회
	@GetMapping("/follower/count")
	public int getFollowerCount(@AuthenticationPrincipal CustomUser user) {
		log.info("팔로워 카운트 조회 요청");
		return this.followService.getFollowerCount(user.getAcIdx());
	}
	
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
