package com.vibesync.workspace.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vibesync.workspace.domain.ScheduleVO;

@Mapper
public interface ScheduleMapper {

	List<ScheduleVO> findSchedulesByRange(@Param("acIdx") int acIdx, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

	List<ScheduleVO> findSchedulesByDate(@Param("acIdx") int acIdx, @Param("date") String date);

	int addSchedule(ScheduleVO newSchedule);

	int updateSchedule(ScheduleVO updatedSchedule);

	int deleteSchedule(@Param("scheduleIdx") int scheduleIdx);

}
