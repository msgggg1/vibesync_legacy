package com.vibesync.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

//403 접근 금지 에러를 다양한 처리를  직접하기 위한 클래스 (객체)
@Component("customAccessDeniedHandler")
@Log4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(
			  HttpServletRequest request
			, HttpServletResponse response,
			AccessDeniedException accessDeniedException
			) throws IOException, ServletException {
		 
		log.error("👌👌👌 Access Denied Handler");
		log.error("👌👌👌 Redirect...");
		// 개발자 직접 하고자 하는 다양한 처리  코딩.
		response.sendRedirect("/common/accessError.htm");
	}

}






