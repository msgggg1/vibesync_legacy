package com.vibesync.mainpage.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibesync.mainpage.domain.MainPageDTO;
import com.vibesync.mainpage.domain.NoteSummaryDTO;
import com.vibesync.mainpage.domain.UserSummaryVO;
import com.vibesync.mainpage.mapper.RecommendMapper;

@Service
public class MainPageServiceImpl implements MainPageService{

	@Autowired
	private RecommendMapper recommendMapper;

	@Override
	public MainPageDTO loadMainPage(int preferredCategory) {
		System.out.println("> MainPageServiceImpl.loadMainPage() - 호출됨");

		MainPageDTO mainPageDTO = null;

		List<NoteSummaryDTO> latestNotes = null; // 선호 카테고리 - 최신글
		List<NoteSummaryDTO> popularNotesByMyCategory = null; // 선호 카테고리 - 인기글
		List<UserSummaryVO> popularUsers = null; // 선호 카테고리 - 인기유저
		Map<Integer, List<NoteSummaryDTO>> popularNotesNotByMyCategory = null; // 비선호 카테고리 인기글
		List<NoteSummaryDTO> flatList = null;
		// 전체 카테고리 인기글
		Map<Integer, List<NoteSummaryDTO>> popularNotes = null;

		flatList = recommendMapper.popularNoteByAllCategory(5);
		
		 popularNotesNotByMyCategory = new LinkedHashMap<>();
	        for (NoteSummaryDTO note : flatList) {
	            Integer categoryId = note.getCategoryIdx();
	            if (categoryId != null) { 
	            	popularNotesNotByMyCategory.computeIfAbsent(categoryId, k -> new ArrayList<>()).add(note);
	            }
	        }
		latestNotes = recommendMapper.recentNoteByMyCategory(preferredCategory, 5);
		popularUsers = recommendMapper.findPopularUsersByCategory(preferredCategory, 5);
		popularNotesByMyCategory = popularNotesNotByMyCategory.remove(preferredCategory);

		mainPageDTO = MainPageDTO.builder()
				.latestNotes(latestNotes)
				.popularNotes(popularNotesByMyCategory)
				.popularUsers(popularUsers)
				.popularNotesNotByMyCategory(popularNotesNotByMyCategory)
				.build();

		return mainPageDTO;
	}

}
