package com.vibesync.common.controller; 

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vibesync.mainpage.domain.MainPageDTO;

/**
 * 메인 페이지, 소개 페이지 등 단순 페이지 뷰로의 이동을 처리하는 컨트롤러
 */
@Controller
@RequestMapping("/page") 
public class PageController {

    @GetMapping("/main")
    public String showMainPage() {
    	/*
    	MainPageDTO mainPageDTO = mainPageService.loadMainPage(memberInfo.getCategory_idx());
        model.addAttribute("mainPageDTO", mainPageDTO);
       */
        return "page/main";
    }
}
