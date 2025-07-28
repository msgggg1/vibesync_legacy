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
public class UserProfileDTO {
	
	private int acIdx;
    private String nickname;
    private String img;
    private int postCount;
    private int followerCount;
    private int followingCount;
    private boolean followedByCurrentUser;
    
}
