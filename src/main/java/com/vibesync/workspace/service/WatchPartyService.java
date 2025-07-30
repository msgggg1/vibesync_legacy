package com.vibesync.workspace.service;

import java.util.List;
import com.vibesync.workspace.domain.WatchPartyDTO;

public interface WatchPartyService {
    List<WatchPartyDTO> getFollowingWatchPartyList(int acIdx);
} 