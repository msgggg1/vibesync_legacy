package com.vibesync.follow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.vibesync.follow.domain.FollowUserDTO;
import com.vibesync.follow.domain.FollowVO;
import com.vibesync.follow.mapper.FollowMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class FollowServiceImpl implements FollowService {
	
	@Autowired
	private FollowMapper followMapper;

	@Transactional
	@Override
	public boolean toggleFollow(int followerAcIdx, int targetUserAcIdx) {
		log.info("팔로우 토글 요청. 팔로우/언팔로우 요청 대상: " + targetUserAcIdx);

		FollowVO follow = FollowVO.builder()
									.acFollow(followerAcIdx)
									.acFollowing(targetUserAcIdx)
									.build();
		
		if (this.isFollowing(followerAcIdx, targetUserAcIdx)) {
			// 언팔로우
			this.followMapper.deleteFollow(follow);
			return false;
		} else {
			// 팔로우
			this.followMapper.insertFollow(follow);
			return true;
		}
	}

	@Override
	public boolean isFollowing(int followerAcIdx, int targetUserAcIdx) {
		log.info("팔로우 상태 확인 요청. 대상: " + targetUserAcIdx);
		
		FollowVO follow = FollowVO.builder()
									.acFollow(followerAcIdx)
									.acFollowing(targetUserAcIdx)
									.build();
				
		return this.followMapper.checkFollowStatus(follow) > 0 ? true : false;
	}

	@Override
	public List<Integer> userFollowingIdList(int acIdx) {
		log.info("팔로잉 목록 조회. 사용자 ID: " + acIdx);
		return this.followMapper.userFollowingIdList(acIdx);
	}
	
	@Override
	public List<FollowUserDTO> getFollowingList(int acIdx) {
		log.info("팔로잉 상세 목록 조회. 사용자 ID: " + acIdx);
		return this.followMapper.getFollowingList(acIdx);
	}
	
	@Override
	public List<FollowUserDTO> getFollowerList(int acIdx) {
		log.info("팔로워 상세 목록 조회. 사용자 ID: " + acIdx);
		return this.followMapper.getFollowerList(acIdx);
	}
	
	@Override
	public int getFollowingCount(int acIdx) {
		log.info("팔로잉 카운트 조회. 사용자 ID: " + acIdx);
		return this.followMapper.getFollowingCount(acIdx);
	}
	
	@Override
	public int getFollowerCount(int acIdx) {
		log.info("팔로워 카운트 조회. 사용자 ID: " + acIdx);
		return this.followMapper.getFollowerCount(acIdx);
	}
	
}
