package com.vibesync.mainpage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vibesync.mainpage.domain.NoteSummaryDTO;
import com.vibesync.mainpage.domain.UserSummaryVO;

@Mapper
public interface RecommendMapper {
		// 전체 카테고리 - 인기글
		List<NoteSummaryDTO> popularNoteByAllCategory(int limit);
		
	    // 선호 카테고리 - 최신글
	    List<NoteSummaryDTO> recentNoteByMyCategory(@Param("categoryIdx") int categoryIdx, @Param("limit") int limit);

	    // 선호 카테고리 - 인기글
	    List<NoteSummaryDTO> popularNoteByMyCategory(@Param("categoryIdx") int categoryIdx, @Param("limit") int limit);
	    
	    // 전체 카테고리의 인기 유저 조회
		List<UserSummaryVO> findPopularUsers(int limit);
	    
		// 특정 카테고리의 인기 유저 조회
		List<UserSummaryVO> findPopularUsersByCategory(@Param("categoryIdx") int categoryIdx, @Param("limit") int limit);
}
