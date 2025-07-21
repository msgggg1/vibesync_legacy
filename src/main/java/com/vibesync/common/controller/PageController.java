package com.vibesync.common.controller; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vibesync.common.annotation.AuthenticatedUserPages;
import com.vibesync.mainpage.MainPageService;
import com.vibesync.mainpage.domain.MainPageDTO;
import com.vibesync.member.domain.Member;
import com.vibesync.member.domain.MemberVO;
import com.vibesync.security.domain.CustomUser;

/**
 * 메인 페이지, 소개 페이지 등 단순 페이지 뷰로의 이동을 처리하는 컨트롤러
 */
@Controller
@RequestMapping("/page") 
@AuthenticatedUserPages
public class PageController {
	
	@Autowired 
	private MainPageService mainPageService;

    @GetMapping("/main")
    public String showMainPage(Model model, @AuthenticationPrincipal CustomUser user) {
    	System.out.println("> PageController.showMainPage() - 호출");
    	
    	Member member = user.getMember();
    	
    	MainPageDTO mainPageDTO = mainPageService.loadMainPage(member.getCategory_idx());
        model.addAttribute("mainPageDTO", mainPageDTO);
       
        return "page/main";
    }
}
