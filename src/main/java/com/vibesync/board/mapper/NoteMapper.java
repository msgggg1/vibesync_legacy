package com.vibesync.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vibesync.board.domain.NoteVO;
import com.vibesync.common.domain.Criteria;
import com.vibesync.sidebar.domain.UserSummaryVO;

@Mapper
public interface NoteMapper {

	// 전체 게시글 목록 조회 (페이징 처리 X)
	public List<NoteVO> selectAll();
	
	// 전체 게시글 목록 조회 (페이징 처리 O)
	public List<NoteVO> selectWithPaging(Criteria criteria);
	
	// 총 게시글 수 조회
	int selectTotalCount(Criteria criteria);

	// 사이드 바 사용
	public List<UserSummaryVO> getNoteIdxListByUser(@Param("acIdx") int ac_idx);
	
	// 사이드 바 사용
	public int getViewCountsForNotesAllByUser(@Param("acIdx") int ac_idx); 
	
}
