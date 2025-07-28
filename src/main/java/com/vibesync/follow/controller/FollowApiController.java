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
import com.vibesync.follow.domain.FollowerInfoDTO;
import com.vibesync.follow.service.FollowService;
import com.vibesync.security.domain.CustomUser;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("/api/follows")
@AuthenticatedUserPages
public class FollowApiController {
	
	@Autowired
	FollowService followService;
	
	// 팔로우 토글 (/vibesync/follows/toggle)
	@PostMapping(value="/toggle")
	public ResponseEntity<Map<String, Object>> followToggle(@RequestParam("targetUserAcIdx") int targetUserAcIdx,
						@AuthenticationPrincipal CustomUser user) {
		log.info("팔로우 토글 요청...POST");
		
		int followerAcIdx = user.getAcIdx();
		boolean isFollowing = this.followService.toggleFollow(followerAcIdx, targetUserAcIdx);
		int newFollowingCount = this.followService.getFollowingCount(followerAcIdx);
		int newFollowerCount = this.followService.getFollowerCount(targetUserAcIdx);
		
		Map<String, Object> response = new HashMap<>();
		response.put("isFollowing", isFollowing);
		response.put("currentUserFollowingCount", newFollowingCount);
		response.put("targetUserFollowerCount", newFollowerCount);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	// 팔로워 수 조회 (/vibesync/api/follows/followerCount)
	@GetMapping(value="/followerCount")
	public ResponseEntity<Object> followerCount (@RequestParam("followedAcIdx") int followedAcIdx) {
		log.info("팔로워 수 조회 요청...GET");
		int followerCount = this.followService.getFollowerCount(followedAcIdx);
		return new ResponseEntity<>(followerCount, HttpStatus.OK);
	}
	
	// 팔로잉 목록 (/vibesync/api/follows/followingList)
	@GetMapping(value="/followingList")
	public ResponseEntity<Object> followingList (@RequestParam("followerAcIdx") int followerAcIdx) {
		log.info("팔로잉 목록 요청...GET");
		List<FollowerInfoDTO> followingList = this.followService.getFollowingList(followerAcIdx);
		return new ResponseEntity<>(followingList, HttpStatus.OK);
	}
	
	// 팔로워 목록 (/vibesync/api/follows/followerList)
	@GetMapping(value="/followerList")
	public ResponseEntity<Object> followerList (@RequestParam("followingAcIdx") int followingAcIdx) {
		log.info("팔로잉 목록 요청...GET");
		List<FollowerInfoDTO> followerList = this.followService.getFollowerList(followingAcIdx);
		return new ResponseEntity<>(followerList, HttpStatus.OK);
	}
	
}
