package com.vibesync.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vibesync.board.domain.NoteDetailDTO;
import com.vibesync.board.domain.NoteListDTO;
import com.vibesync.board.domain.NoteVO;
import com.vibesync.common.domain.Criteria;

@Mapper
public interface BoardNoteMapper {

	// 전체 게시글 목록 조회 (페이징 처리 X)
	public List<NoteVO> selectAll();
	
	// 전체 게시글 목록 조회 (페이징 처리 O)
	public List<NoteListDTO> selectWithPaging(Criteria criteria);
	
	// 총 게시글 수 조회
	public int selectTotalCount(Criteria criteria);
	
	// 게시글 상세보기
	public NoteDetailDTO selectByNoteIdx(@Param("noteIdx") int noteIdx);
}
