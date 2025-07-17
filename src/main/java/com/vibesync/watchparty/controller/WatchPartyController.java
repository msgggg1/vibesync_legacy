package com.vibesync.watchparty.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vibesync.watchparty.domain.UserVO;
import com.vibesync.watchparty.domain.WatchPartyVO;
import com.vibesync.watchparty.mapper.WatchPartyMapper;



@Controller
@RequestMapping("/watch")
public class WatchPartyController {

    @Autowired
    private WatchPartyMapper wpMapper;

    // 1) watch.jsp 화면 출력
    @GetMapping("/{watchPartyIdx}")
    public String viewWatch(@PathVariable int watchPartyIdx,
                            Model model,
                            HttpSession session) {
        WatchPartyVO wp = wpMapper.selectOne(watchPartyIdx);
        model.addAttribute("wp", wp);

        UserVO user = (UserVO) session.getAttribute("userInfo");
        String nickname = (user != null) ? user.getNickname() : "익명";
        model.addAttribute("nickname", nickname);

        return "watch";  // /WEB-INF/views/watch.jsp
    }

    // 2) GetSyncStatusServlet → JSON 리턴 메서드
    @GetMapping("/getSyncStatus")
    @ResponseBody
    public Map<String, Object> getSyncStatus(@RequestParam int watchPartyIdx) {
        // 기존 GetSyncStatusServlet 로직을 그대로 이동
        // (예시: wa_sync 테이블에서 최신 play, timeline 조회)
        // 아래는 간단화한 예시 구현입니다.
        Map<String, Object> result = new HashMap<>();
        // 예: play = "PLAY" or "PAUSE"
        String play = wpMapper.selectLatestByUniqueFields("", "", 0) != null ? "PLAY" : "PAUSE";
        double timeline = 0.0;
        result.put("play", play);
        result.put("timeline", timeline);
        return result;
    }
}
