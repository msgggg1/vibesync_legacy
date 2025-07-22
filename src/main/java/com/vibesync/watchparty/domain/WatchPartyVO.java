package com.vibesync.watchparty.domain;

import java.sql.Timestamp;

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

public class WatchPartyVO {
   private int watchPartyIdx;
    private String title;
    private String videoId;
    private Timestamp createdAt;
    private int host;
    private String hostNickname;
    private String hostImg;
}
