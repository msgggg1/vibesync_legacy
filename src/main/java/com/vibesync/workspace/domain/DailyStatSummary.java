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
public class DailyStatSummary {
    private Long postCount = 0L;
    private Long viewCount = 0L;
    private Long likeCount = 0L;
} 