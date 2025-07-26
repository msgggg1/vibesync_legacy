package com.vibesync.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
	        log.warn("Remember Me 요청 여부: " + request.getParameter("remember-me"));
	        log.warn("Remember Email 요청 여부: " + request.getParameter("RememEmail"));
	        log.warn("====================================================");
	        
	        // 이메일 기억하기 처리
	        String rememberEmail = request.getParameter("RememEmail");
	        String userEmail = authentication.getName(); // 로그인한 사용자 이메일
	        
	        if ("on".equals(rememberEmail)) {
	            // 이메일 기억하기 쿠키 설정
	            Cookie emailCookie = new Cookie("rememberedEmail", userEmail);
	            emailCookie.setPath("/");
	            emailCookie.setMaxAge(60 * 60 * 24 * 30); // 30일
	            response.addCookie(emailCookie);
	            log.warn("이메일 기억하기 쿠키 설정: " + userEmail);
	        } else {
	            // 이메일 기억하기 쿠키 삭제
	            Cookie emailCookie = new Cookie("rememberedEmail", "");
	            emailCookie.setPath("/");
	            emailCookie.setMaxAge(0);
	            response.addCookie(emailCookie);
	            log.warn("이메일 기억하기 쿠키 삭제");
	        }
	        
	        // Spring Security의 기본 리다이렉트 로직을 사용하여 Remember Me 쿠키가 정상 생성되도록 함
	        String targetUrl = request.getContextPath() + "/page/main";
	        response.sendRedirect(targetUrl);
	    }
} 