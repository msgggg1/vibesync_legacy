package com.vibesync.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibesync.common.domain.ContentVO;
import com.vibesync.common.mapper.ContentMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ContentServiceImpl implements ContentService {

	@Autowired
    private ContentMapper contentMapper;

	@Override
	public List<ContentVO> findAll() {
		log.info("전체 컨텐츠 목록 조회 요청");
		
    	log.debug("ContentMapper.selectAll() 실행");
    	List<ContentVO> contentList = contentMapper.selectAll();
		
		log.info("조회된 컨텐츠 개수: " + contentList.size());
		
		return contentList;
	}
	
}
