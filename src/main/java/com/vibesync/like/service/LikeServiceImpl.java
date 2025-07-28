package com.vibesync.like.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.like.mapper.LikeMapper;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	LikeMapper likeMapper;
	
	@Transactional
	@Override
	public boolean toggleLike(int acIdx, int noteIdx) throws SQLException {
		if (this.likeMapper.isLiked(acIdx, noteIdx) > 0) {
			this.likeMapper.removeLike(acIdx, noteIdx);
			return false;
		} else {
			this.likeMapper.addLike(acIdx, noteIdx);
			return true;
		}
		// 추후에 노트 테이블 좋아요 수 컬럼 수 변경하는 메서드 추가 예정
	}

	@Override
	public boolean getLikeStatus(int ac_idx, int note_idx) throws SQLException {
		return this.likeMapper.isLiked(ac_idx, note_idx) > 0;
	}

}
