package com.vibesync.category.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vibesync.category.domain.CategoryVO;

@Mapper
public interface CategoryMapper {

	// 카테고리 전체 정보 조회
	public List<CategoryVO> selectAll();
	
	// 카테고리 추가
    public int insert(CategoryVO category);
	
}
