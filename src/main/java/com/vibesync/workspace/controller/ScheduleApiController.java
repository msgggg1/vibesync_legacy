package com.vibesync.workspace.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vibesync.security.domain.CustomUser;
import com.vibesync.workspace.domain.CalendarEventDTO;
import com.vibesync.workspace.domain.ScheduleVO;
import com.vibesync.workspace.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules") // 리소스 중심으로 URL을 변경
public class ScheduleApiController {
	
	@Autowired
	private ScheduleService scheduleService;

	@GetMapping
	public List<CalendarEventDTO> getMonthlySchedules(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
			@AuthenticationPrincipal CustomUser user) {
		
		int acIdx = user.getAcIdx();
		// 서비스 메소드도 LocalDateTime을 받도록 수정하는 것이 좋습니다.
		return scheduleService.getMonthlySchedules(acIdx, start, end);
	}

	@GetMapping("/daily") // 명확한 구분을 위해 경로 추가
	public List<ScheduleVO> getDailySchedules(
			@RequestParam String date,
			@AuthenticationPrincipal CustomUser user) {
		
		int acIdx = user.getAcIdx();
		return scheduleService.getDailySchedules(acIdx, date);
	}

	
	@PostMapping
	public ResponseEntity<ScheduleVO> addSchedule(
			@RequestBody ScheduleVO newSchedule,
			@AuthenticationPrincipal CustomUser user) {
		
		newSchedule.setAcIdx(user.getAcIdx());
		ScheduleVO createdSchedule = scheduleService.addSchedule(newSchedule);
		
		// 생성 성공 시, 생성된 객체와 201 Created 상태를 반환
		return ResponseEntity.status(201).body(createdSchedule);
	}
	
	@PutMapping("/{scheduleId}")
	public ResponseEntity<Void> updateSchedule(
			@PathVariable int scheduleId,
			@RequestBody ScheduleVO updatedSchedule) {
		
		updatedSchedule.setScheduleIdx(scheduleId);
		scheduleService.updateSchedule(updatedSchedule);
		
		return ResponseEntity.ok().build(); // 성공했다는 의미로 200 OK 상태만 반환
	}

	@DeleteMapping("/{scheduleId}")
	public ResponseEntity<Void> deleteSchedule(@PathVariable int scheduleId) {
		scheduleService.deleteSchedule(scheduleId);
		return ResponseEntity.ok().build();
	}
}

