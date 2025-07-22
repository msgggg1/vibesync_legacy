package com.vibesync.follow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vibesync.sidebar.domain.UserSummaryVO;

public interface FollowMapper {

	public List<UserSummaryVO> userFollowingList(@Param("acIdx") int acIdx) ;

	public List<UserSummaryVO> userFollowerList(@Param("acIdx") int acIdx) ;
	
}
