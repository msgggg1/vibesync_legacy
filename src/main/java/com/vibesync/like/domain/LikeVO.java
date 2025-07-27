package com.vibesync.like.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeVO {

	private int likesIdx;
	private Timestamp createdAt;
	private int noteIdx;
	private int acIdx;
	
}
