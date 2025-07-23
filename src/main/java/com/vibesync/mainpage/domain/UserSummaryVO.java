package com.vibesync.mainpage.domain;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSummaryVO {
	
	private String nickname;
	private int acIdx;
	
}
