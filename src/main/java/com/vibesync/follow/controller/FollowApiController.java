package com.vibesync.follow.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	public boolean followToggle(@RequestParam("targetUserAcIdx") int targetUserAcIdx,
						@AuthenticationPrincipal CustomUser user) {
		log.info("팔로우 토글 요청...GET");
		
		int followerAcIdx = user.getAcIdx();
		
		return this.followService.toggleFollow(followerAcIdx, targetUserAcIdx);
	}
	
}
