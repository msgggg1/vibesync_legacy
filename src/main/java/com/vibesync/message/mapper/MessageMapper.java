package com.vibesync.message.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vibesync.message.domain.MessageDTO;
import com.vibesync.message.domain.MessageListDTO;

@Mapper
public interface MessageMapper {
    
    // 안읽은 메시지 목록 조회
    List<MessageListDTO> selectUnreadMessageList(@Param("acIdx") int acIdx);
    
    // 전체 메시지 목록 조회
    List<MessageListDTO> selectMessageListAll(@Param("acIdx") int acIdx);
    
    // 채팅방 채팅 내역 열람
    List<MessageDTO> selectChatHistory(@Param("myIdx") int myIdx, @Param("otherIdx") int otherIdx);
    
    // 메시지 읽음 처리
    int updateChkMessage(@Param("myIdx") int myIdx, @Param("msgIdxList") List<Integer> msgIdxList);
    
    // 메시지 전송
    int insertMessage(@Param("senderIdx") int senderIdx, 
                     @Param("receiverIdx") int receiverIdx, 
                     @Param("text") String text);
} 