package com.vibesync.note.domain;

import java.sql.Timestamp;

import com.vibesync.member.domain.MemberVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDetailDTO {

    private int noteIdx;
    private String title;
    private String text; // Editor.js의 JSON 데이터
    private Timestamp createAt;
    private Timestamp editAt;
    private int viewCount;
    private int likeCount;
    private MemberVO member; // 작성자 정보
	
}
