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
	
	private int noteIdx;
    private String title;
    private String titleimg;
    
}
