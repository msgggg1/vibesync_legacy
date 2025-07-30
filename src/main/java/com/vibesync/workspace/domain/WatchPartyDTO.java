package com.vibesync.workspace.domain;

import com.vibesync.watchparty.domain.WatchPartyVO;

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
public class WatchPartyDTO {
    private WatchPartyVO watchparty;
	private int current_num;
	private int max_num;
	private UserSummaryDTO host;
}
