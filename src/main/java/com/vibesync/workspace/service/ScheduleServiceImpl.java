package com.vibesync.workspace.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.workspace.domain.CalendarEventDTO;
import com.vibesync.workspace.domain.ScheduleVO;
import com.vibesync.workspace.mapper.ScheduleMapper;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	
	@Autowired
	private ScheduleMapper scheduleMapper; 

	@Override
	public List<CalendarEventDTO> getMonthlySchedules(int acIdx, LocalDateTime start, LocalDateTime end) {
		
		List<ScheduleVO> schedulesFromDB = scheduleMapper.findSchedulesByRange(acIdx, start, end);

		return schedulesFromDB.stream()
			.map(vo -> CalendarEventDTO.builder()
					.scheduleIdx(vo.getScheduleIdx())
					.title(vo.getTitle())
					// Timestamp를 ISO 8601 형식의 문자열로 변환
					.start(vo.getStartTime().toInstant().toString())
					.end(vo.getEndTime().toInstant().toString())
					.color(vo.getColor())
					.acIdx(vo.getAcIdx())
					.description(vo.getDescription())
					.durationEditable(true)
					.allDay(false)
					.build())
			.collect(Collectors.toList());
	}

	@Override
	public List<ScheduleVO> getDailySchedules(int acIdx, String date) {
		return scheduleMapper.findSchedulesByDate(acIdx, date);
	}

	@Override
	@Transactional // 이 메소드가 실행될 때 트랜잭션을 시작하고, 끝나면 자동으로 커밋/롤백합니다.
	public ScheduleVO addSchedule(ScheduleVO newSchedule) {
		scheduleMapper.addSchedule(newSchedule);
		
		// Mybatis의 <insert> 태그에 <selectKey>
		// 파라미터로 전달된 newSchedule 객체에 DB가 생성한 ID(schedule_idx)가 자동으로 채워진다.
		// ID가 포함된 완전한 객체를 반환하는 것이 더 RESTful한 방식.
		return newSchedule; 
	}

	@Override
	@Transactional
	public void updateSchedule(ScheduleVO updatedSchedule) {
		scheduleMapper.updateSchedule(updatedSchedule);
	}

	@Override
	@Transactional
	public void deleteSchedule(int scheduleIdx) {
		scheduleMapper.deleteSchedule(scheduleIdx);
	}

}
