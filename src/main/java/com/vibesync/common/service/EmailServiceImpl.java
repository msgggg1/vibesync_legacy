package com.vibesync.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	@Async // 이메일 발송에 시간이 걸릴 수 있으므로 비동기 처리
	public void sendEmail(String toEmail, String resetLink) {
		String subject = "[VibeSync] Password Reset Request";
		String body = createEmailBody(resetLink);
		
	}

	private String createEmailBody(String resetLink) {
		// TODO Auto-generated method stub
		return null;
	}

}
