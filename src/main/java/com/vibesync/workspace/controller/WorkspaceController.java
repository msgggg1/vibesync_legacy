package com.vibesync.workspace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibesync.common.annotation.AuthenticatedUserPages;
import com.vibesync.security.domain.CustomUser;
import com.vibesync.workspace.domain.BlockDTO;
import com.vibesync.workspace.domain.UserStatsBlockDTO;
import com.vibesync.workspace.service.BlockService;
import com.vibesync.message.domain.MessageListDTO;
import com.vibesync.message.service.MessageService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/page/workspace")
@AuthenticatedUserPages
@Log4j
public class WorkspaceController {

    @Autowired
    private BlockService blockService;
    
    @Autowired
    private MessageService messageService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public String workspace(@AuthenticationPrincipal CustomUser user, Model model) {
        try {
            // 사용자의 블록 목록 조회 (기본 기간은 'weekly'로 설정)
            List<BlockDTO> blocks = blockService.getBlocksForUser(user.getAcIdx(), "weekly");
            
            // 안읽은 메시지 목록 조회
            List<MessageListDTO> unreadMessages = messageService.getUnreadMessageList(user.getAcIdx());
            
            // UserStats 블록의 경우 차트 데이터를 JSON 문자열로 변환
            for (BlockDTO block : blocks) {
                if (block instanceof UserStatsBlockDTO) {
                    UserStatsBlockDTO userStatsBlock = (UserStatsBlockDTO) block;
                    try {
                        String chartDataJson = objectMapper.writeValueAsString(userStatsBlock.getChartData());
                        userStatsBlock.setChartDataJson(chartDataJson);
                    } catch (Exception e) {
                        log.error("차트 데이터 JSON 변환 중 오류 발생", e);
                        userStatsBlock.setChartDataJson("{}");
                    }
                }
            }
            
            // Model에 데이터 설정
            model.addAttribute("workspaceData", new java.util.HashMap<String, Object>() {{
                put("blocks", blocks);
                put("unreadMessages", unreadMessages);
            }});
            
            log.info("로드된 블록 개수: " + blocks.size());
            
        } catch (Exception e) {
            log.error("워크스페이스 데이터 로드 중 오류 발생", e);
            model.addAttribute("workspaceData", new java.util.HashMap<String, Object>() {{
                put("blocks", new java.util.ArrayList<>());
            }});
        }
        
        return "page/workspace";
    }
} 