package com.vibesync.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibesync.board.domain.NoteVO;
import com.vibesync.board.mapper.NoteMapper;
import com.vibesync.common.domain.Criteria;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private NoteMapper noteMapper;

	@Override
	public List<NoteVO> findAllNotes() {
		log.info("모든 게시글 목록 조회.");
		
		log.debug("NoteMapper.selectAll() 실행");
		List<NoteVO> noteList = noteMapper.selectAll();
		
		log.info("조회된 총 게시글 개수: " + noteList.size());
		
		return noteList;
	}

	@Override
	public List<NoteVO> findAllNotesWithPaging(Criteria criteria) {
		log.info("페이징 처리된 게시글 목록 조회 요청. 조건: " + criteria);
		
		log.debug("NoteMapper.selectWithPaging() 실행. 파라미터: " + criteria);
		List<NoteVO> noteList = noteMapper.selectWithPaging(criteria);
		
		log.debug("현재 페이지에서 조회된 게시글 개수: " + noteList.size());
		
		return noteList;
	}

	@Override
	public int getTotalCount(Criteria criteria) {
		log.info("조건에 맞는 전체 게시글 개수 조회. 조건: " + criteria);
		
		return noteMapper.selectTotalCount(criteria);
	}
	
}
