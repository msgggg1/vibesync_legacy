package com.vibesync.board.domain;

import java.util.List;

import com.vibesync.common.domain.ContentVO;
import com.vibesync.common.domain.GenreVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardWriteDTO {
	
	// GET
	List<GenreVO> genreList;
	List<ContentVO> contentList;
	
	// POST
	private NoteVO note;
	private String thumbnailBase64;
	private String thumbnailExt;
	private String images;
	
}
