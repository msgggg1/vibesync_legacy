package com.vibesync.workspace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.workspace.domain.WatchPartyDTO;
import com.vibesync.workspace.mapper.WorkspaceWatchPartyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class WatchPartyServiceImpl implements WatchPartyService {

    @Autowired
    private WorkspaceWatchPartyMapper watchPartyMapper;
    


    @Override
    @Transactional(readOnly = true)
    public List<WatchPartyDTO> getFollowingWatchPartyList(int acIdx) {
        log.info("팔로우하는 사용자들의 워치파티 목록 조회. 사용자 ID: " + acIdx);
        return watchPartyMapper.findFollowingWatchPartyList(acIdx);
    }
} 