package com.vibesync.workspace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.workspace.mapper.WorkspaceFollowServiceMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class FollowServiceImpl implements FollowService {

    @Autowired
    private WorkspaceFollowServiceMapper followServiceMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getUserFollowingIdList(int acIdx) {
        log.info("사용자 팔로우 목록 조회. 사용자 ID: " + acIdx);
        return followServiceMapper.selectUserFollowingIdList(acIdx);
    }
} 