package com.vibesync.message.domain;

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
public class MessageDTO {
    private int msgIdx;
    private String text;
    private Timestamp time;
    private String relativeTime;
    private String date;
    private String img;
    private int chk;
    private int acReceiver;
    private int acSender;
    private String senderNickname;
    private String senderImg;
    private boolean isMine;
} 