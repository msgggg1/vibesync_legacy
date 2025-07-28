package com.vibesync.note.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteViewDTO {
	
	private NoteDetailDTO noteDetail;
	private int userAcIdx;
	private boolean following;
	private boolean liking;
	
}
