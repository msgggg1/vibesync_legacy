package com.vibesync.common.advice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.vibesync.category.domain.CategoryVO;
import com.vibesync.category.service.CategoryService;

import lombok.extern.log4j.Log4j;

@ControllerAdvice
@Log4j
public class GlobalControllerAdvice { // 모든 컨트롤러의 메소드 실행 전에 호출될 메서드들을 작성하는 클래스
	
	@Autowired
	private CategoryService categoryService;
	
	// "categoryList" 라는 이름으로 Model에 데이터 담기
    @ModelAttribute("categoryList")
    public List<CategoryVO> globalCategories() {
    	log.info("모든 모델에 전역 카테고리 목록 추가");
    	return categoryService.findAll(); // 캐싱이 적용된 서비스 메소드 호출
    }

}
