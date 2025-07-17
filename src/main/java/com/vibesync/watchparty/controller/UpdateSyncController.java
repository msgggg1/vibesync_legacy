package com.vibesync.watchparty.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vibesync.watchparty.domain.WaSyncVO;
import com.vibesync.watchparty.mapper.WaSyncMapper;


@RestController
@RequestMapping("/watch")
public class UpdateSyncController {

    private final WaSyncMapper waSyncMapper;

    @Autowired
    public UpdateSyncController(WaSyncMapper waSyncMapper) {
        this.waSyncMapper = waSyncMapper;
    }

    /**
     * 실시간 재생 정보 삽입
     * URL: POST /watch/updateSync
     * 요청 JSON 예시:
     * {
     *   "watchPartyIdx": 123,
     *   "play": "PLAY",
     *   "timeline": 45.7
     * }
     */
    @PostMapping("/updateSync")
    public Map<String, Object> updateSync(@RequestBody Map<String, Object> request) {
        Integer watchPartyIdx = (Integer) request.get("watchPartyIdx");
        String play           = (String) request.get("play");
        Double timeline       = ((Number) request.get("timeline")).doubleValue();

        WaSyncVO sync = new WaSyncVO();
        sync.setWatchPartyIdx(watchPartyIdx);
        sync.setPlay(play);
        sync.setTimeline(timeline);

        int inserted = waSyncMapper.insert(sync);

        Map<String, Object> response = new HashMap<>();
        if (inserted > 0) {
            response.put("success", true);
        } else {
            response.put("success", false);
            response.put("error", "wa_sync INSERT 실패");
        }
        return response;
    }
}
