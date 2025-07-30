package com.vibesync.workspace.service;

import java.time.LocalDateTime;
import java.util.List;

import com.vibesync.workspace.domain.CalendarEventDTO;
import com.vibesync.workspace.domain.ScheduleVO;

public interface ScheduleService {

	public List<CalendarEventDTO> getMonthlySchedules(int acIdx, LocalDateTime start, LocalDateTime end) ;

	public List<ScheduleVO> getDailySchedules(int acIdx, String date);

	public ScheduleVO addSchedule(ScheduleVO newSchedule);

	public ScheduleVO updateSchedule(ScheduleVO updatedSchedule);

	public void deleteSchedule(int scheduleId);

	
}
