package com.vibesync.workspace.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * JavaScript 캘린더 라이브러리(예: FullCalendar)와 연동하기 위한 DTO
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class CalendarEventDTO {
	//@SerializedName("id")
    private int scheduleIdx;          
    private String title;    // 일정 제목
    private String start;    // 시작 시간 (ISO 8601 형식의 문자열, 예: "2025-06-07T14:00:00")
    private String end;      // 종료 시간
    private String color;    // 이벤트 색상
    private int acIdx;
    private String description; 
    
    private boolean durationEditable;
    private boolean allDay;

}
