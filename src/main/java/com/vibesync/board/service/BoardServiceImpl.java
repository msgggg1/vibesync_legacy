package com.vibesync.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibesync.board.domain.NoteListDTO;
import com.vibesync.board.mapper.BoardNoteMapper;
import com.vibesync.common.domain.Criteria;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardNoteMapper boardNoteMapper;

	@Override
	public List<NoteListDTO> findAllNotesWithPaging(Criteria criteria) {
		log.info("페이징 처리된 게시글 목록 조회 요청. 조건: " + criteria);
		
		log.debug("BoardNoteMapper.selectWithPaging() 실행. 파라미터: " + criteria);
		List<NoteListDTO> noteList = this.boardNoteMapper.selectWithPaging(criteria);
		
		log.debug("현재 페이지에서 조회된 게시글 개수: " + noteList.size());
		log.debug("현재 페이지에서 조회된 게시글 목록 : " + noteList);
		
		return noteList;
	}

	@Override
	public int getTotalCount(Criteria criteria) {
		log.info("조건에 맞는 전체 게시글 개수 조회. 조건: " + criteria);
		
		return this.boardNoteMapper.selectTotalCount(criteria);
	}
	
}
