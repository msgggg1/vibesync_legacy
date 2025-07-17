package com.vibesync.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.vibesync.common.domain.CategoryVO;
import com.vibesync.common.mapper.CategoryMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
    private CategoryMapper categoryMapper;
    
    @Override
	// "categories" 캐시에 결과값을 저장. 다음 호출부터는 DB 조회 없이 캐시에서 바로 반환.
    @Cacheable("categories")
    public List<CategoryVO> findAllWithCache() {
    	log.info("DB에서 모든 카테고리 목록을 조회합니다 (캐시 저장 예정)");
    	
    	log.debug("CategoryMapper.selectAll() 실행");
    	List<CategoryVO> categories = categoryMapper.selectAll();
        
        log.info(String.format("총 {%d}개의 카테고리를 조회했습니다.", categories.size()));
        
        return categories;
    }
	
}
