package com.vibesync.workspace.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vibesync.workspace.domain.WatchPartyDTO;

@Mapper
public interface WorkspaceWatchPartyMapper {
    List<WatchPartyDTO> findFollowingWatchPartyList(@Param("hostList") List<Integer> hostList);
} 