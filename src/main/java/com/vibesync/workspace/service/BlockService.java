package com.vibesync.workspace.service;

import java.util.List;
import java.util.Map;
import com.vibesync.workspace.domain.BlockDTO;

public interface BlockService {
    
    // 유저별로 개인화된 추가블록 불러오기
    List<BlockDTO> getBlocksForUser(int acIdx, String period);
    
    // 특정 블록 ID에 대한 DTO만 생성해서 반환하는 메서드 (특정 블록 새로고침용)
    BlockDTO getBlockContentAsDto(int acIdx, int blockId, String period);
    
    // 블록 추가
    int addBlock(int acIdx, String blockType, String config);
    
    // 블록 삭제
    boolean removeBlock(int acIdx, int blockId);
    
    // 블록 순서 변경
    boolean changeBlockOrder(int acIdx, List<Map<String, Object>> orders);
    
    // 내 노트 데이터 조회
    Map<String, Object> getMyNoteData(int acIdx);
    
    // 고정 블록 데이터 조회
    Map<String, Object> getFixedBlockData(int acIdx, String blockType);
    

} 