package com.vibesync.note.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vibesync.note.domain.ChildNoteListDTO;
import com.vibesync.note.domain.NoteDetailDTO;
import com.vibesync.note.domain.NoteVO;

public interface NoteMapper {
	
    // 새 노트 저장
    int insert(NoteVO note);
    
    // 기존 노트 수정
    int update(NoteVO note);
    
    // 노트 삭제
    int delete(int noteIdx);
	
    // ID로 노트 상세 정보 조회 (작성자 정보 포함)
    NoteDetailDTO findNoteDetailByIdx(int noteIdx);
    
	// note_seq의 다음 시퀀스 값 미리 조회
    int selectNextNoteIdx();
    
    // 하위 노트 추가 시 마지막 순서(displayOrder) 조회
    int getLastDisplayOrder(int parentNoteIdx);
    
    // 하위 노트 목록
    List<ChildNoteListDTO> findChildNotesByParentIdx(int parentNoteIdx);
    
    // displayOrder 업데이트
    void updateDisplayOrder(@Param("noteIdx") int noteIdx, @Param("displayOrder") int displayOrder);
	
}
