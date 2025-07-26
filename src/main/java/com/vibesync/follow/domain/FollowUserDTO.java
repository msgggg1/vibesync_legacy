package com.vibesync.follow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowUserDTO {
    private int acIdx;
    private String nickname;
    private String profile_img;
    private boolean followedByCurrentUser;
} 