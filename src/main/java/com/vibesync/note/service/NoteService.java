package com.vibesync.note.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.vibesync.note.domain.BoardEditRequestDTO;
import com.vibesync.note.domain.NoteDetailDTO;
import com.vibesync.note.domain.NoteSaveRequestDTO;
import com.vibesync.note.domain.NoteViewDTO;
import com.vibesync.security.domain.CustomUser;

public interface NoteService {
	
	// 게시글 상세 보기
	public NoteDetailDTO findNoteByNoteIdx(@Param("noteIdx") int noteIdx);

	// 게시글 작성
	public int save(NoteSaveRequestDTO dto, HttpServletRequest request);

	// 게시글 수정
	public int edit(BoardEditRequestDTO dto, HttpServletRequest request);
	
	// 노트 상세 보기
	NoteViewDTO getNoteViewData(int noteIdx, CustomUser currentUser);

	// 새 노트 작성
	int saveNewNote(NoteSaveRequestDTO saveDTO, int acIdx);

}
