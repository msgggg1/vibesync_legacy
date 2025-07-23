package com.vibesync.userpage.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteSummaryDTO {
    private int noteIdx;           // 게시글 ID (postView로 링크 시 필요)
    private String title;           // 게시글 제목
    private String thumbnailImg;   // 게시글 썸네일 이미지 경로 (note 테이블의 img 필드 등)
    private int viewCount;
    private int likeCount;
    private String authorName;
}
