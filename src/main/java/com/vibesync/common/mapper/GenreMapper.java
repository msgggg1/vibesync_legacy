package com.vibesync.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vibesync.common.domain.GenreVO;

@Mapper
public interface GenreMapper {

	// 전체 장르 목록 조회
	public List<GenreVO> selectAll();
	
}
