package com.vibesync.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardEditRequestDTO {
	
	private NoteVO note;
	private String thumbnailBase64;
	private String thumbnailExt;
	private String newImages; // 새로 추가된 이미지 목록
	private String existingImages; // 수정된 note의 이미지 목록
	private String originalImages; // 기존 note의 이미지 목록
	
}
