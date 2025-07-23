package com.vibesync.workspace.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoVO {
    private Integer todo_idx;
    private String text;
    private String todo_group;
    private String color;
    private Integer ac_idx;
    private Boolean completed;
    private String created_date;
    private String updated_date;
} 