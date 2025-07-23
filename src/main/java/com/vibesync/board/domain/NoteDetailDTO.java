package com.vibesync.board.domain;

import com.vibesync.member.domain.MemberVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDetailDTO {

	private NoteVO note;
	private MemberVO member;
	private int likeNum;
	private int upacIdx;
	
}
