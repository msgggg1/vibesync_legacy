package com.vibesync.workspace.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.vibesync.workspace.domain.NoteSummaryDTO;
import com.vibesync.workspace.domain.DailyStatsDTO;

@Mapper
public interface WorkspaceNoteMapper {
    // 노트 조회 관련 메서드들
    List<NoteSummaryDTO> findMyPostsByPopularity(@Param("acIdx") int acIdx);
    List<NoteSummaryDTO> findAllMyPostsByPopularity(@Param("acIdx") int acIdx);
    List<NoteSummaryDTO> findLikedPostsByRecent(@Param("acIdx") int acIdx);
    List<NoteSummaryDTO> findAllLikedPostsByRecent(@Param("acIdx") int acIdx);
    List<NoteSummaryDTO> popularNoteByMyCategory(@Param("categoryIdx") int categoryIdx, @Param("limit") int limit);
    List<NoteSummaryDTO> recentNoteByMyCategory(@Param("categoryIdx") int categoryIdx, @Param("limit") int limit);
    
    // 노트 통계 관련 메서드들
    List<DailyStatsDTO> getDailyPostCounts(@Param("acIdx") int acIdx, @Param("days") int days);
    List<DailyStatsDTO> getDailyViewCounts(@Param("acIdx") int acIdx, @Param("days") int days);
    List<DailyStatsDTO> getWeeklyPostCounts(@Param("acIdx") int acIdx, @Param("weeks") int weeks);
    List<DailyStatsDTO> getWeeklyViewCounts(@Param("acIdx") int acIdx, @Param("weeks") int weeks);
    List<DailyStatsDTO> getMonthlyPostCounts(@Param("acIdx") int acIdx, @Param("months") int months);
    List<DailyStatsDTO> getMonthlyViewCounts(@Param("acIdx") int acIdx, @Param("months") int months);
    List<DailyStatsDTO> getYearlyPostCounts(@Param("acIdx") int acIdx, @Param("years") int years);
    List<DailyStatsDTO> getYearlyViewCounts(@Param("acIdx") int acIdx, @Param("years") int years);
    
    // 좋아요 통계 관련 메서드들 (노트에 대한 좋아요)
    List<DailyStatsDTO> getDailyLikeCountsForUserPosts(@Param("acIdx") int acIdx, @Param("days") int days);
    List<DailyStatsDTO> getWeeklyLikeCountsForUserPosts(@Param("acIdx") int acIdx, @Param("weeks") int weeks);
    List<DailyStatsDTO> getMonthlyLikeCountsForUserPosts(@Param("acIdx") int acIdx, @Param("months") int months);
    List<DailyStatsDTO> getYearlyLikeCountsForUserPosts(@Param("acIdx") int acIdx, @Param("years") int years);
} 