package com.vibesync.sidebar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vibesync.sidebar.domain.SidebarProfileDTO;

public interface SidebarFollowMapper {

	public List<SidebarProfileDTO> userFollowingList(@Param("acIdx") int acIdx) ;

	public List<SidebarProfileDTO> userFollowerList(@Param("acIdx") int acIdx) ;
	
}
