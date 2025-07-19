package com.vibesync.security.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component("customLogoutSuccessHandler")
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        
        // 자동 로그인 쿠키 삭제
        Cookie autoLoginCookie = new Cookie("autoLoginUserEmail", null);
        autoLoginCookie.setMaxAge(0);
        autoLoginCookie.setPath("/"); // ✨ 중요: 생성 시와 동일한 경로를 명시
        response.addCookie(autoLoginCookie);

        // 사용자 인덱스 쿠키 삭제
        Cookie userIdxCookie = new Cookie("login_user_idx", null);
        userIdxCookie.setMaxAge(0);
        userIdxCookie.setPath("/"); // ✨ 중요: 생성 시와 동일한 경로를 명시
        response.addCookie(userIdxCookie);

        // 로그인 페이지로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/member/login?from=logout");
    }
}