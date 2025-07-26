package com.vibesync.workspace.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibesync.workspace.domain.BlockDTO;
import com.vibesync.workspace.domain.BlockVO;
import com.vibesync.workspace.domain.CategoryPostsBlockDTO;
import com.vibesync.workspace.domain.ChartDataDTO;
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
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // BlockService 안에서만 사용할 private 메서드
    private BlockDTO convertVoToDto(BlockVO blockVO, int acIdx, String period) {
        
        String blockType = blockVO.getBlockType();
        BlockDTO blockDTO = null;
        
        switch (blockType) {
            case "CategoryPosts":
                // config JSON 문자열을 파싱하여 category_idx, sort_type 얻어오기
                String configJson = blockVO.getConfig();
                int categoryIdx = 0;
                String categoryName = "";
                String sortType = "";
                
                if (configJson != null && !configJson.trim().isEmpty()) {
                    Map<String, Object> config = null;
					try {
						config = objectMapper.readValue(configJson, Map.class);
					} catch (IOException e) {
						e.printStackTrace();
					}
					categoryIdx = (Integer) config.get("categoryIdx");
					categoryName = (String) config.get("categoryName");
					sortType = (String) config.get("sortType");
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
                if (userStatsBlock != null) {
                    userStatsBlock.setBlockId(blockVO.getBlockId());
                    userStatsBlock.setBlockType(blockVO.getBlockType());
                    userStatsBlock.setBlockOrder(blockVO.getBlockOrder());
                    blockDTO = userStatsBlock;
                } else {
                    // null인 경우 기본 UserStatsBlockDTO 생성
                    ChartDataDTO emptyChartData = ChartDataDTO.builder()
                        .labels(new ArrayList<>())
                        .datasets(new ArrayList<>())
                        .build();
                    
                    blockDTO = UserStatsBlockDTO.builder()
                        .blockId(blockVO.getBlockId())
                        .blockType(blockVO.getBlockType())
                        .blockOrder(blockVO.getBlockOrder())
                        .title("사용자 통계")
                        .chartData(emptyChartData)
                        .build();
                }
                
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
    public int addBlock(int acIdx, String blockType, String config) {
        log.info("블록 추가. 사용자 ID: " + acIdx + ", 블록 타입: " + blockType);
        
        // config는 이미 JSON 문자열로 전달받음
        String configJson = config != null ? config : "{}";
        
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
            int blockId = ((Number) order.get("blockId")).intValue();
            int blockOrder = ((Number) order.get("blockOrder")).intValue();
            
            blockMapper.updateBlockOrder(acIdx, blockId, blockOrder);
        }
        
        return true;
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
    
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getFixedBlockData(int acIdx, String blockType) {
        log.info("고정 블록 데이터 조회. 사용자 ID: " + acIdx + ", 블록 타입: " + blockType);
        
        Map<String, Object> blockData = new HashMap<>();
        
        switch (blockType) {
            case "MyPosts":
                // 내가 작성한 글 목록 조회 (인기글 순)
                List<NoteSummaryDTO> myPosts = noteService.getMyPostsPreview(acIdx);
                blockData.put("posts", myPosts);
                break;
                
            case "LikedPosts":
                // 좋아요한 글 목록 조회
                List<NoteSummaryDTO> likedPosts = noteService.getLikedPostsPreview(acIdx);
                blockData.put("posts", likedPosts);
                break;
                
            default:
                log.warn("알 수 없는 고정 블록 타입: " + blockType);
                return null;
        }
        
        return blockData;
    }
    

} 