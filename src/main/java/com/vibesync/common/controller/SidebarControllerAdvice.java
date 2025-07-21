package com.vibesync.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.vibesync.common.annotation.AuthenticatedUserPages;
import com.vibesync.security.domain.CustomUser;
import com.vibesync.sidebar.domain.SidebarDTO;
import com.vibesync.sidebar.service.SidebarService;

import lombok.extern.log4j.Log4j;

@ControllerAdvice(annotations = AuthenticatedUserPages.class) // 전역 컨트롤러 설정 클래스
@Log4j
public class SidebarControllerAdvice {

    @Autowired
    private SidebarService sidebarService;

    // "sidebar"라는 이름으로 모든 Model에 이 메서드의 반환값을 추가
    @ModelAttribute("sidebar") 
    public SidebarDTO addSidebarData() {
        
    	// Spring Security의 전역 저장소에서 현재 인증 정보를 직접 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나, Principal이 CustomUser 타입이 아니면(예: 익명 사용자) null을 반환합니다.
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUser)) {
            return null;
        }

        // 인증된 사용자의 Principal(CustomUser) 객체를 직접 형변환하여 가져옵니다.
        CustomUser user = (CustomUser) authentication.getPrincipal();
        
        log.info("SecurityContextHolder에서 직접 가져온 사용자: " + user.getNickname());
        
        return sidebarService.loadSidebar(user);
    }
}