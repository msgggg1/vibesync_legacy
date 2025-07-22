package com.vibesync.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vibesync.common.domain.ContentVO;

@Mapper
public interface ContentMapper {

	// 전체 컨텐츠 목록 조회
	public List<ContentVO> selectAll();
	
}
