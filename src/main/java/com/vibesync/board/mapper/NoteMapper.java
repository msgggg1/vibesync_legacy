package com.vibesync.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vibesync.board.domain.NoteVO;

@Mapper
public interface NoteMapper {

	// 게시글 목록 조회
	public List<NoteVO> selectAll();
	
}
