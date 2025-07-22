package com.vibesync.mainpage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vibesync.mainpage.domain.NoteSummaryDTO;
import com.vibesync.mainpage.domain.UserSummaryVO;

@Mapper
public interface RecommendMapper {
		// 전체 카테고리 - 인기글
		Map<Integer, List<NoteSummaryDTO>> popularNoteByAllCategory(int limit);
		
	    // 선호 카테고리 - 최신글
	    List<NoteSummaryDTO> recentNoteByMyCategory(int categoryIdx, int limit);

	    // 선호 카테고리 - 인기글
	    List<NoteSummaryDTO> popularNoteByMyCategory(int categoryIdx, int limit);
	    
	    // 전체 카테고리의 인기 유저 조회
		List<UserSummaryVO> findPopularUsers(int limit);
	    
		// 특정 카테고리의 인기 유저 조회
		List<UserSummaryVO> findPopularUsersByCategory(int categoryIdx, int limit);
}
