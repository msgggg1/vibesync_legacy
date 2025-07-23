package com.vibesync.userpage.domain;

import java.util.List;

import com.vibesync.userpage.domain.NoteSummaryDTO;

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
public class UserPageDataDTO {
    private UserPageInfoDTO userPageInfoDTO;        // 프로필 사용자 정보
    private MorePostsDTO morePostsDTO;
}
