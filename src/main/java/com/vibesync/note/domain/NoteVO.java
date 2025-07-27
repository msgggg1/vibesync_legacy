package com.vibesync.note.domain;

import java.sql.Timestamp;

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
	
	private int acIdx; // 작성자 (페이지 관리자)
	private int categoryIdx;
	private Integer parentNoteIdx; // 상위 노트
	private Integer customCategoryIdx;
	
	private String title;
	private String text; // Editor.js의 JSON 데이터
	private String titleimg;
	
	private int displayOrder;
	private String shareStatus;
	
	private int viewCount;
	private int likeCount;
	private Timestamp createAt;
	private Timestamp editAt;
	
}
