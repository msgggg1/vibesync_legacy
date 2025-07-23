package com.vibesync.userpage.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vibesync.security.domain.CustomUser;
import com.vibesync.userpage.domain.NoteSummaryDTO;
import com.vibesync.userpage.domain.UserPageDataDTO;
import com.vibesync.userpage.service.UserPageService;

@Controller
@RequestMapping("page/user")
public class UserPageController {

	@Autowired
	private UserPageService userpageService;

	@GetMapping
	public String userPage(@RequestParam("acIdx") int profileUserAcIdx,
			@AuthenticationPrincipal CustomUser user,
			Model model
			) {

		// 1. 현재 로그인한 사용자 ID 가져오기 (비로그인 사용자 고려)
		int loggedInUserAcIdx = (user != null) ? user.getAcIdx() : 0;

		UserPageDataDTO userPageData = userpageService.getUserPageData(profileUserAcIdx, loggedInUserAcIdx, 1);

		List<NoteSummaryDTO> temp = userPageData.getMorePostsDTO().getPosts();
		for (NoteSummaryDTO note : temp) {
			System.out.println("note img : " + note.getThumbnailImg()); 
		}

		System.out.println("getPosts : " + userPageData.getMorePostsDTO().getPosts());
		// 3. 사용자 정보를 찾을 수 없는 경우, 에러 페이지로 이동
		if (userPageData == null || userPageData.getUserPageInfoDTO() == null) {
			//return "error/404"; // /WEB-INF/views/error/404.jsp 와 같은 에러 페이지의 뷰 이름
			return "member/login"; 
		}

		// 4. Model 객체에 데이터 추가
		model.addAttribute("userPageData", userPageData);

		return "page/user"; 

	}
}
