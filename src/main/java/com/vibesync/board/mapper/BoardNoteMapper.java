package com.vibesync.board.mapper;

import java.util.List;

import com.vibesync.board.domain.NoteListDTO;
import com.vibesync.common.domain.Criteria;

public interface BoardNoteMapper {
	
	// 총 게시글 수 조회
	public int selectTotalCount(Criteria criteria);
	
	// 전체 게시글 목록 조회 (페이징 처리 O)
	public List<NoteListDTO> selectWithPaging(Criteria criteria);
	
}
