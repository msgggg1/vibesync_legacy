package com.vibesync.common.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
		
		MimeMessage message = mailSender.createMimeMessage();
		try {
            // MimeMessageHelper를 사용하면 HTML, 첨부파일 등을 쉽게 처리 가능
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // true: HTML 형식으로 전송
            
            mailSender.send(message);

        } catch (MessagingException e) {
            // 실제 운영이라면 Log.error() 등으로 예외를 기록해야 함
            throw new RuntimeException("메일 발송 중 오류가 발생했습니다.", e);
        }
		
	}

	private String createEmailBody(String resetLink) {
		 return "<html><body>" +
	               "<h2>VibeSync Password Reset</h2>" +
	               "<p>To reset your password, please click the button below.</p>" +
	               "<a href='" + resetLink + "' style='background-color:#5DAED7; color:white; padding:14px 25px; text-align:center; text-decoration:none; display:inline-block; border-radius:4px;'>Reset Password</a>" +
	               "<p>This link is valid for 1 hour.</p>" +
	               "<p>If you did not request a password reset, please ignore this email.</p>" +
	               "</body></html>";
	}

}
