package com.vibesync.userpage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vibesync.page.domain.UserPageDTO;
import com.vibesync.userpage.service.UserpageService;

@RestController
@RequestMapping("/api/userpage")
public class UserpageApiController {

	@Autowired
	UserpageService userpageService;
	
	@GetMapping("")
    public ResponseEntity<UserPageDTO> user(@RequestParam("acIdx") int acIdx) {
    	UserPageDTO userPageData = this.userpageService.getUserPageData(acIdx);
    	return new ResponseEntity<>(userPageData, HttpStatus.OK);
    }
	
}
