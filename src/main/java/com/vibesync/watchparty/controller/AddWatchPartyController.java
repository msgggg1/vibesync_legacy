package com.vibesync.watchparty.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.vibesync.watchparty.domain.WaSyncVO;
import com.vibesync.watchparty.domain.WatchPartyVO;
import com.vibesync.watchparty.mapper.WaSyncMapper;
import com.vibesync.watchparty.mapper.WatchPartyMapper;


@RestController
@RequestMapping("/watchParty")
public class AddWatchPartyController {

    private final WatchPartyMapper watchPartyMapper;
    private final WaSyncMapper waSyncMapper;

    @Autowired
    public AddWatchPartyController(WatchPartyMapper watchPartyMapper,
                                   WaSyncMapper waSyncMapper) {
        this.watchPartyMapper = watchPartyMapper;
        this.waSyncMapper = waSyncMapper;
    }

    /**
     * 새로운 WatchParty 생성 및 초기 Sync 레코드 삽입
     * 요청 예시 (application/json):
     * {
     *   "title": "Some Title",
     *   "video_id": "abc123",
     *   "host": 42
     * }
     */
    @PostMapping("/add")
    @Transactional
    public Map<String, Object> addWatchParty(@RequestBody Map<String, Object> request) {
        String title   = (String) request.get("title");
        String videoId = (String) request.get("video_id");
        Integer host   = (Integer) request.get("host");

        Map<String, Object> response = new HashMap<>();
        boolean success = false;

        // 중복 호스트 체크
        int existing = watchPartyMapper.checkExit(host);
        if (existing > 0) {
            response.put("success", false);
            response.put("error", "현재 Host 중인 영상이 존재합니다.");
            return response;
        }

        // 새 파티 삽입
        WatchPartyVO wp = new WatchPartyVO();
        wp.setTitle(title);
        wp.setVideoId(videoId);
        wp.setHost(host);
        int inserted = watchPartyMapper.insert(wp);

        if (inserted > 0) {
            // 방금 삽입된 레코드 조회
            WatchPartyVO insertedWp = watchPartyMapper.selectLatestByUniqueFields(title, videoId, host);
            if (insertedWp != null) {
                // 초기 Sync 상태 삽입
                WaSyncVO initialSync = new WaSyncVO();
                initialSync.setWatchPartyIdx(insertedWp.getWatchPartyIdx());
                initialSync.setTimeline(0.0);
                initialSync.setPlay("PAUSE");
                waSyncMapper.insert(initialSync);

                success = true;
            }
        }

        response.put("success", success);
        if (!success) {
            response.put("error", "DB 삽입 실패");
        }
        return response;
    }
}