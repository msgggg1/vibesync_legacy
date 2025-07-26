package com.vibesync.watchparty.domain;

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
public class UserSummaryVO {

    private int ac_idx;
	private String nickname;
	private String profile_img;
	private int category_idx;
	private boolean followedByCurrentUser;

}
