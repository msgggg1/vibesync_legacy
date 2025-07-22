package com.vibesync.board.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vibesync.board.domain.NoteDetailDTO;
import com.vibesync.board.domain.NoteListDTO;
import com.vibesync.common.domain.Criteria;

public interface NoteService {
    
    // 전체 게시글 목록 조회 (페이징 처리 O)
    public List<NoteListDTO> findAllNotesWithPaging(Criteria criteria);

    // 총 게시글 수 조회
	public int getTotalCount(Criteria criteria);

	// 게시글 상세 보기
	public NoteDetailDTO findNoteByNoteIdx(@Param("noteIdx") int noteIdx);
	
}
