package com.vibesync.note.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteSaveRequestDTO {
	
	private String title;
    private String contentJson; // Editor.js는 내용을 JSON 형태로 출력함
    private int categoryIdx;
	
}
