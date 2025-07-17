package com.vibesync.watchparty.mapper;

import org.apache.ibatis.annotations.Param;

import com.vibesync.watchparty.domain.WaSyncVO;

public interface WaSyncMapper {

    /**
     * 특정 WatchParty의 최근 sync 정보 가져오기 (마지막 재생 상태)
     */
    WaSyncVO selectLatestByWatchParty(@Param("watchPartyIdx") int watchPartyIdx);

    /**
     * 실시간 재생 정보 삽입
     */
    int insert(WaSyncVO sync);

    /**
     * WatchParty 별로 upsert 처리
     */
    int upsertByWatchParty(WaSyncVO sync);
}