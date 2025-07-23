package com.vibesync.workspace.service;

import java.util.List;

import com.vibesync.workspace.domain.TodoVO;

public interface TodoService {
    List<TodoVO> getTodoListByUser(int acIdx);
    boolean addTodo(TodoVO todo);
    boolean updateTodoStatus(int todoIdx, boolean isCompleted);
    boolean deleteTodo(int todoIdx);
    boolean updateTodo(TodoVO todo);
} 