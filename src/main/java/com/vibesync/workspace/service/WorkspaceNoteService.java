package com.vibesync.workspace.service;

import java.util.List;

import com.vibesync.workspace.domain.NoteSummaryDTO;
import com.vibesync.workspace.domain.UserStatsBlockDTO;


public interface WorkspaceNoteService {
    // 노트 조회 관련 메서드들
    List<NoteSummaryDTO> getMyPostsPreview(int acIdx);
    List<NoteSummaryDTO> getAllMyPosts(int acIdx);
    List<NoteSummaryDTO> getLikedPostsPreview(int acIdx);
    List<NoteSummaryDTO> getAllLikedPosts(int acIdx);
    List<NoteSummaryDTO> getPostsByCategory(int categoryIdx, String sortType);
    UserStatsBlockDTO getUserStatsForChart(int acIdx, String period);
    List<NoteSummaryDTO> getMyNotes(int acIdx);
} 