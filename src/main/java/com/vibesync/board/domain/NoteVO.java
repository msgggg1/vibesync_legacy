package com.vibesync.board.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteVO {

	private int noteIdx;
	private String title;
	private String text;
	private String img;
	private Date createAt;
	private Date editAt;
	private int viewCount;
	private String titleimg;
	private int contentIdx;
	private int genreIdx;
	private int categoryIdx;
	private int userpgIdx;
	
}
