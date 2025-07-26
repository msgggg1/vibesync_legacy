package com.vibesync.workspace.domain;

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
public class BlockVO {
    private int blockId;
    private int acIdx;
    private String blockType;
    private String config;
    private int blockOrder;
} 