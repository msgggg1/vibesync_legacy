package com.vibesync.workspace.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.workspace.domain.BlockDTO;
import com.vibesync.workspace.domain.BlockVO;
import com.vibesync.workspace.domain.CategoryPostsBlockDTO;
import com.vibesync.workspace.domain.NoteSummaryDTO;
import com.vibesync.workspace.domain.UserStatsBlockDTO;
import com.vibesync.workspace.domain.WatchPartyBlockDTO;
import com.vibesync.workspace.domain.WatchPartyDTO;
import com.vibesync.workspace.mapper.BlockMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BlockServiceImpl implements BlockService {
    
    @Autowired
    private BlockMapper blockMapper;
    
    @Autowired
    private WorkspaceNoteService noteService;
    
    @Autowired
    private WatchPartyService watchPartyService;
    
    // BlockService 안에서만 사용할 private 메서드
    private BlockDTO convertVoToDto(BlockVO blockVO, int acIdx, String period) {
        
        String blockType = blockVO.getBlockType();
        BlockDTO blockDTO = null;
        
        switch (blockType) {
            case "CategoryPosts":
                // config JSON 문자열을 파싱하여 category_idx, sort_type 얻어오기
                String configJson = blockVO.getConfig();
                int categoryIdx = 0;
                String categoryName = "", sortType = "";
                 
                if (configJson != null && !configJson.trim().isEmpty()) {
                    try {
                        // 간단한 JSON 파싱 (실제로는 DB에서 이미 파싱된 데이터를 사용하는 것이 좋음)
                        // 여기서는 예시로 하드코딩하거나, 다른 방법 사용
                        categoryIdx = 1; // 기본값
                        categoryName = "기본 카테고리";
                        sortType = "latest";
                         
                    } catch (Exception e) {
                        log.error("설정 파싱 중 오류 발생: " + e.getMessage());
                    }
                }
                
                // NoteService를 호출하여 해당 조건에 맞는 게시글 목록 가져오기
                List<NoteSummaryDTO> posts = noteService.getPostsByCategory(categoryIdx, sortType);
                
                blockDTO = CategoryPostsBlockDTO.builder()
                                                .blockId(blockVO.getBlockId())
                                                .blockType(blockVO.getBlockType())
                                                .blockOrder(blockVO.getBlockOrder())
                                                .categoryIdx(categoryIdx)
                                                .categoryName(categoryName)
                                                .sortType(sortType)
                                                .posts(posts)
                                                .build();
                
                break;
                
            case "WatchParties":
                // WatchPartyService를 호출하여 진행 중인 워치파티 목록을 가져오기
                List<WatchPartyDTO> watchParties = watchPartyService.getFollowingWatchPartyList(acIdx);
                
                blockDTO = WatchPartyBlockDTO.builder()
                                             .blockId(blockVO.getBlockId())
                                             .blockType(blockVO.getBlockType())
                                             .blockOrder(blockVO.getBlockOrder())
                                             .watchParties(watchParties)
                                             .build();
                
                break;
                
            case "UserStats":
                // NoteService를 호출하여 사용자 통계 가져오기
                UserStatsBlockDTO userStatsBlock = noteService.getUserStatsForChart(acIdx, period);
                userStatsBlock.setBlockId(blockVO.getBlockId());
                userStatsBlock.setBlockType(blockVO.getBlockType());
                userStatsBlock.setBlockOrder(blockVO.getBlockOrder());
                blockDTO = userStatsBlock;
                
                break;

            default:
                break;
        }
        
        return blockDTO;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BlockDTO> getBlocksForUser(int acIdx, String period) {
        log.info("사용자별 블록 목록 조회. 사용자 ID: " + acIdx + ", 기간: " + period);
        
        List<BlockDTO> blockDTOList = new ArrayList<>();
        
        List<BlockVO> blockVOList = blockMapper.findBlocksByAcIdx(acIdx);
        
        for (BlockVO blockVO : blockVOList) {
            BlockDTO blockDTO = this.convertVoToDto(blockVO, acIdx, period);
            blockDTOList.add(blockDTO);
        }
        
        log.debug("조회된 블록 개수: " + blockDTOList.size());
        
        return blockDTOList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public BlockDTO getBlockContentAsDto(int acIdx, int blockId, String period) {
        log.info("특정 블록 조회. 블록 ID: " + blockId + ", 사용자 ID: " + acIdx);
        
        BlockVO blockVO = blockMapper.findBlockById(blockId);
        if (blockVO == null) {
            return null;
        }
        
        BlockDTO blockDTO = this.convertVoToDto(blockVO, acIdx, period);
        
        return blockDTO;
    }
    
    @Override
    @Transactional
    public int addBlock(int acIdx, String blockType, Map<String, Object> config) {
        log.info("블록 추가. 사용자 ID: " + acIdx + ", 블록 타입: " + blockType);
        
        // Map을 JSON 문자열로 변환 (DB 저장용)
        String configJson = "{}"; // 기본값
        if (config != null && !config.isEmpty()) {
            try {
                // 간단한 JSON 변환 (실제로는 Jackson ObjectMapper 사용 권장)
                StringBuilder jsonBuilder = new StringBuilder("{");
                boolean first = true;
                for (Map.Entry<String, Object> entry : config.entrySet()) {
                    if (!first) {
                        jsonBuilder.append(",");
                    }
                    jsonBuilder.append("\"").append(entry.getKey()).append("\":");
                    if (entry.getValue() instanceof String) {
                        jsonBuilder.append("\"").append(entry.getValue()).append("\"");
                    } else {
                        jsonBuilder.append(entry.getValue());
                    }
                    first = false;
                }
                jsonBuilder.append("}");
                configJson = jsonBuilder.toString();
            } catch (Exception e) {
                log.error("JSON 변환 중 오류 발생: " + e.getMessage());
            }
        }
        
        BlockVO blockVO = BlockVO.builder()
                                 .acIdx(acIdx)
                                 .blockType(blockType)
                                 .config(configJson)
                                 .build();
        
        blockMapper.insertBlock(blockVO);
        
        log.debug("생성된 블록 ID: " + blockVO.getBlockId());
        
        return blockVO.getBlockId();
    }
    
    @Override
    @Transactional
    public boolean removeBlock(int acIdx, int blockId) {
        log.info("블록 삭제. 블록 ID: " + blockId + ", 사용자 ID: " + acIdx);
        
        int result = blockMapper.deleteBlock(acIdx, blockId);
        
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean changeBlockOrder(int acIdx, List<Map<String, Object>> orders) {
        log.info("블록 순서 변경. 사용자 ID: " + acIdx);
        
        for (Map<String, Object> order : orders) {
            int blockId = ((Number) order.get("block_id")).intValue();
            int blockOrder = ((Number) order.get("block_order")).intValue();
            
            blockMapper.updateBlockOrder(acIdx, blockId, blockOrder);
        }
        
        return true;
    }
    
    @Override
    public Map<String, Object> renderBlockToJson(BlockDTO blockData, String period) {
        Map<String, Object> response = new HashMap<>();
        
        // HTML 렌더링 로직 (실제 구현에서는 Thymeleaf나 JSP 렌더링)
        response.put("html", "<div>블록 HTML 내용</div>");
        response.put("block_type", blockData.getBlockType());
        
        // 차트 블록인 경우, 차트 데이터도 JSON에 추가
        if (blockData instanceof UserStatsBlockDTO) {
            UserStatsBlockDTO userStatsBlock = (UserStatsBlockDTO) blockData;
            response.put("chart_data", userStatsBlock.getChartData());
            response.put("title", userStatsBlock.getTitle());
        }
        
        return response;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getMyNoteData(int acIdx) {
        log.info("내 노트 데이터 조회. 사용자 ID: " + acIdx);
        
        // 내 노트 데이터 조회 로직
        Map<String, Object> noteData = new HashMap<>();
        noteData.put("notes", noteService.getMyNotes(acIdx));
        
        return noteData;
    }
} 