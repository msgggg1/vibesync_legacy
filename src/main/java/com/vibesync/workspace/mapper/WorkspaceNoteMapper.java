package com.vibesync.workspace.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.vibesync.workspace.domain.NoteSummaryDTO;
import com.vibesync.workspace.domain.DailyStatsDTO;

@Mapper
public interface WorkspaceNoteMapper {
    // 노트 조회 관련 메서드들
    List<NoteSummaryDTO> findMyPostsByPopularity(int acIdx);
    List<NoteSummaryDTO> findAllMyPostsByPopularity(int acIdx);
    List<NoteSummaryDTO> findLikedPostsByRecent(int acIdx);
    List<NoteSummaryDTO> findAllLikedPostsByRecent(int acIdx);
    List<NoteSummaryDTO> popularNoteByMyCategory(int categoryIdx, int limit);
    List<NoteSummaryDTO> recentNoteByMyCategory(int categoryIdx, int limit);
    
    // 노트 통계 관련 메서드들
    List<DailyStatsDTO> getDailyPostCounts(int acIdx, int days);
    List<DailyStatsDTO> getDailyViewCounts(int acIdx, int days);
    List<DailyStatsDTO> getWeeklyPostCounts(int acIdx, int weeks);
    List<DailyStatsDTO> getWeeklyViewCounts(int acIdx, int weeks);
    List<DailyStatsDTO> getMonthlyPostCounts(int acIdx, int months);
    List<DailyStatsDTO> getMonthlyViewCounts(int acIdx, int months);
    List<DailyStatsDTO> getYearlyPostCounts(int acIdx, int years);
    List<DailyStatsDTO> getYearlyViewCounts(int acIdx, int years);
    
    // 좋아요 통계 관련 메서드들 (노트에 대한 좋아요)
    List<DailyStatsDTO> getDailyLikeCountsForUserPosts(int acIdx, int days);
    List<DailyStatsDTO> getWeeklyLikeCountsForUserPosts(int acIdx, int weeks);
    List<DailyStatsDTO> getMonthlyLikeCountsForUserPosts(int acIdx, int months);
    List<DailyStatsDTO> getYearlyLikeCountsForUserPosts(int acIdx, int years);
} 