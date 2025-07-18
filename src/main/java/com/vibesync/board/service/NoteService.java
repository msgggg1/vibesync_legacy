package com.vibesync.board.service;

import java.util.List;

import com.vibesync.board.domain.NoteVO;
import com.vibesync.common.domain.Criteria;

public interface NoteService {
	
	// 전체 게시글 목록 조회 (페이징 처리 X)
    public List<NoteVO> findAllNotes();
    
    // 전체 게시글 목록 조회 (페이징 처리 O)
    public List<NoteVO> findAllNotesWithPaging(Criteria criteria);

    // 총 게시글 수 조회
	public int getTotalCount(Criteria criteria);
	
}
