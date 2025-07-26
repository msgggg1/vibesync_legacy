package com.vibesync.workspace.domain;

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
public class ScheduleVO {
    private int scheduleIdx;
    private String title;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;
    private String color;
    private int acIdx;
}
