package com.vibesync.sidebar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vibesync.sidebar.domain.SidebarProfileDTO;

@Mapper
public interface SidebarNoteMapper {

	// 사용자가 작성한 노트 목록
	public List<SidebarProfileDTO> getNoteIdxListByUser(@Param("acIdx") int acIdx);
	
	// 사용자가 작성한 노트 조회수 총합
	public int getViewCountsForNotesAllByUser(@Param("acIdx") int acIdx);
	
}
