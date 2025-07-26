package com.vibesync.workspace.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NoteSummaryDTO {
    private int noteIdx;
    private String title;
    private String thumbnailImg;
    private int viewCount;
    private int likeCount;
    private String authorName;
}
