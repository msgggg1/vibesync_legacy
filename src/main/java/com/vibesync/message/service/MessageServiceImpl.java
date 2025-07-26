package com.vibesync.message.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.message.domain.MessageDTO;
import com.vibesync.message.domain.MessageListDTO;
import com.vibesync.message.mapper.MessageMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    
    @Override
    public List<MessageListDTO> getUnreadMessageList(int acIdx) {
        List<MessageListDTO> unreadMessageList = messageMapper.selectUnreadMessageList(acIdx);
        
        // 상대 시간 계산
        LocalDateTime today = LocalDateTime.now().withHour(0);
        DateTimeFormatter formatter;
        String time;
        
        for (MessageListDTO messageList : unreadMessageList) {
            MessageDTO message = messageList.getLatestMessage();
            if (message != null && message.getTime() != null) {
                LocalDateTime sentTime = message.getTime().toLocalDateTime();
                int days = (int) ChronoUnit.DAYS.between(sentTime, today);
                
                if (days == 0) { // 오늘
                    formatter = DateTimeFormatter.ofPattern("a h:mm");
                    time = sentTime.format(formatter); // 오전/오후 1:34
                } else if (days == 1) { // 어제
                    time = "어제";
                } else if (days > 1 && days < 365) { // 1일 초과, 1년 미만
                    formatter = DateTimeFormatter.ofPattern("M월 d일");
                    time = sentTime.format(formatter); // 6월 7일
                } else { // 1년 이상 전
                    time = days/365 + "년 전"; // 1년 전
                }
                
                message.setRelativeTime(time);
            }
        }
        
        return unreadMessageList;
    }
    
    @Override
    @Transactional
    public boolean updateChkMessage(int myIdx, List<Integer> msgIdxList) {
        try {
            int result = messageMapper.updateChkMessage(myIdx, msgIdxList);
            return result >= 1;
        } catch (Exception e) {
            log.error("메시지 읽음 처리 중 오류 발생", e);
            return false;
        }
    }
    
    @Override
    public List<MessageListDTO> getMessageListAll(int acIdx) {
        List<MessageListDTO> messageList = messageMapper.selectMessageListAll(acIdx);
        
        // 상대 시간 계산
        LocalDateTime today = LocalDateTime.now().withHour(0);
        DateTimeFormatter formatter;
        String time;
        
        for (MessageListDTO messageListDTO : messageList) {
            MessageDTO message = messageListDTO.getLatestMessage();
            if (message != null && message.getTime() != null) {
                LocalDateTime sentTime = message.getTime().toLocalDateTime();
                int days = (int) ChronoUnit.DAYS.between(sentTime, today);
                
                if (days == 0) { // 오늘
                    formatter = DateTimeFormatter.ofPattern("a h:mm");
                    time = sentTime.format(formatter); // 오전/오후 1:34
                } else if (days == 1) { // 어제
                    time = "어제";
                } else if (days > 1 && days < 365) { // 1일 초과, 1년 미만
                    formatter = DateTimeFormatter.ofPattern("M월 d일");
                    time = sentTime.format(formatter); // 6월 7일
                } else { // 1년 이상 전
                    time = days/365 + "년 전"; // 1년 전
                }
                
                message.setRelativeTime(time);
            }
        }
        
        return messageList;
    }
    
    @Override
    @Transactional
    public List<MessageDTO> getChatHistory(int myIdx, int otherIdx) {
        try {
            // 채팅 내역 조회
            List<MessageDTO> chatHistory = messageMapper.selectChatHistory(myIdx, otherIdx);
            
            if (chatHistory != null && !chatHistory.isEmpty()) {
                // 읽지 않은 메시지들의 msg_idx 추출
                List<Integer> msgIdxList = new ArrayList<>();
                for (MessageDTO message : chatHistory) {
                    msgIdxList.add(message.getMsgIdx());
                }
                
                // 읽음 처리
                messageMapper.updateChkMessage(myIdx, msgIdxList);
            }
            
            // 상대 시간 계산
            LocalDateTime today = LocalDateTime.now().withHour(0);
            DateTimeFormatter formatter;
            String time;
            
            for (MessageDTO message : chatHistory) {
                if (message.getTime() != null) {
                    LocalDateTime sentTime = message.getTime().toLocalDateTime();
                    int days = (int) ChronoUnit.DAYS.between(sentTime, today);
                    
                    if (days == 0) { // 오늘
                        formatter = DateTimeFormatter.ofPattern("a h:mm");
                        time = sentTime.format(formatter); // 오전/오후 1:34
                    } else if (days == 1) { // 어제
                        time = "어제";
                    } else if (days > 1 && days < 365) { // 1일 초과, 1년 미만
                        formatter = DateTimeFormatter.ofPattern("M월 d일");
                        time = sentTime.format(formatter); // 6월 7일
                    } else { // 1년 이상 전
                        time = days/365 + "년 전"; // 1년 전
                    }
                    
                    message.setRelativeTime(time);
                }
            }
            
            return chatHistory;
        } catch (Exception e) {
            log.error("채팅 내역 조회 중 오류 발생", e);
            return new ArrayList<>();
        }
    }
    
    @Override
    @Transactional
    public boolean sendMessage(int senderIdx, int receiverIdx, String text) {
        try {
            int result = messageMapper.insertMessage(senderIdx, receiverIdx, text);
            return result >= 1;
        } catch (Exception e) {
            log.error("메시지 전송 중 오류 발생", e);
            return false;
        }
    }
} 