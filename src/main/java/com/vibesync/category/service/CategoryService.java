package com.vibesync.category.service;

import java.util.List;

import com.vibesync.category.domain.CategoryVO;

public interface CategoryService {
	
	// 카테고리 전체 정보 조회 (캐시 저장)
    public List<CategoryVO> findAll();
	
    // 새로운 카테고리 추가 (캐시 비우기 적용)
    public void addNewCategory(CategoryVO category);
    
}
