package com.vibesync.page.domain;

import java.util.List;

import com.vibesync.mainpage.domain.NoteSummaryDTO;

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
public class UserPageDTO {

    private UserProfileDTO userProfile;        // 프로필 사용자 정보
    private List<NoteSummaryDTO> posts;         // 사용자가 작성한 게시글 목록 (초기 로드분)
    private boolean hasMorePosts;               // 더 로드할 게시글이 있는지 여부 (무한 스크롤용)
    private int nextPageNumber;                 // 다음에 로드할 페이지 번호 (무한 스크롤용)
	
}
