package com.vibesync.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibesync.board.domain.NoteVO;
import com.vibesync.board.mapper.NoteMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private NoteMapper noteMapper;

	@Override
	public List<NoteVO> getNoteList() {
		log.info("DB에서 모든 게시글 목록을 조회합니다");
		
		log.debug("NoteMapper.selectAll() 실행");
		List<NoteVO> noteList = noteMapper.selectAll();
		
		log.info(String.format("총 {%d}개의 게시글 목록을 조회했습니다.", noteList.size()));
		
		return noteList;
	}
	
}
