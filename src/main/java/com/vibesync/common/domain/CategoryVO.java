package com.vibesync.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {

	private int category_idx;
	private String c_name;
	private String img;
	
}
