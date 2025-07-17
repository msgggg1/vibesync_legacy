package com.vibesync.watchparty.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vibesync.watchparty.domain.WaSyncVO;
import com.vibesync.watchparty.mapper.WaSyncMapper;


@RestController
@RequestMapping("/watch")
public class SyncStatusController {

    private final WaSyncMapper waSyncMapper;

    @Autowired
    public SyncStatusController(WaSyncMapper waSyncMapper) {
        this.waSyncMapper = waSyncMapper;
    }

    /**
     * 클라이언트 초기 동기화 상태 조회
     * URL: GET /watch/getSyncStatus?watchPartyIdx={wpIdx}
     */
    @GetMapping("/getSyncStatus")
    public Map<String, Object> getSyncStatus(@RequestParam("watchPartyIdx") int wpIdx) {
        WaSyncVO lastSync = waSyncMapper.selectLatestByWatchParty(wpIdx);

        Map<String, Object> resp = new HashMap<>();
        if (lastSync != null) {
            resp.put("play", lastSync.getPlay());
            resp.put("timeline", lastSync.getTimeline());
        } else {
            resp.put("play", "PAUSE");
            resp.put("timeline", 0.0);
        }
        return resp;
    }
}