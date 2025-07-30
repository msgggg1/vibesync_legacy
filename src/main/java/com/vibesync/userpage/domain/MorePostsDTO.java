package com.vibesync.userpage.domain;

import java.util.List;

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

public class MorePostsDTO {
	private List<NoteSummaryDTO> posts;
	private boolean hasMore;
	private int nextPage;
	
	
}

