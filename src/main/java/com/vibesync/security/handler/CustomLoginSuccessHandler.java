package com.vibesync.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Component("customLoginSuccessHandler")
@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{

	 @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	            Authentication authentication) throws IOException, ServletException {
	        
	        log.warn("====================================================");
	        log.warn("로그인 성공! Authentication 객체 내용을 확인합니다.");
	        log.warn("Principal 객체 타입: " + authentication.getPrincipal().getClass().getName());
	        log.warn("부여된 권한(Authorities): " + authentication.getAuthorities());
	        log.warn("====================================================");
	        
	        // 확인 후, 원래 목적지였던 메인 페이지로 리다이렉트
	        response.sendRedirect(request.getContextPath() + "/page/main");
	    }
}



