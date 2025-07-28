package com.vibesync.mainpage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vibesync.common.annotation.AuthenticatedUserPages;
import com.vibesync.mainpage.domain.MainPageDTO;
import com.vibesync.mainpage.service.MainPageService;
import com.vibesync.security.domain.CustomUser;

@RestController
@RequestMapping("/api/mainpage")
@AuthenticatedUserPages
public class MainpageApiController {

	@Autowired
	MainPageService mainPageService;
	
	@GetMapping("")
    public ResponseEntity<MainPageDTO> user(@AuthenticationPrincipal CustomUser user) {
		MainPageDTO mainPageDTO = mainPageService.loadMainPage(user.getCategoryIdx());
    	return new ResponseEntity<>(mainPageDTO, HttpStatus.OK);
    }
	
}
