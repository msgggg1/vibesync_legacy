package com.vibesync.like.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;

public interface LikeMapper {

	// 좋아요 추가
	int addLike(@Param("acIdx") int acIdx, @Param("noteIdx") int noteIdx) throws SQLException;
	
	// 좋아요 제거
	int removeLike(@Param("acIdx") int acIdx, @Param("noteIdx") int noteIdx) throws SQLException;
	
	// 특정 사용자가 특정 게시글을 좋아했는지 확인
	int isLiked(@Param("acIdx") int acIdx, @Param("noteIdx") int noteIdx) throws SQLException;
	
}
