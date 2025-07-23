package com.vibesync.workspace.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.vibesync.workspace.domain.BlockVO;

@Mapper
public interface BlockMapper {
    List<BlockVO> findBlocksByAcIdx(int acIdx);
    BlockVO findBlockById(int blockId);
    void insertBlock(BlockVO block);
    int deleteBlock(int acIdx, int blockId);
    void updateBlockOrder(int acIdx, int blockId, int blockOrder);
} 