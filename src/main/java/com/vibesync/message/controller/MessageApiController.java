package com.vibesync.message.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vibesync.message.domain.MessageDTO;
import com.vibesync.message.domain.MessageListDTO;
import com.vibesync.message.service.MessageService;
import com.vibesync.security.domain.CustomUser;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/api/messages")
@Log4j
public class MessageApiController {

    @Autowired
    private MessageService messageService;
    
    // 안읽은 메시지 목록 조회
    @GetMapping("/unread")
    public ResponseEntity<List<MessageListDTO>> getUnreadMessages(
            @AuthenticationPrincipal CustomUser user) {
        
        try {
            List<MessageListDTO> unreadMessages = messageService.getUnreadMessageList(user.getAcIdx());
            return ResponseEntity.ok(unreadMessages);
        } catch (Exception e) {
            log.error("안읽은 메시지 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // 전체 메시지 목록 조회
    @GetMapping("/all")
    public ResponseEntity<List<MessageListDTO>> getAllMessages(
            @AuthenticationPrincipal CustomUser user) {
        
        try {
            List<MessageListDTO> allMessages = messageService.getMessageListAll(user.getAcIdx());
            return ResponseEntity.ok(allMessages);
        } catch (Exception e) {
            log.error("전체 메시지 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // 채팅방 채팅 내역 열람
    @GetMapping("/chat")
    public ResponseEntity<List<MessageDTO>> getChatHistory(
            @AuthenticationPrincipal CustomUser user,
            @RequestParam int otherIdx) {
        
        try {
            List<MessageDTO> chatHistory = messageService.getChatHistory(user.getAcIdx(), otherIdx);
            return ResponseEntity.ok(chatHistory);
        } catch (Exception e) {
            log.error("채팅 내역 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // 메시지 읽음 처리
    @PostMapping("/read")
    public ResponseEntity<Boolean> markAsRead(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody List<Integer> msgIdxList) {
        
        try {
            boolean result = messageService.updateChkMessage(user.getAcIdx(), msgIdxList);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("메시지 읽음 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // 메시지 전송
    @PostMapping("/send")
    public ResponseEntity<Boolean> sendMessage(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody MessageSendRequest request) {
        
        try {
            boolean result = messageService.sendMessage(
                user.getAcIdx(), 
                request.getReceiverIdx(), 
                request.getText()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("메시지 전송 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // 메시지 전송 요청 DTO
    public static class MessageSendRequest {
        private int receiverIdx;
        private String text;
        
        public int getReceiverIdx() { return receiverIdx; }
        public void setReceiverIdx(int receiverIdx) { this.receiverIdx = receiverIdx; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }
} 