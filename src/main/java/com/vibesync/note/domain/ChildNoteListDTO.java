package com.vibesync.note.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChildNoteListDTO {

	private int noteIdx;
	private String title;
	private int displayOrder;
	private String shareStatus;
	
}
