package com.vibesync.workspace.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.workspace.domain.ChartDataDTO;
import com.vibesync.workspace.domain.DailyStatSummary;
import com.vibesync.workspace.domain.DailyStatsDTO;
import com.vibesync.workspace.domain.DatasetDTO;
import com.vibesync.workspace.domain.NoteSummaryDTO;
import com.vibesync.workspace.domain.UserStatsBlockDTO;
import com.vibesync.workspace.mapper.WorkspaceNoteMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class WorkspaceNoteServiceImpl implements WorkspaceNoteService {
    
    @Autowired
    private WorkspaceNoteMapper noteMapper;

    // --- Workspace [내가 작성한 글] 관련 서비스 메소드 ---
    //사용자가 작성한 글 인기순. (위젯 미리보기용)
    @Override
    @Transactional(readOnly = true)
    public List<NoteSummaryDTO> getMyPostsPreview(int acIdx) {
        log.info("내가 작성한 글 미리보기 조회. 사용자 ID: " + acIdx);
        return noteMapper.findMyPostsByPopularity(acIdx);
    }

    //사용자가 작성한 글 전체를 인기순 (모달용)
    @Override
    @Transactional(readOnly = true)
    public List<NoteSummaryDTO> getAllMyPosts(int acIdx) {
        log.info("내가 작성한 글 전체 조회. 사용자 ID: " + acIdx);
        return noteMapper.findAllMyPostsByPopularity(acIdx);
    }
    
    // --- [좋아요한 글] 관련 서비스 메소드 ---
    // 좋아요한 글 최신순 7개 (위젯 미리보기용)
    @Override
    @Transactional(readOnly = true)
    public List<NoteSummaryDTO> getLikedPostsPreview(int acIdx) {
        log.info("좋아요한 글 미리보기 조회. 사용자 ID: " + acIdx);
        return noteMapper.findLikedPostsByRecent(acIdx);
    }

    //좋아요한 글 전체를 최신순
    @Override
    @Transactional(readOnly = true)
    public List<NoteSummaryDTO> getAllLikedPosts(int acIdx) {
        log.info("좋아요한 글 전체 조회. 사용자 ID: " + acIdx);
        return noteMapper.findAllLikedPostsByRecent(acIdx);
    }
    
    // 카테고리별 인기/최신글 조회
    @Override
    @Transactional(readOnly = true)
    public List<NoteSummaryDTO> getPostsByCategory(int categoryIdx, String sortType) {
        log.info("카테고리별 게시글 조회. 카테고리 ID: " + categoryIdx + ", 정렬: " + sortType);
        
        List<NoteSummaryDTO> postsByCategory = new ArrayList<>();
        
        if (sortType == null) {
            sortType = "";
        }
        
        if (sortType.equals("popular")) {
            postsByCategory = noteMapper.popularNoteByMyCategory(categoryIdx, 5);
        } else if (sortType.equals("latest")) {
            postsByCategory = noteMapper.recentNoteByMyCategory(categoryIdx, 5);
        }
        
        return postsByCategory;
    }

    // 내 활동 통계 (신버전) : 통계 범위 조정 가능
    @Override
    @Transactional(readOnly = true)
    public UserStatsBlockDTO getUserStatsForChart(int acIdx, String period) {
        log.info("사용자 통계 조회. 사용자 ID: " + acIdx + ", 기간: " + period);
        
        if (period == null || period.isEmpty()) {
            period = "daily";
        }
        
        UserStatsBlockDTO userStatsBlockDTO = null;
        
        try {
            Map<String, DailyStatSummary> summaryMap = new LinkedHashMap<>();
            List<DailyStatsDTO> postData, viewData, likeData;
            String title, labelFormat, xLabelType;

            // 기간(period)에 따라 분기 처리
            switch (period) {
            case "weekly":
                title = "최근 4주 활동 통계 (주별)";
                labelFormat = "yyyy-MM-dd";
                xLabelType = "week";
                int weeks = 4;
                LocalDate mondayOfThisWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                for (int i = 0; i < weeks; i++) {
                    summaryMap.put(mondayOfThisWeek.minusWeeks(i).format(DateTimeFormatter.ofPattern(labelFormat)), new DailyStatSummary());
                }
                postData = noteMapper.getWeeklyPostCounts(acIdx, weeks);
                viewData = noteMapper.getWeeklyViewCounts(acIdx, weeks);
                likeData = noteMapper.getWeeklyLikeCountsForUserPosts(acIdx, weeks);
                break;

            case "monthly":
                title = "최근 1년 활동 통계 (월별)";
                labelFormat = "yyyy-MM";
                xLabelType = "month";
                int months = 12;
                YearMonth currentMonth = YearMonth.now();
                for (int i = 0; i < months; i++) {
                    summaryMap.put(currentMonth.minusMonths(i).format(DateTimeFormatter.ofPattern(labelFormat)), new DailyStatSummary());
                }
                postData = noteMapper.getMonthlyPostCounts(acIdx, months);
                viewData = noteMapper.getMonthlyViewCounts(acIdx, months);
                likeData = noteMapper.getMonthlyLikeCountsForUserPosts(acIdx, months);
                break;

            case "yearly":
                title = "최근 5년 활동 통계 (연도별)";
                xLabelType = "year";
                int years = 5;
                LocalDate currentYear = LocalDate.now();
                for (int i = 0; i < years; i++) {
                    summaryMap.put(String.valueOf(currentYear.minusYears(i).getYear()), new DailyStatSummary());
                }
                postData = noteMapper.getYearlyPostCounts(acIdx, years);
                viewData = noteMapper.getYearlyViewCounts(acIdx, years);
                likeData = noteMapper.getYearlyLikeCountsForUserPosts(acIdx, years);
                break;
                
            case "daily":
            default:
                title = "최근 7일 활동 통계 (일별)";
                labelFormat = "yyyy-MM-dd (E)";
                xLabelType = "day";
                int days = 7;
                LocalDate today = LocalDate.now();
                for (int i = 0; i < days; i++) {
                    summaryMap.put(today.minusDays(i).format(DateTimeFormatter.ofPattern(labelFormat)), new DailyStatSummary());
                }
                postData = noteMapper.getDailyPostCounts(acIdx, days);
                viewData = noteMapper.getDailyViewCounts(acIdx, days);
                likeData = noteMapper.getDailyLikeCountsForUserPosts(acIdx, days);
                break;
            }

            // 가져온 통계 데이터를 Map에 채우기
            postData.forEach(stat -> summaryMap.computeIfPresent(stat.getStatDate(), (k, v) -> { v.setPostCount(stat.getCount()); return v; }));
            viewData.forEach(stat -> summaryMap.computeIfPresent(stat.getStatDate(), (k, v) -> { v.setViewCount(stat.getCount()); return v; }));
            likeData.forEach(stat -> summaryMap.computeIfPresent(stat.getStatDate(), (k, v) -> { v.setLikeCount(stat.getCount()); return v; }));

            // 통합된 Map 데이터를 최종 DTO 형태로 변환
            List<String> labels = new ArrayList<>();
            List<Long> postCounts = new ArrayList<>();
            List<Long> viewCounts = new ArrayList<>();
            List<Long> likeCounts = new ArrayList<>();
            
            // 날짜 순서 오름차순으로 정렬
            new ArrayList<>(summaryMap.keySet()).stream().sorted().forEach(dateKey -> {
                DailyStatSummary summary = summaryMap.get(dateKey);
                
                String label = "";
                switch(xLabelType) {
                case "day":
                    String month = dateKey.substring((dateKey.indexOf("-0") == -1 ? 5 : 6), dateKey.indexOf('-', 6));
                    int monthEnd = dateKey.indexOf('-', 6);
                    int dateHas0 = dateKey.indexOf("-0", monthEnd);
                    String date = dateKey.substring((dateHas0 == -1 ? monthEnd + 1 : dateHas0 + 2), dateKey.indexOf('(')-1);
                    String day = dateKey.substring(dateKey.indexOf("("));
                    label =  String.format("%s월 %s일 %s", month, date, day);
                    break;
                
                case "week": 
                    label = getWeekOfMonthLabel(dateKey);
                    break;
                    
                case "month": label = dateKey.substring(2, 4) + "년 " + (dateKey.indexOf("-0") == -1 ? dateKey.substring(5) : dateKey.substring(6)) + "월" ; break;
                case "year": label = dateKey + "년"; break;
                }
                labels.add(label);
                
                postCounts.add(summary.getPostCount());
                viewCounts.add(summary.getViewCount());
                likeCounts.add(summary.getLikeCount());
            });

            // 최종 DTO 조립
            DatasetDTO postDataset = DatasetDTO.builder().label("게시글").data(postCounts).build();
            DatasetDTO viewDataset = DatasetDTO.builder().label("조회수").data(viewCounts).build();
            DatasetDTO likeDataset = DatasetDTO.builder().label("좋아요").data(likeCounts).build();

            ChartDataDTO chartData = ChartDataDTO.builder()
                .labels(labels)
                .datasets(List.of(postDataset, viewDataset, likeDataset))
                .build();
            
            userStatsBlockDTO = UserStatsBlockDTO.builder()
                .title(title)
                .chartData(chartData)
                .period(period)
                .build();

        } catch (Exception e) {
            log.error("사용자 통계 조회 중 오류 발생: " + e.getMessage());
        }

        return userStatsBlockDTO;
    }
    
    // 몇째주인지 반환해주는 메서드
    private String getWeekOfMonthLabel(String yyyyMmDd) {
        if (yyyyMmDd == null || yyyyMmDd.length() < 10) return "";

        LocalDate date = LocalDate.parse(yyyyMmDd);
        int month = date.getMonthValue();
        TemporalField wof = WeekFields.of(Locale.KOREA).weekOfMonth();
        int weekOfMonth = date.get(wof);

        String weekOrdinal;
        switch (weekOfMonth) {
            case 1: weekOrdinal = "첫째주"; break;
            case 2: weekOrdinal = "둘째주"; break;
            case 3: weekOrdinal = "셋째주"; break;
            case 4: weekOrdinal = "넷째주"; break;
            case 5: weekOrdinal = "다섯째주"; break;
            default: weekOrdinal = String.valueOf(weekOfMonth) + "주차"; break;
        }
        
        return String.format("%d월 %s", month, weekOrdinal);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<NoteSummaryDTO> getMyNotes(int acIdx) {
        log.info("내 노트 조회. 사용자 ID: " + acIdx);
        return noteMapper.findMyPostsByPopularity(acIdx);
    }
} 