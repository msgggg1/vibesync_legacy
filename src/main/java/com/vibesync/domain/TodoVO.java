package com.vibesync.domain;

import java.sql.Timestamp;
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
public class TodoVO {
    private int todoIdx;
    private String text;
    private String todoGroup;
    private String color;
    private int acIdx;
    private boolean completed;
    private Timestamp createdDate;
    private Timestamp updatedDate;
} 