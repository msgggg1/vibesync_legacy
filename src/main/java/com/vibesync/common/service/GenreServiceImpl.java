package com.vibesync.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibesync.common.domain.GenreVO;
import com.vibesync.common.mapper.GenreMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class GenreServiceImpl implements GenreService {

	@Autowired
    private GenreMapper genreMapper;

	@Override
	public List<GenreVO> findAll() {
		log.info("전체 장르 목록 조회 요청");
		
    	log.debug("GenreMapper.selectAll() 실행");
    	List<GenreVO> genreList = genreMapper.selectAll();
		
		log.info("조회된 장르 개수: " + genreList.size());
		
		return genreList;
	}
	
}
