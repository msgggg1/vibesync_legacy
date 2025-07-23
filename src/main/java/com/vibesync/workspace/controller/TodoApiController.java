package com.vibesync.workspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vibesync.workspace.service.TodoService;

@RestController
@RequestMapping("/api/todo")
public class TodoApiController {
	
	@Autowired
	private TodoService todoService;
	
	@GetMapping("/todoList") // 할일 목록 로딩
	public 
	
	@PutMapping("/todoList") //할일 상태 업데이트

	@PostMapping("/todoList") //할일 등록
	
	@DeleteMapping("/todoList") // 할일 삭제
}
