package com.vibesync.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentVO {

	private int contentIdx;
	private String title;
	private String img;
	private String dsc;
	private int categoryIdx;
	
}
