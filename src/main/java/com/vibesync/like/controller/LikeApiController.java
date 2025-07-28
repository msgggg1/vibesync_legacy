package com.vibesync.like.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vibesync.common.annotation.AuthenticatedUserPages;
import com.vibesync.like.domain.LikeRequestDTO;
import com.vibesync.like.service.LikeService;
import com.vibesync.security.domain.CustomUser;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("/api/likes")
@AuthenticatedUserPages
public class LikeApiController {
	
	@Autowired
	LikeService likeService;
	
	// 좋아요 토글 (/vibesync/api/likes/toggle)
	@PostMapping(value="/toggle")
	public ResponseEntity<Map<String, Object>> toggleLike(
												@RequestBody LikeRequestDTO dto,
												@AuthenticationPrincipal CustomUser user) {
		log.info("좋아요 토글 요청...GET");
		
		// boolean isLiking = this.likeService.toggleLike(user.getAcIdx(), noteIdx);
		
		Map<String, Object> response = new HashMap<>();
		// response.put("isLiking", isLiking);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
