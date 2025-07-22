package com.vibesync.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {

	private int categoryIdx;
	private String categoryName;
	private String img;
	
}
