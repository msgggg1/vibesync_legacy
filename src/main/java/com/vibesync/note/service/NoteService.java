package com.vibesync.note.service;

import java.util.List;
import java.util.Map;

import com.vibesync.note.domain.NoteSaveRequestDTO;
import com.vibesync.note.domain.NoteViewDTO;
import com.vibesync.security.domain.CustomUser;

public interface NoteService {
	
	// 노트 상세 보기
	NoteViewDTO getNoteViewData(int noteIdx, CustomUser currentUser);

	// 새 노트 작성
	Map<String, Object> saveNewNote(NoteSaveRequestDTO saveDTO, int acIdx);
	
	// 기존 노트 수정
	void updateNote(int noteIdx, NoteSaveRequestDTO saveDTO);
	
	// 노트 삭제
	void deleteNote(int noteIdx);
	
	// displayOrder 업데이트
	void updateDisplayOrder(List<Integer> orderedNoteIds);
	
}
