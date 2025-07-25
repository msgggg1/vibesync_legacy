package com.vibesync.workspace.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vibesync.workspace.domain.BlockVO;

@Mapper
public interface BlockMapper {
    List<BlockVO> findBlocksByAcIdx(@Param("acIdx") int acIdx);
    BlockVO findBlockById(@Param("blockId") int blockId);
    void insertBlock(BlockVO block);
    int deleteBlock(@Param("acIdx") int acIdx, @Param("blockId") int blockId);
    void updateBlockOrder(@Param("acIdx") int acIdx, @Param("blockId") int blockId,@Param("blockOrder") int blockOrder);
} 