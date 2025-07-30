package com.vibesync.message.service;

import java.util.List;

import com.vibesync.message.domain.MessageDTO;
import com.vibesync.message.domain.MessageListDTO;

public interface MessageService {
    
    // 안읽은 메시지 목록 조회
    List<MessageListDTO> getUnreadMessageList(int acIdx);
    
    // 전체 메시지 목록 조회
    List<MessageListDTO> getMessageListAll(int acIdx);
    
    // 채팅방 채팅 내역 열람
    List<MessageDTO> getChatHistory(int myIdx, int otherIdx);
    
    // 메시지 읽음 처리
    boolean updateChkMessage(int myIdx, List<Integer> msgIdxList);
    
    // 메시지 전송
    boolean sendMessage(int senderIdx, int receiverIdx, String text);
} 