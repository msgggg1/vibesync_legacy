package com.vibesync.watchparty.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vibesync.watchparty.domain.WaCommentVO;

public interface WaCommentMapper {

    /**
     * 특정 WatchParty의 전체 댓글 목록 조회 (타임라인 오름차순)
     */
    List<WaCommentVO> selectByWatchParty(@Param("watchPartyIdx") int watchPartyIdx);

    /**
     * 실시간 채팅(댓글) 삽입
     */
    int insert(WaCommentVO comment);
}