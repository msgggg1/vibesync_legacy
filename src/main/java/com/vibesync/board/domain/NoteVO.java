package com.vibesync.board.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteVO {

	private int noteIdx;
	private int parentNoteIdx; // 상위 노트
	private int acIdx; // 작성자 (페이지 관리자)
	private String title;
	private String text;
	private int displayOrder;
	private String shareStatus;
	private Date createAt;
	private Date editAt;
	private int viewCount;
	private String titleimg;
	private int categoryIdx;
	private int customCategoryIdx;
	
}
