package com.vibesync.userpage.controller; 

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/userpage")
@Log4j2
public class UserpageController {

	@GetMapping("/{acIdx}")
	public String user(@PathVariable("acIdx") int acIdx, Model model) {
		log.info(acIdx + "번 유저 페이지 요청...GET");
		model.addAttribute("acIdx", acIdx);
		return "userpage/user";
	}
	
}
