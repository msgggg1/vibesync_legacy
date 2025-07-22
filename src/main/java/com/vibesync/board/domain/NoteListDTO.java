package com.vibesync.board.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteListDTO {

	private int noteIdx;
	private String title;
	private String author;
	
}
