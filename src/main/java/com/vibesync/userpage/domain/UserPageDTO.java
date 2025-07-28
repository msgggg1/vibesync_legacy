package com.vibesync.userpage.domain;

import java.util.List;

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
public class UserPageDTO {
	
    private UserProfileDTO userProfile;
    private List<NoteSummaryDTO> posts;
    
}
