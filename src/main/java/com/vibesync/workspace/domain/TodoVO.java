package com.vibesync.workspace.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoVO {
    private int todoIdx;
    private String text;
    private String todoGroup;
    private String color;
    private int acIdx;
    private int status;
    private Timestamp createdAt;
} 