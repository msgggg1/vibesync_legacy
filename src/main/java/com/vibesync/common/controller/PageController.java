package com.vibesync.common.controller; 

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vibesync.common.annotation.AuthenticatedUserPages;

import lombok.extern.log4j.Log4j2;

/**
 * 단순 페이지 뷰로의 이동을 처리하는 컨트롤러
 */
@Controller 
@AuthenticatedUserPages
@Log4j2
public class PageController {

	@GetMapping("/user")
	public String user(@RequestParam("acIdx") int acIdx, Model model) {
		log.info(acIdx + "번 유저 페이지 요청...GET");
		model.addAttribute("acIdx", acIdx);
		return "page/user";
	}
}
