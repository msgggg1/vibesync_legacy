package com.vibesync.common.controller; 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vibesync.common.annotation.AuthenticatedUserPages;

/**
 * 단순 페이지 뷰로의 이동을 처리하는 컨트롤러
 */
@Controller 
@AuthenticatedUserPages
public class PageController {
	@GetMapping("/page/workspace")
	public String workspace() {
		return "page/workspace";	
	}
}
