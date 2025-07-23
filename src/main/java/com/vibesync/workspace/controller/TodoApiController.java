package com.vibesync.workspace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vibesync.domain.TodoVO;
import com.vibesync.security.domain.CustomUser;
import com.vibesync.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoApiController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<TodoVO> getTodoList(@AuthenticationPrincipal CustomUser user) {
        int acIdx = user.getAcIdx();
        return todoService.getTodoListByUser(acIdx);
    }

    @PostMapping
    public ResponseEntity<TodoVO> addTodo(
            @RequestBody TodoVO newTodo,
            @AuthenticationPrincipal CustomUser user) {
        
        newTodo.setAcIdx(user.getAcIdx());
        TodoVO createdTodo = todoService.addTodo(newTodo);
        
        return ResponseEntity.status(201).body(createdTodo);
    }
    
    @PutMapping("/{todoId}/status")
    public ResponseEntity<Void> updateTodoStatus(
            @PathVariable int todoId,
            @RequestBody boolean completed) {
        
        todoService.updateTodoStatus(todoId, completed);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<Void> updateTodo(
            @PathVariable int todoId,
            @RequestBody TodoVO updatedTodo) {
        
        updatedTodo.setTodoIdx(todoId);
        todoService.updateTodo(updatedTodo);
        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable int todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok().build();
    }
} 