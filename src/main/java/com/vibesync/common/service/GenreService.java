package com.vibesync.common.service;

import java.util.List;

import com.vibesync.common.domain.GenreVO;

public interface GenreService {
	
	// 전체 장르 목록 조회
    public List<GenreVO> findAll();
	
}
