package com.vibesync.like.service;

import java.sql.SQLException;

public interface LikeService {

	// 좋아요 토글
	boolean toggleLike(int acIdx, int noteIdx) throws SQLException;
	
	// 좋아요 여부
	boolean getLikeStatus(int ac_idx, int note_idx) throws SQLException;
	
}
