package com.vibesync.common.service;

import java.util.List;

import com.vibesync.common.domain.CategoryVO;

public interface CategoryService {
	
	// 카테고리 전체 정보 조회 (캐시 저장)
    public List<CategoryVO> findAllWithCache();
	
}
