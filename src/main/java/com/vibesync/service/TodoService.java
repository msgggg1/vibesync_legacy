package com.vibesync.service;

import java.util.List;
import com.vibesync.domain.TodoVO;

public interface TodoService {
    List<TodoVO> getTodoListByUser(int acIdx);
    TodoVO addTodo(TodoVO todo);
    void updateTodoStatus(int todoIdx, boolean completed);
    void deleteTodo(int todoIdx);
    void updateTodo(TodoVO todo);
} 