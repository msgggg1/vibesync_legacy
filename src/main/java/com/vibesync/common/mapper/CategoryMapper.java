package com.vibesync.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vibesync.common.domain.CategoryVO;

@Mapper
public interface CategoryMapper {

	// 카테고리 전체 정보 조회
	public List<CategoryVO> selectAll();
	
}
