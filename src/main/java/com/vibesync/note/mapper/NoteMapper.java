package com.vibesync.note.mapper;

import org.apache.ibatis.annotations.Param;

import com.vibesync.note.domain.NoteDetailDTO;
import com.vibesync.note.domain.NoteVO;

public interface NoteMapper {
	
	// ID로 노트 상세 정보 조회 (작성자 정보 포함)
    NoteDetailDTO findNoteDetailByIdx(int noteIdx);
	
	// 게시글 상세보기
	public NoteDetailDTO selectByNoteIdx(@Param("noteIdx") int noteIdx);
	
	// note_seq의 다음 시퀀스 값 미리 조회
    public int selectNextNoteIdx();
	
	// 게시글 작성
	public int insert(NoteVO note);
	
	// 게시글 수정
	public int update(NoteVO note);
	
}
