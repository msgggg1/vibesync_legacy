package com.vibesync.message.domain;

import com.vibesync.workspace.domain.UserSummaryDTO;

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
public class MessageListDTO {
    private int acSender;
    private UserSummaryDTO other;
    private int numOfUnreadMessages;
    private MessageDTO latestMessage;
} 