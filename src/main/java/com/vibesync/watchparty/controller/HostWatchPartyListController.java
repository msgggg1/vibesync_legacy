package com.vibesync.watchparty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vibesync.watchparty.domain.WatchPartyVO;
import com.vibesync.watchparty.mapper.WatchPartyMapper;



@RestController
@RequestMapping("/watch")
public class HostWatchPartyListController {

    private final WatchPartyMapper watchPartyMapper;

    @Autowired
    public HostWatchPartyListController(WatchPartyMapper watchPartyMapper) {
        this.watchPartyMapper = watchPartyMapper;
    }

    /**
     * 호스트(로그인 사용자)의 WatchParty 목록 반환
     * GET /watch/hostList
     * 
     * @param loginUserIdx 쿠키 "login_user_idx" 값
     * @return 로그인된 호스트의 WatchParty 목록, 비로그인 시 빈 배열
     */
    @GetMapping("/hostList")
    public List<WatchPartyVO> hostList(
            @CookieValue(value = "login_user_idx", required = false) Integer loginUserIdx) {

        if (loginUserIdx == null || loginUserIdx == 0) {
            // 로그인되지 않았거나 쿠키가 없으면 빈 리스트 반환
            return List.of();
        }

        // 해당 호스트의 파티 목록 조회
        return watchPartyMapper.selectByHost(loginUserIdx);
    }
}
