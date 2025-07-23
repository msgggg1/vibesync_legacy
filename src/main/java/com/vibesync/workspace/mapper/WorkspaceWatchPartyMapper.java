package com.vibesync.workspace.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.vibesync.workspace.domain.WatchPartyDTO;

@Mapper
public interface WorkspaceWatchPartyMapper {
    List<WatchPartyDTO> findFollowingWatchPartyList(int acIdx);
} 