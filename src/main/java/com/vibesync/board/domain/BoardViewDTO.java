package com.vibesync.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardViewDTO {
	
	private NoteDetailDTO noteDetail;
	private int userAcIdx;
	private boolean following;
	private boolean liking;
	
}
