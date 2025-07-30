package com.vibesync.workspace.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.workspace.domain.WatchPartyDTO;
import com.vibesync.workspace.mapper.WorkspaceWatchPartyMapper;
import com.vibesync.follow.service.FollowService;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class WatchPartyServiceImpl implements WatchPartyService {

    @Autowired
    private WorkspaceWatchPartyMapper watchPartyMapper;
    
    @Autowired
    private FollowService followService;

    @Override
    @Transactional(readOnly = true)
    public List<WatchPartyDTO> getFollowingWatchPartyList(int acIdx) {
        log.info("팔로우하는 사용자들의 워치파티 목록 조회. 사용자 ID: " + acIdx);
        
        // 1. 팔로잉 목록 조회
        List<Integer> followingUsers = followService.userFollowingIdList(acIdx);
        
        // 2. 팔로잉 목록이 비어있으면 빈 결과 반환
        if (followingUsers == null || followingUsers.isEmpty()) {
            log.info("팔로잉 목록이 비어있어 빈 결과 반환");
            return new ArrayList<>();
        }
        
        // 3. 팔로우하는 사용자들의 워치파티 목록 조회
        List<WatchPartyDTO> followingWatchParties = watchPartyMapper.findFollowingWatchPartyList(followingUsers);
        
        return followingWatchParties;
    }
} 