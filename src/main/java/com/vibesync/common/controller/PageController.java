package com.vibesync.common.controller; 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vibesync.common.annotation.AuthenticatedUserPages;

/**
 * 메인 페이지, 소개 페이지 등 단순 페이지 뷰로의 이동을 처리하는 컨트롤러
 */
@Controller
@RequestMapping("/page") 
@AuthenticatedUserPages
public class PageController {

}
