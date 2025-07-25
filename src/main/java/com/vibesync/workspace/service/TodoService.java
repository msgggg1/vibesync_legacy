package com.vibesync.workspace.service;

import java.util.List;

import com.vibesync.workspace.domain.TodoVO;

public interface TodoService {
    List<TodoVO> getTodoListByUser(int acIdx);
    TodoVO addTodo(TodoVO todo);
    void updateTodoStatus(int todoIdx, int status);
    void deleteTodo(int todoIdx);
    TodoVO updateTodo(TodoVO todo);
} 