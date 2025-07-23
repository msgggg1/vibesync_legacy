package com.vibesync.mainpage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vibesync.common.annotation.AuthenticatedUserPages;
import com.vibesync.mainpage.domain.MainPageDTO;
import com.vibesync.mainpage.service.MainPageService;
import com.vibesync.security.domain.CustomUser;

@Controller
@RequestMapping("/page/main") 
@AuthenticatedUserPages
// @log4j2
public class MainPageController {
	
	@Autowired 
	private MainPageService mainPageService;

    @GetMapping
    public void showMainPage(Model model, @AuthenticationPrincipal CustomUser user) {
    	// log.info("> PageController.showMainPage() - 호출");
      
    	MainPageDTO mainPageDTO = mainPageService.loadMainPage(user.getCategoryIdx());

    	model.addAttribute("mainPageDTO", mainPageDTO);
    	model.addAttribute("categoryIdx", user.getCategoryIdx());

    }
}