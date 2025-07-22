package com.vibesync.common.service;

import java.util.List;

import com.vibesync.common.domain.ContentVO;

public interface ContentService {
	
	// 전체 컨텐츠 목록 조회
    public List<ContentVO> findAll();
	
}
