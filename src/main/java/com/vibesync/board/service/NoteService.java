package com.vibesync.board.service;

import java.util.List;

import com.vibesync.board.domain.NoteVO;

public interface NoteService {
	
	// 게시글 목록 조회
    public List<NoteVO> getNoteList();
	
}
