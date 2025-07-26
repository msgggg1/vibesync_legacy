package com.vibesync.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardWriteRequestDTO {
	
	private NoteVO note;
	private String thumbnailBase64;
	private String thumbnailExt;
	private String images;
	
}
